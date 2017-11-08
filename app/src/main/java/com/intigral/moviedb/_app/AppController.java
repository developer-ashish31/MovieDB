package com.intigral.moviedb._app;
/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */

import android.app.Application;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.Tracker;
import com.intigral.moviedb._googletracker.AnalyticsTrackers;
import com.intigral.moviedb._property.LruBitmapCache;
import com.intigral.moviedb._user.LoginInformation;


import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Date;


public class AppController extends Application {

	public static final String TAG = AppController.class
			.getSimpleName();

	private RequestQueue mRequestQueue;
	private ImageLoader mImageLoader;

	private static AppController mInstance;
	private final RediaoExceptionHandler UNCAUGHT_EX_HANDLER = new RediaoExceptionHandler();
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		LoginInformation.createInstance(this);
//		GcmKeyChecker.createInstance(this);
		AnalyticsTrackers.initialize(this);
//		AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
//		Intent intent = new Intent(this, RegistrationIntentService.class);
//		startService(intent);
		Thread.setDefaultUncaughtExceptionHandler(UNCAUGHT_EX_HANDLER);
		/*Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread thread, Throwable e) {
				handleUncaughtException(thread, e);
			}
		});*/
	}
	/*public void handleUncaughtException (Thread thread, Throwable e)
	{
		e.printStackTrace(); // not all Android versions will print the stack trace automatically

		Intent intent = new Intent ();
		intent.setAction("com.mydomain.SEND_LOG"); // see step 5.
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // required when starting from Application
		startActivity(intent);

		System.exit(1); // kill off the crashed app
	}*/
	public static synchronized AppController getInstance() {
		return mInstance;
	}

	public RequestQueue getRequestQueue() {
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext());
		}

		return mRequestQueue;
	}
	public synchronized Tracker getGoogleAnalyticsTracker() {
		AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
		return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
	}

	public ImageLoader getImageLoader() {
		getRequestQueue();
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader(this.mRequestQueue,
					new LruBitmapCache());
		}
		return this.mImageLoader;
	}

	public <T> void addToRequestQueue(Request<T> req, String tag) {
		// set the default tag if tag is empty
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
		getRequestQueue().add(req);
	}

	public <T> void addToRequestQueue(Request<T> req) {
		req.setTag(TAG);
		getRequestQueue().add(req);
	}

	public void cancelPendingRequests(Object tag) {
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

	/*private String extractLogToFile()
	{

		try {
			PackageManager manager = this.getPackageManager();
			PackageInfo info = null;
			try {
				info = manager.getPackageInfo(this.getPackageName(), 0);
			} catch (PackageManager.NameNotFoundException e2) {
			}
			String model = Build.MODEL;
			if (!model.startsWith(Build.MANUFACTURER))
				model = Build.MANUFACTURER + " " + model;

			// Make file name - file must be saved to external storage or it wont be readable by
			// the email app.
			String path = Environment.getExternalStorageDirectory() + "/" + "MyApp/";
			String fullName = path + < some name >;

			// Extract to file.
			File file = new File(fullName);
			InputStreamReader reader = null;
			FileWriter writer = null;
			try {
				// For Android 4.0 and earlier, you will get all app's log output, so filter it to
				// mostly limit it to your app's output.  In later versions, the filtering isn't needed.
				String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
						"logcat -d -v time MyApp:v dalvikvm:v System.err:v *:s" :
						"logcat -d -v time";

				// get input stream
				Process process = Runtime.getRuntime().exec(cmd);
				reader = new InputStreamReader(process.getInputStream());

				// write output stream
				writer = new FileWriter(file);
				writer.write("Android version: " + Build.VERSION.SDK_INT + "\n");
				writer.write("Device: " + model + "\n");
				writer.write("App version: " + (info == null ? "(null)" : info.versionCode) + "\n");

				char[] buffer = new char[10000];
				do {
					int n = reader.read(buffer, 0, buffer.length);
					if (n == -1)
						break;
					writer.write(buffer, 0, n);
				} while (true);

				reader.close();
				writer.close();
			} catch (IOException e) {
				if (writer != null)
					try {
						writer.close();
					} catch (IOException e1) {
					}
				if (reader != null)
					try {
						reader.close();
					} catch (IOException e1) {
					}

				// You might want to write a failure message to the log here.
				return null;
			}

			return fullName;

		}catch (Exception e){
			e.printStackTrace();
		}
	}*/


	private class RediaoExceptionHandler implements Thread.UncaughtExceptionHandler {
		public void uncaughtException(Thread thread, Throwable throwable) {

			throwable.printStackTrace();
			final Writer result = new StringWriter();
			final PrintWriter printWriter = new PrintWriter(result);
			throwable.printStackTrace(printWriter);
			String stacktrace = result.toString();
			printWriter.close();

//			Crittercism.logHandledException(throwable);

			writeToFile(stacktrace);

			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			killApplication();

			System.exit(2);

			final DialogInterface.OnClickListener exitHandler = new DialogInterface.OnClickListener() {

				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							// cancelTimer();

							Intent i = getBaseContext().getPackageManager()
									.getLaunchIntentForPackage(
											"pfunzo.sourcesoftsolution.com.pfunzo");
							i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(i);
							break;
						default:
							// cancelTimer();

							Intent intent = new Intent(Intent.ACTION_MAIN);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.addCategory(Intent.CATEGORY_HOME);
							startActivity(intent);
							killApplication();
							break;
					}
				}
			};

		}
	}

	public static void killApplication() {

		android.os.Process.killProcess(android.os.Process.myPid());
		System.runFinalizersOnExit(true);
	}

	public static boolean isExternalStorageWritable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;
	}

	/* Checks if external storage is available to at least read */
	public static boolean isExternalStorageReadable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			return true;
		}
		return false;
	}
	public  void writeToFile(Exception exception) {
		Throwable t = new Throwable();
		StackTraceElement[] elements = t.getStackTrace();
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();

	}
	private  void writeToFile(String stacktrace) {


		File file = null;
		if (isExternalStorageWritable()) {
			String path = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
			file = new File(path + "/pfunzo");
			file.mkdir();
			file = new File(path + "/pfunzo/crash.txt");
		} else {

			file = new File(getCacheDir(), "/pfunzo");
			file.mkdir();

			file = new File(getCacheDir(),
					"/pfunzo/crahs.txt");

		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file,
					true));
			Date d = new Date();
			writer.write(":::::::::::::::::::::::::::: Date :" + d.toString());
			writer.write("--------totalMemory------------" + ""
					+ Runtime.getRuntime().totalMemory() / 1024 + " KB");
			writer.write("--------freeMemory------------" + ""
					+ Runtime.getRuntime().freeMemory() / 1024 + " KB");
			writer.write(stacktrace);
			writer.flush();
			writer.close();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedWriter writerssss = new BufferedWriter(new OutputStreamWriter(baos));
			writerssss.write(":::::::::::::::::::::::::::: Date :" + d.toString());
			writerssss.write("--------totalMemory------------" + ""
					+ Runtime.getRuntime().totalMemory() / 1024 + " KB");
			writerssss.write("--------freeMemory------------" + ""
					+ Runtime.getRuntime().freeMemory() / 1024 + " KB");

			writerssss.write(stacktrace);
			writerssss.flush();

			writerssss.close();
			byte[] bytes = baos.toByteArray();

			//LongOperationDataSender operation =new LongOperationDataSender(bytes,file.getPath());
			//operation.execute(""+file.getPath());
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
