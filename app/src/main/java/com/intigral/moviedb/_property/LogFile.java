package com.intigral.moviedb._property;

import android.os.Environment;


import com.intigral.moviedb._app.AppController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;


public class LogFile
{	
	public static boolean LoggerFlag=true;//on live false
	public static boolean requestResponse = false;//on live false
	public static boolean requestResponseDetaild=false;//on live false
	public static boolean CheatFlag=false;//on live false
	public static boolean LoggerFlagException=false;//on live false
	public static boolean infoSearchOpponent=false;//on live false
	public static boolean easyTracker=false;//on live false	
	public static boolean userBackgroundForgroundStatus=false;//on live false
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("[ MMM dd, yyyy HH:mm:ss:SSS]");
	
	public LogFile(){} 
	
	public static final void debugFb(String tag, String message) {}
	public static final void debug(String tag, String message) {}
	public static final void debugOP(String tag, byte[] message) {}
	private static void saveToFile3(String data, Throwable th) {}
	private static void saveToFile4(String data, Throwable th) {}
	private static void saveToFile(String data, Throwable th) {}
	private static void saveToFileFbLog(String data, Throwable th) {}
	private static void saveToFileSearchOpponent(String data, Throwable th) {}
	
	public static void pushKey(String data) {

	}
	private static void saveToFileException(String data, Throwable th) {}
	private static void saveToFile2(String data, Throwable th) {}
	private static void saveToFile(String fname, byte []data, Throwable th) {}
	public static final void info(String tag, String message) {	}
	public static final void infoSearchOpponent(String tag, String message) {}
	public static final void infoException(String tag, String message) {}
	public static final void warning(String tag, String message) {}
	public static final void error(String tag, String message, Throwable th) {}
	
	static String responseLogfileName = "requestResponse_"+ClientParam.client_version+".txt" ;
//	static String pushkey = "pushkey_"+ClientParam.client_version+".txt" ;
	
	public static void deleteRequestResponse()
	{
		File file =null;
		if(isExternalStorageWritable()){
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		file = new File(path + "/moviedb/"+responseLogfileName);
		}else{
			 file= new File(AppController.getInstance().getCacheDir(),
					 responseLogfileName);
		}
		file.delete() ;
	}
	public static String getRequestResponse()
	{
		File file =null;
		if(isExternalStorageWritable()){
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		file = new File(path + "/moviedb/"+responseLogfileName);
		}else{
			 file= new File(AppController.getInstance().getCacheDir(),
					 responseLogfileName);
		}
		return file.getAbsolutePath() ;
	}
	public static void requestResponse(String data) {
//		Log.d("LOG FILE",data);
		File file =null;
		
		Throwable t = new Throwable();
		StackTraceElement[] elements = t.getStackTrace();

//		String calleeMethod = elements[0].getMethodName(); 
//		String callerMethodName = elements[1].getMethodName(); 
//		String callerClassName = elements[1].getClassName(); 

		if(requestResponseDetaild){
		final Writer result = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(result);
		t.printStackTrace(printWriter);
		String stacktrace = result.toString();
		printWriter.close();		
		data = data + " :: "+"stacktrace=" + stacktrace;		
		}
		System.out.println(data+"\n");
		if(isExternalStorageWritable()){
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath();
		String dirName = path + "/RisingStars/";
		try{
			 File dir = new File(dirName);
			 if(!dir.exists())
				{
					dir.mkdirs();
				}
				}catch(Exception e){
					e.printStackTrace();
				}
		file = new File(path + "/moviedb/"+responseLogfileName);
		//File dir = new File(dirName);
		
		}else{
			
			 file= new File(AppController.getInstance().getCacheDir(),
					 responseLogfileName);
				String dirName = AppController.getInstance().getCacheDir() + "/moviedb/";
				try{
			 File dir = new File(dirName);
			 if(!dir.exists())
				{
					dir.mkdirs();
				}
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		if(file.exists()){
			 int file_size = Integer.parseInt(String.valueOf(file.length()/1024));
			 long fileSizeInMB = file_size / 1024;

			 if(fileSizeInMB>2)
				 file.delete() ;
		}
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
			writer.append("\n");
			writer.append(dateFormat.format(System.currentTimeMillis()));//+" :Userid: "+LoginInformation.getInstance().USERID);
			writer.append(" " + data);
			writer.newLine();
			writer.close(); 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/* Checks if external storage is available for read and write */
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
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}
}
