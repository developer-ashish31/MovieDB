package com.intigral.moviedb._property;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Vibrator;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.intigral.moviedb.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Created by AKM on 28,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class AppUtils {

    static String TAG = "AppUtils";

    public static int getAcctivityCount(Context context) {
        int count = 0;
        ActivityManager mngr = (ActivityManager) context
                .getSystemService(context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> taskList = mngr
                .getRunningTasks(10);

        if (taskList.get(0).topActivity.getPackageName().equals(
                context.getPackageName())) {
            count++;
        }
        Log.d("AppUtils", "------getAcctivityCount---- > " + count);
        return count;
    }

    private static Dialog lDialog = null;


    private static void closeDialog() {
        if (lDialog != null && lDialog.isShowing()) {
            lDialog.dismiss();
            lDialog = null;
        }
    }

    public static void showWebViewDialog(final Activity context, String url) {
        if (lDialog != null && lDialog.isShowing()) {
            lDialog.dismiss();
            lDialog = null;
        }

        lDialog = new Dialog(context,
                android.R.style.Theme_Translucent_NoTitleBar) {
            public void onBackPressed() {
                closeDialog();
            }

            ;
        };
        lDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        lDialog.setContentView(R.layout.webview_screen);
        final WebView webView = (WebView) lDialog.findViewById(R.id.webView1);
        final View loading = lDialog.findViewById(R.id.loading);
        final View close = lDialog.findViewById(R.id.close);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            private int webViewPreviousState;
            private final int PAGE_STARTED = 0x1;
            private final int PAGE_REDIRECTED = 0x2;

            @Override
            public boolean shouldOverrideUrlLoading(WebView view,
                                                    String urlNewString) {
                webViewPreviousState = PAGE_REDIRECTED;
                webView.loadUrl(urlNewString);
                return true;
            }


            @Override
            public void onPageFinished(WebView view, String url) {

                // if (webViewPreviousState == PAGE_STARTED)
                {
                    // loading.setVisibility(View.GONE) ;
                }
            }

        });
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress >= 100)
                    loading.setVisibility(View.GONE);
                // change your progress bar
            }

        });
        webView.loadUrl(url);

        close.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                closeDialog();
            }
        });
        if (!((Activity) context).isFinishing())
            lDialog.show();

    }

    public static void killApp(boolean killSafely) {
        if (killSafely) {
            System.runFinalizersOnExit(true);
            System.exit(0);
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

    }


    private static Context contextS;


    public static void showToastCenterDecord(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                View view = ((LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.customtost, null, false);

                ((TextView) view.findViewById(R.id.textView2)).setText(message);
                Toast toast = new Toast(activity);
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });
    }

    public static void moveFile(String inputFile, String outputPath) {

        InputStream in = null;
        OutputStream out = null;
        try {


            in = new FileInputStream(inputFile); // inputPath + inputFile
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;


        } catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void openWeb(Context context, String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }


    public static String getAppDownloadLink(Context context) {

        return "http://risingstars.jangobanana.com/invite/app.html";

    }

    public static void addDrawableTop(TextView textView, int id) {
        Drawable drawable = textView.getContext().getResources()
                .getDrawable(id);
        Rect r = null;
        Drawable[] d = textView.getCompoundDrawables();
        r = null;
        if (d[1] != null)
            r = d[1].getBounds();
        if (r != null)
            drawable.setBounds(r);
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    public static void cancelNotification(Context context, int notificationId) {
        try {
            NotificationManager notificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(notificationId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void vibrator(Context context, long milliseconds) {

        Vibrator v = (Vibrator) context
                .getSystemService(Context.VIBRATOR_SERVICE);

        // Output yes if can vibrate, no otherwise
        if (v.hasVibrator()) {
            Log.v("Can Vibrate", "YES");
        } else {
            Log.v("Can Vibrate", "NO");
        }
        v.vibrate(milliseconds);
    }


    public static Bitmap getButmapFromAssest(Context context, String fileName) {
        AssetManager assetManager = context.getAssets();
        InputStream istr = null;
        try {
            istr = assetManager.open(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(istr);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static File saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory()
                + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Const.COMPRESS_TYPE, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {

            // Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {

            // Log.e("GREC", e.getMessage(), e);
        }
        return imagePath;
    }


    public static void startAnimition(View view, int animition) {
        try {
            Animation animation = AnimationUtils.loadAnimation(
                    view.getContext(), animition);
            view.startAnimation(animation);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //
    }

    public static void showGoHomeDialog(Context context) {

    }


    public static int getDuration(Context context, String path) {
        try {// /data/data/com.jb.imaloud.base/cache/VM_1415359697964.aac


            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();

            long timeInmillisec = Long.parseLong(time) / 1000;

            return (int) timeInmillisec;
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    public static int getDuration(Context context, String path, int defalut) {
        try {// /data/data/com.jb.imaloud.base/cache/VM_1415359697964.aac


            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(path);
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();

            long timeInmillisec = Long.parseLong(time) / 1000;

            if (timeInmillisec < defalut)
                return defalut;
            return (int) timeInmillisec;
        } catch (Exception e) {
            e.printStackTrace();
            ;
            return defalut;
        }
    }

    public static int getDurationFile(Context context, File file) {
        try {


            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(file.getAbsolutePath());
            String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();

            long timeInmillisec = Long.parseLong(time) / 1000;

            return (int) timeInmillisec;
        } catch (Exception e) {
            e.printStackTrace();

            return 0;
        }
    }

    public static byte[] ConvertFileToByteArray(final String filePath) {
        byte[] data = null;

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        File aFile = new File(filePath);
        InputStream is;

        try {
            is = new FileInputStream(aFile);
            byte[] temp = new byte[1024];
            int read;

            try {
                while ((read = is.read(temp)) >= 0) {
                    buffer.write(temp, 0, read);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
            }

            data = buffer.toByteArray();
            do {
                try {
                    Thread.sleep(500);
                } catch (Exception e) {

                    // TODO: handle exception
                }
            } while (data == null);

            // System.out.println("VideoPath========"+data.length);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
        }


        return data;

    }


    public static String gen7DigitNumber() {
        Random rng = new Random();
        int val = rng.nextInt(10000000);
        return String.format("%07d", val);
    }

    static private Dialog progressdialog;

    public static void showWating(Activity mContext) {

        hideWating();
        // progressdialog = new ProgressDialog(mContext);
        progressdialog = new Dialog(mContext,
                android.R.style.Theme_Translucent_NoTitleBar);
        progressdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressdialog.setContentView(R.layout.wating_dialog);// Message("Wait...");
        if (!mContext.isFinishing())
            progressdialog.show();
    }

    public static void hideWating() {
        try {
            if (progressdialog != null && progressdialog.isShowing())
                progressdialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readRaw(Context context, int resId) {
        try {
            Resources res = context.getResources();
            InputStream in_s = res.openRawResource(resId);

            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            in_s.close();
            return b;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void changeBG(View view, int count, int resid) {
        if (count <= 0)
            view.setBackgroundResource(R.color.white);
        else
            view.setBackgroundResource(resid);
    }


    public static Intent findTwitterClient(Context context) {
        final String[] twitterApps = {

                "com.twitter.android", // official - 10 000
                "com.twidroid", // twidroid - 5 000
                "com.handmark.tweetcaster", // Tweecaster - 5 000
                "com.thedeck.android"}; // TweetDeck - 5 000 };
        Intent tweetIntent = new Intent();
        tweetIntent.setType("text/plain");
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(
                tweetIntent, PackageManager.MATCH_DEFAULT_ONLY);

        for (int i = 0; i < twitterApps.length; i++) {
            for (ResolveInfo resolveInfo : list) {
                String p = resolveInfo.activityInfo.packageName;
                if (p != null && p.startsWith(twitterApps[i])) {
                    tweetIntent.setPackage(p);
                    return tweetIntent;
                }
            }
        }

        return null;
    }


    public static void logHandledException(Exception exception) {

    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getAppPackage(Context context) {
        try {

            return context.getPackageName();
        } catch (Exception e) {
            // should never happen
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    public static String getSecToHumanReadableFormat(int s) {
        try {
            String readable = String.format("%d:%02d", s / 60, s % 60);
            return readable;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s + "s";

    }

    public static String getSecToHumanReadableFormatRemoveZero(int s) {
        try {
            String readable = String.format("%d:%02d", s / 60, s % 60);
            if (readable != null && readable.indexOf(":00") != -1) {
                readable = readable.substring(0, readable.indexOf(":"));
            }
            return readable;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return s + "s";

    }


    public static void setViewColor(ImageView view, String color) {
        try {
            ColorFilter cf = new PorterDuffColorFilter(Color.parseColor(color), PorterDuff.Mode.MULTIPLY);
            view.getDrawable().setColorFilter(cf);
            view.invalidate();
        } catch (Exception e) {
            System.err.print("" + e.getMessage());
        }
    }


    public static void openVideo(String url, Activity activity) {
        try {


            Uri uri = Uri.parse(url);//"https://s3-ap-southeast-1.amazonaws.com/imaloud/VID_20150518_165734139.mp4");//media.getMediaUrl());//"http://media.jangobanana.com/mediaServer/get/372_1_5_E_V_V2_e3ijshr915.3gp");
            Log.d("openVideo", "video url :" + uri.toString());


//		AppUtils.showToast(activity,uri.toString()) ;
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setDataAndType(uri, "video/mp4");
            activity.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        si = false;
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp - 1) + (si ? "" : "");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    //com.whatsapp"
    public static boolean chachAppWahtapp(Activity activity) {

        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(
                it, 0);
        boolean yes = false;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.startsWith("com.whatsapp")
                    || info.activityInfo.name.toLowerCase().contains(
                    "whatsapp")) {
                yes = true;
                break;
            }
        }
        return yes;
    }

    public static boolean checkAppInstalled(Activity activity, String packageName) {

        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        final PackageManager pm = activity.getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(
                it, 0);
        boolean yes = false;
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.startsWith(packageName)
                    || info.activityInfo.name.toLowerCase().contains(
                    packageName)) {
                yes = true;
                break;
            }
        }
        return yes;
    }


    public static void hideKeyboard(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            // Ignore exceptions if any
            Log.e("KeyBoardUtil", e.toString(), e);
        }
    }

    public static Boolean isActivityRunning(Class activityClass, Context context) {

        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

            for (ActivityManager.RunningTaskInfo task : tasks) {
                if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                    return true;
            }

            return false;
        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }
    }


    public void akm(String akm) {

    }

	/*public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private Button startBtn;
	private static ProgressDialog mProgressDialog;


	public static void downloadFile(String downloadUrl, String filePath, Context mContext,String downloadText , int type, int action, Activity activity) {

		File file = new File(filePath);
		if(file.exists()) {
//Do somehting
			switch (type){
				case Const.MEDIA_TYPE_PDF:
					if(action ==Const.ACTION_VIEW) {
						Intent intent = new Intent(mContext, MyPdfViewerActivity.class);
						intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME,
								filePath);
						mContext.startActivity(intent);
					}else
					{

						DialogInterface.OnClickListener okHandler = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								//Toast.makeText(RegistrationActivity.this,"OK",Toast.LENGTH_SHORT).show();
							}
						};
						new AlertDialogView().showSimpleAlert("", "Your file has been already downloaded",

								okHandler, mContext, activity);

						//Toast.makeText(mContext, "Your file has been already downloaded",Toast.LENGTH_LONG).show();
					}
					break;
				case Const.MEDIA_TYPE_AUDIO:
					if(action ==Const.ACTION_VIEW) {
						*//*Intent intent = new Intent(mContext, MyPdfViewerActivity.class);
                        intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME,
								filePath);
						mContext.startActivity(intent);*//*
					}else
					{
						DialogInterface.OnClickListener okHandler = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								//Toast.makeText(RegistrationActivity.this,"OK",Toast.LENGTH_SHORT).show();
							}
						};
						new AlertDialogView().showSimpleAlert("", "Your file has been already downloaded",

								okHandler, mContext, activity);
						//Toast.makeText(mContext, "Your file has been already downloaded",Toast.LENGTH_LONG).show();
					}
					break;
				case Const.MEDIA_TYPE_IMAGE:

					break;
				case Const.MEDIA_TYPE_VIDEO:

					break;
			}
		}else
		{

			DownloadFileAsync async = new DownloadFileAsync(mContext, filePath, type, action, activity);
			async.execute(downloadUrl);
		}

	}
	public static Dialog onCreateDialog(int id,Context mContext) {
		switch (id) {
			case DIALOG_DOWNLOAD_PROGRESS:
				mProgressDialog = new ProgressDialog(mContext);
				mProgressDialog.setMessage("Downloading file..");
				mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				mProgressDialog.setCancelable(false);
				mProgressDialog.show();
				return mProgressDialog;
			default:
				return null;
		}
	}*/
	/*public static class DownloadFileAsync extends AsyncTask<String, String, String> {
		Context mContext;
		String filePath;
		int type;
		int action;
		Activity activity;
	    public DownloadFileAsync(Context mContext,String filePath,int type,int action,Activity activity) {
		this.mContext=mContext;
		this.filePath=filePath;
			this.activity=activity;
			this.type=type;
			this.action=action;
			}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			onCreateDialog(DIALOG_DOWNLOAD_PROGRESS,mContext);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream(filePath);

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
			}
			return null;

		}

		protected void onProgressUpdate(String... progress) {
			Log.d("ANDRO_ASYNC", progress[0]);
			mProgressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) {
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
			switch (type){
				case Const.MEDIA_TYPE_PDF:
					if(action==Const.ACTION_VIEW) {
						Intent intent = new Intent(mContext, MyPdfViewerActivity.class);
						intent.putExtra(PdfViewerActivity.EXTRA_PDFFILENAME,
								filePath);
						mContext.startActivity(intent);
					}else if(action==Const.ACTION_DOWNLAOD){
						//Toast.makeText(mContext, "Your file has been downloaded in your sdcard",Toast.LENGTH_LONG).show();
						DialogInterface.OnClickListener okHandler = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								//Toast.makeText(RegistrationActivity.this,"OK",Toast.LENGTH_SHORT).show();
							}
						};
						new AlertDialogView().showSimpleAlert("", "Your file has been downloaded in your sdcard",

								okHandler, mContext, activity);
					}
					break;
				case Const.MEDIA_TYPE_AUDIO:
					if(action==Const.ACTION_VIEW) {

					}else if(action==Const.ACTION_DOWNLAOD){
						//Toast.makeText(mContext, "Your file has been downloaded in your sdcard",Toast.LENGTH_LONG).show();
						DialogInterface.OnClickListener okHandler = new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
												int which) {
								//Toast.makeText(RegistrationActivity.this,"OK",Toast.LENGTH_SHORT).show();
							}
						};
						new AlertDialogView().showSimpleAlert("", "Your file has been downloaded in your sdcard",

								okHandler, mContext, activity);

					}
					break;
				case Const.MEDIA_TYPE_IMAGE:

					break;
				case Const.MEDIA_TYPE_VIDEO:

					break;
			}


		}

		public static void dismissDialog(int id){
			if(mProgressDialog!=null && mProgressDialog.isShowing()){
				mProgressDialog.dismiss();
			}
		}

	}*/

    //String url = "http://farm1.static.flickr.com/114/298125983_0e4bf66782_b.jpg";
    public static Uri getOutputMediaFileUri(int type, String fileName) {
        return Uri.fromFile(getOutputMediaFile(type, fileName));
    }

    /*
      * returning image / video
      */
    public static File getOutputMediaFile(int type, String fileName) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                Const.IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d(Const.IMAGE_DIRECTORY_NAME, "Oops! Failed create "
                        + Const.IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        String timeStamp = fileName;
        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
        //	Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == Const.MEDIA_TYPE_IMAGE_PATH) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else if (type == Const.MEDIA_TYPE_IMAGE_PATH) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "VID_" + timeStamp + ".mp4");
        } else if (type == Const.MEDIA_TYPE_AUDIO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "AUD_" + timeStamp + ".mp3");
        } else if (type == Const.MEDIA_TYPE_PDF) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "PDF_" + timeStamp + ".pdf");
        } else {
            return null;
        }

        return mediaFile;
    }


    public static byte[] encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            return encodedParams.toString().getBytes(paramsEncoding);
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }


    public static String getPostParamsEncoding() {
        return getParamsEncoding();
    }

    public static String getParamsEncoding() {
        return DEFAULT_PARAMS_ENCODING;
    }

    public static final String DEFAULT_PARAMS_ENCODING = "UTF-8";
    public static Dialog dialog;

    public static void appRatingOnGooglePlayStore(final Context mContext, final Activity mActivity, final String appPackageName) {
        final RatingBar ratingBar;
        final TextView txtRatingValue;
        final Button btnSubmit;
        final ImageView close;
        try {

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            dialog = new Dialog(mActivity, android.R.style.Theme_Translucent_NoTitleBar);
            WindowManager.LayoutParams wp = dialog.getWindow()
                    .getAttributes();
            wp.dimAmount = 0.50f;
            dialog.getWindow().getAttributes().windowAnimations = R.style.animation;
            dialog.getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_DIM_BEHIND);
           /* dialog.setContentView(R.layout.r_okcanceldialogview);*/
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.apprating);
            ratingBar = (RatingBar) dialog.findViewById(R.id.ratingBar);
            txtRatingValue = (TextView) dialog.findViewById(R.id.txtRatingValue);
            close = (ImageView) dialog.findViewById(R.id.close);
            close.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(final View v) {
                    dialog.dismiss();
                }
            });
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {

                }
            });
            dialog.show();


            //if rating value is changed,
            //display the current rating value in the result (textview) automatically
            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {

                    txtRatingValue.setText(String.valueOf(rating));

                }
            });


            btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);

            //if click on me, then display the current rating value.
            btnSubmit.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                    try {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                    } catch (ActivityNotFoundException ex) {
                        mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
                    }
                    DialogInterface.OnClickListener okHandler = new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            //Toast.makeText(RegistrationActivity.this,"OK",Toast.LENGTH_SHORT).show();

                        }
                    };
                    new AlertDialogView().showSimpleAlert("", "Thank you for given rating!",

                            okHandler, mContext, mActivity);

                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showToast(final Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {

            @Override
            public void run() {

                View view = ((LayoutInflater) activity
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                        .inflate(R.layout.customtost, null, false);

                ((TextView) view.findViewById(R.id.textView2)).setText(message);
                Toast toast = new Toast(activity);
                toast.setView(view);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });
    }
}

