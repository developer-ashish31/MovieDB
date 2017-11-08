package com.intigral.moviedb._property;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.intigral.moviedb.R;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class UtilitesData {
    public static String sMacAddress;
    public static String sPhoneNumber;
    private static String sIMEINumber;
    public static String sPhoneModel;// = "iPod touch";
    public static String sPhoneLanguage = "1";
    public static String sOSVersion;// = "iPod touch";
    public static String sRegId = null;
    public static String draftPostPath = "/voiz/.draftpostdata/";

    public static void startAnimition(Context context, View view, int animition) {
        Animation animation = AnimationUtils.loadAnimation(context, animition);
        view.startAnimation(animation);
    }

    public static float getDip(int dip, Context context) {
        Resources r = context.getResources();
        float pix = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip,
                r.getDisplayMetrics());
        return pix;
    }

    public static float convertDpToPixel(float dp, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    public static String UrlEncoding(String encodedUrl) {
        String finalUrl = null;
        try {
            finalUrl = URLDecoder.decode(encodedUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return finalUrl;
    }

    /**
     * @param emailstring
     * @return
     */
    public static boolean checkEmail(String emailstring) {
        String pattern = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+";
        Pattern emailPattern = Pattern.compile(pattern);
        Matcher emailMatcher = emailPattern.matcher(emailstring);
        return emailMatcher.matches();
    }

    /**
     * @param path
     * @return
     */
    public static InputStream getFileInputStream(String path) {
        try {
            //if (Logger.ENABLE)
            //System.out
            //.println("----------------getFileInputStream----------------->"
            //	+ path);
            InputStream finput = new FileInputStream(path);
            return finput;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

    /**
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(InputStream inputStream) throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        //System.out.println("-----byteBuffer is availabel : "+inputStream.available());
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
            //System.out.println("-----byteBuffer size : "+byteBuffer.size());
        }
        // inputStream.close() ;
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    /**
     * @param inputStream
     * @param till
     * @return
     * @throws IOException
     */
    public static byte[] readBytes(InputStream inputStream, int till)
            throws IOException {
        // this dynamically extends to take the bytes you read
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

        // this is storage overwritten on each iteration with bytes
        int bufferSize = till;
        byte[] buffer = new byte[bufferSize];

        // we need to know how may bytes were read to write them to the
        // byteBuffer
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
            break;
        }
        inputStream.close();
        // and then we can return your byte array.
        return byteBuffer.toByteArray();
    }

    /**
     * @param value
     * @return
     */
    public static String onMbKbReturnResult(Long value) {
        String size;
        if (value / 1024.0 < 1024.0) {

            size = "" + value / 1024 + "KB";
        } else {
            double sizeInMB = value / 1024.0 / 1024.0;
            String text = Double.toString(sizeInMB);
            int index = text.indexOf('.');
            if (-1 != index && index + 5 < text.length()) {
                text = text.substring(0, index + 5);
            }
            size = "" + text + "MB";
        }

        return size;
    }

    /**
     * @return
     */
    public static long getRandomNumber() {
        return System.currentTimeMillis();// rand.nextInt();
    }

    /**
     * @param sourceFilepath
     * @return
     */
    public static boolean moveFileToSdCard(String sourceFilepath) {

        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel in = null;
        FileChannel out = null;

        try {
            // File root = Environment.getExternalStorageDirectory();
            // File file = new
            // File(root.getAbsolutePath()+"/DCIM/Camera/img.jpg");

            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + "/"
                    + System.currentTimeMillis()
                    + "_RT.jpg";// "/RockeTalk/Chat/";
            File backupFile = new File(path);
            backupFile.createNewFile();
            File sourceFile = new File(sourceFilepath);
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(backupFile);
            byte fData[] = new byte[fis.available()];
            fis.read(fData);
            fos.write(fData);
            // in = fis.getChannel();
            // out = fos.getChannel();
            //
            // long size = in.size();
            // in.transferTo(0, size, out);
            return true;
        } catch (Throwable e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (fis != null)
                    fis.close();
            } catch (Throwable ignore) {
            }

            try {
                if (fos != null)
                    fos.close();
            } catch (Throwable ignore) {
            }

            try {
                if (in != null && in.isOpen())
                    in.close();
            } catch (Throwable ignore) {
            }

            try {
                if (out != null && out.isOpen())
                    out.close();
            } catch (Throwable ignore) {

            }
        }

    }

    /**
     * @param date
     * @return
     */
    public static String getDateString(String date) {// 13/05/2011 9:41:14PM

        try {

            SimpleDateFormat sdf = null;
            sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                // 06-14 11:58:52.970: INFO/System.out(1185): input
                // data:2012-06-14 04:54:12

                if (date.indexOf("-") != -1) {
                    date = convertGMTDateToCurrentGMTDate(date,
                            "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                    sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                } else {
                    sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                }

                Date past = sdf.parse(date);

                // System.out.println(DateFormat.getDateTimeInstance(
                // DateFormat.MEDIUM, DateFormat.SHORT).format(past));

                return DateFormat.getDateTimeInstance(DateFormat.MEDIUM,
                        DateFormat.SHORT).format(past);
                // Calendar cal= Calendar.getInstance();
                // cal.setTime(past);
                // return cal.get(field);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return date;
    }

    /**
     * @param context
     */
    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    /**
     * @param context
     */
    public static void deleteDraftPostData(Context context) {
        try {
            String path = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + draftPostPath;
            File dir = new File(path);
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param context
     */
    public static void deleteData(Context context) {
        try {
            File dir = new File(
                    "/data/data/com.Radio.RadioStation/files/");
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
        }
    }

    /**
     * @param dir
     * @return
     */
    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        if (dir.getAbsolutePath().indexOf(".th") != -1)
            return false;
        // System.out.println("-----------delete file---"+dir.getAbsolutePath());
        return dir.delete();
    }

    /**
     * @param filePath
     * @return
     */
    public static byte[] getFileData(String filePath) {
        try {
            FileInputStream fin = new FileInputStream(filePath);
            byte[] data = UtilitesData.readBytes(fin);// new byte[fin.available()];
            fin.read(data, 0, data.length);
            return data;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return null;
    }

    /**
     * @param context
     * @return
     */
    public static String getPhoneIMEINumberMethod(Context context) {
        if (null != sIMEINumber)
            return sIMEINumber;
        try {
            TelephonyManager telphonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            sIMEINumber = telphonyManager.getDeviceId();
            // System.out.println("-------------sIMEINumber---------"+sIMEINumber);
            // if(sIMEINumber == null || sIMEINumber.equalsIgnoreCase("null")){
            // return sIMEINumber = telphonyManager.getDeviceId();
            // }

            if (sIMEINumber == null) {
                sIMEINumber = "123456789";
            }
        } catch (Exception _ex) {
            if (sIMEINumber == null) {
                sIMEINumber = "123456789";
            }
        }
        return sIMEINumber;
    }


    //public static String


    //{"play":"cid:17::sid:229::cntId:161^cid:17::sid:229::cntId:162","share":"cid:17::sid:229::cntId:161^cid:17::sid:229","common":"value"}
    // {"play":"cid:17::sid:229::cntId:161^cid:17::sid:229::cntId:162","share":"cid:17::sid:229::cntId:161^cid:17::sid:229","common":"value"}

    /**
     * @param play
     * @param Share
     * @return
     */
    public static String JsonWriteForPlay(String play, String Share) {
        JSONObject obj = null;
        JSONObject parent = new JSONObject();


        try {
            obj = new JSONObject();
            obj.put("play", play);
            obj.put("Share", Share);

            //obj.put("sid", new Long(sid));
            //obj.put("cntId", new Long(cntId));
            //JSONArray list = new JSONArray();
            //list.add("msg 1");
            //list.add("msg 2");
            //list.add("msg 3");
            // parent.put("play", play);
            //obj.put("messages", list);
        } catch (Exception e) {

        }
        return obj.toString();
    }


    /**
     * @param cid
     * @param sid
     * @param cntId
     * @return
     */
    public static String JsonWriteForPlay(Long cid, Long sid, Long cntId) {
        JSONObject obj = null;
        JSONObject parent = new JSONObject();


        try {
            obj = new JSONObject();
            obj.put("cid", cid);
            obj.put("sid", sid);
            obj.put("sid", cntId);
            //obj.put("sid", new Long(sid));
            //obj.put("cntId", new Long(cntId));
            //JSONArray list = new JSONArray();
            //list.add("msg 1");
            //list.add("msg 2");
            //list.add("msg 3");
            // parent.put("play", obj);
            //obj.put("messages", list);
        } catch (Exception e) {

        }
        return obj.toString();
    }

    /**
     * @param context
     * @return
     */
    public static int getScreenType(Activity context) {
        //Determine density
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int value = metrics.densityDpi;
            /*switch(value)
            {*/
        if (value == DisplayMetrics.DENSITY_LOW) {
            return Const.SCREENLAYOUT_SIZE_SMALL;
        } else if (value == 240) {
            return Const.SCREENLAYOUT_SIZE_NORMAL;
        } else if (value == DisplayMetrics.DENSITY_DEFAULT) {
            return Const.SCREENLAYOUT_SIZE_NORMAL;
        } else if (value == 320) {
            return Const.SCREENLAYOUT_SIZE_LARGE;
        } else if (value == 480) {
            return Const.SCREENLAYOUT_SIZE_X_LARGE;
        } else if (value == Const.SCREENLAYOUT_SIZE_X_LARGE) {
            return Const.SCREENLAYOUT_SIZE_X_LARGE;
        } else {
            return Const.SCREENLAYOUT_SIZE_LARGE;
        }
	      /*  case DisplayMetrics.DENSITY_LOW://120
	        	return Constants.SCREENLAYOUT_SIZE_SMALL;
	        case 240://240
	        	return Constants.SCREENLAYOUT_SIZE_NORMAL;
	        case DisplayMetrics.DENSITY_DEFAULT://DisplayMetrics.DENSITY_MEDIUM this is 160
	        	return Constants.SCREENLAYOUT_SIZE_NORMAL;
	    	case 320://320
				return Constants.SCREENLAYOUT_SIZE_LARGE;
	    	case 480://Samsung S4, HTC One
				return Constants.SCREENLAYOUT_SIZE_X_LARGE;
				
			default:
				return Constants.SCREENLAYOUT_SIZE_LARGE;
	        }*/
    }

    /**
     * @param timeMillis
     * @return
     */
    public static String converMiliSecond(long timeMillis) {
        long time = timeMillis;/// 1000;
        String seconds = Integer.toString((int) (time % 60));
        String minutes = Integer.toString((int) ((time % 3600) / 60));
        String hours = Integer.toString((int) (time / 3600));
        //System.out.println("time====="+time);
        //System.out.println("seconds====="+seconds);
        //System.out.println("minutes====="+minutes);
        //System.out.println("hours====="+hours);

        for (int i = 0; i < 2; i++) {
            if (seconds.length() < 2) {
                seconds = "0" + seconds;
            }
            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }
            if (hours.length() < 2) {
                hours = "0" + hours;
            }
        }
        return minutes + ":" + seconds;
    }

    /**
     * @param file
     * @return
     * @throws IOException
     */
    public static byte[] readBytesFromFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        // Get the size of the file
        long length = file.length();

        // You cannot create an array using a long type.
        // It needs to be an int type.
        // Before converting to an int type, check
        // to ensure that file is not larger than Integer.MAX_VALUE.
        if (length > Integer.MAX_VALUE) {
            throw new IOException("Could not completely read file " + file.getName() + " as it is too long (" + length + " bytes, max supported " + Integer.MAX_VALUE + ")");
        }

        // Create the byte array to hold the data
        byte[] bytes = new byte[(int) length];

        // Read in the bytes
        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        // Ensure all the bytes have been read in
        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        // Close the input stream and return bytes
        is.close();
        return bytes;
    }

    /**
     * @param timeMillis
     * @return
     */
    public static String converMiliSecondForAudioStatus(long timeMillis) {
        long time = timeMillis / 1000;
        String seconds = (Integer.toString((int) (time % 60)));
        String minutes = Integer.toString((int) ((time % 3600) / 60));
        if ((int) ((time % 3600) / 60) >= 1) {

        }

        String hours = Integer.toString((int) (time / 3600));
        //System.out.println("time====="+time);
        //System.out.println("seconds====="+seconds);
        //System.out.println("minutes====="+minutes);
        //System.out.println("hours====="+hours);

        for (int i = 0; i < 2; i++) {
            if (seconds.length() < 2) {
                seconds = "" + seconds;
            }
            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }
            if (hours.length() < 2) {
                hours = "0" + hours;
            }
        }
        return minutes + ":" + seconds;
    }

    /**
     * @param timeMillis
     * @return
     */
    public static String converMiliSecondForAudioStatusRecorded(long timeMillis) {
        long time = timeMillis / 1000;
        String seconds = (Integer.toString((int) (29 - time % 60)));
        String minutes = Integer.toString((int) ((time % 3600) / 60));
        String hours = Integer.toString((int) (time / 3600));
        //System.out.println("time====="+time);
        //System.out.println("seconds====="+seconds);
        //System.out.println("minutes====="+minutes);
        //System.out.println("hours====="+hours);

        for (int i = 0; i < 2; i++) {
            if (seconds.length() < 2) {
                seconds = "" + seconds;
            }
            if (minutes.length() < 2) {
                minutes = "0" + minutes;
            }
            if (hours.length() < 2) {
                hours = "0" + hours;
            }
        }
        return seconds;
    }


    /**
     * @param target
     * @return
     */
    public final static String asLowerCaseFirstChar(final String target) {

        if ((target == null) || (target.length() == 0)) {
            return target; // You could omit this check and simply live with an
            // exception if you like
        }
        return Character.toLowerCase(target.charAt(0))
                + (target.length() > 1 ? target.substring(1) : "");
    }

    /**
     * returns the string, the first char uppercase
     *
     * @param target
     * @return
     */
    /**
     * @param target
     * @return
     */
    public final static String asUpperCaseFirstChar(final String target) {

        if ((target == null) || (target.length() == 0)) {
            return target; // You could omit this check and simply live with an
            // exception if you like
        }
        return Character.toUpperCase(target.charAt(0))
                + (target.length() > 1 ? target.substring(1) : "");
    }


    /**
     * @param string
     * @return
     */
    public static String capitalizeString(String string) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i] == '.' || chars[i] == '\'') { // You can add other chars here
                found = false;
            }
        }
        return String.valueOf(chars);
    }
    //2013-12-23 05:46:27

    /**
     * @param date
     * @return
     */
    public static String compareDate(String date) {// 13/05/2011 9:41:14PM

        try {
            // System.out.println("------------date : " + date);
            date = date.trim();
            date = date.replaceAll("\n", " ");
            StringBuilder time = new StringBuilder();
            SimpleDateFormat sdf = null;

            if (date.indexOf("-") != -1) {
                date = convertGMTDateToCurrentGMTDate(date,
                        "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
                sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            } else {
                sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            }

            // sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            // sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date past = sdf.parse(date);
            Date now = new Date();
            // System.out.println(past.toString()+"-----------"+now.toString());
            String s = sdf.format(now);
            now = sdf.parse(s);
            long agosecond = Math.abs(TimeUnit.MILLISECONDS.toSeconds(now
                    .getTime() - past.getTime()));

            int seconds = (int) (agosecond % 60);
            int minutes = (int) ((agosecond / 60) % 60);
            int hours = (int) ((agosecond / 3600) % 24);

            long agodays = ((agosecond / 86400));
            // System.out.println("agodays===="+agodays);
            long month = ((agodays / 30));
            if (month > 0) {
                time.append(month);
                if (month == 1)
                    time.append(" month ago");
                else
                    time.append(" months ago");
                return time.toString();
            } else if (agodays > 0) {
                time.append(agodays);
                if (agodays == 1)
                    time.append(" day ago");
                else
                    time.append(" days ago");
                return time.toString();
            } else if (hours > 0) {
                if (hours == 1) {
                    time.append(hours);
                    time.append("h");
                } else {

                    time.append(hours);
                    time.append("h");
                }
                if (minutes > 0) {
                    // time.append(" and ");
                    time.append(" ");
                    time.append(minutes);

                    if (minutes == 1)
                        time.append("m ago");
                    else
                        time.append("m ago");
                } else {
                    time.append(" ago");
                }
                return time.toString();
            } else if (minutes > 0) {

                time.append(minutes);
                if (minutes == 1)
                    time.append("m");
                else
                    time.append("m");
                if (seconds > 0) {
                    // time.append(" and ");
                    time.append(" ");
                    time.append(seconds);
                    if (seconds == 1)
                        time.append("s ago");
                    else
                        time.append("s ago");
                } else {
                    time.append(" ago");
                }
                return time.toString();
            } else {
                time.append(agosecond);
                if (agosecond == 1)
                    time.append("s ago");
                else
                    time.append("s ago");
                return time.toString();
            }

        } catch (Exception ex) {
            if (date != null)
                return date;
            ex.printStackTrace();
            return "NA";
        }

    }

    /**
     * @param value
     * @return
     */
    public static long CompareTime(long value) {

        Long xx = (System.currentTimeMillis() - value) / 1000;
			/*
			 * java.util.Date past = new Date(value);//sdf.parse(date);
			 * java.util.Date now = new java.util.Date(); //
			 * System.out.println(past.toString()+"-----------"+now.toString()); //
			 * String s = sdf.format(now); // now = sdf.parse(s); long agosecond =
			 * Math.abs(TimeUnit.MILLISECONDS.toSeconds(now .getTime() -
			 * past.getTime()));
			 * 
			 * int seconds = (int) (agosecond % 60);
			 */
        // System.out.println("xx===="+xx);
        return xx;
    }

    /**
     * @param date
     * @return
     */
    public static String compareDate(long date) {// 13/05/2011 9:41:14PM

        try {
            // System.out.println("------------date : " + date);
            // date = date.trim();
            // date = date.replaceAll("\n", " ");
            StringBuilder time = new StringBuilder();
            // SimpleDateFormat sdf = null;
            //
            // if (date.indexOf("-") != -1) {
            // date = DateFormatUtils.convertGMTDateToCurrentGMTDate(date,
            // "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss");
            // sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // } else {
            // sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            // }

            // sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            // sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date past = new Date(date);// sdf.parse(date);
            Date now = new Date();
            // System.out.println(past.toString()+"-----------"+now.toString());
            // String s = sdf.format(now);
            // now = sdf.parse(s);
            long agosecond = Math.abs(TimeUnit.MILLISECONDS.toSeconds(now
                    .getTime() - past.getTime()));

            // System.out.println("-------compareDate-----now-----"+now
            // .getTime());
            // System.out.println("-------compareDate----server-----"+past
            // .getTime());

            int seconds = (int) (agosecond % 60);
            int minutes = (int) ((agosecond / 60) % 60);
            int hours = (int) ((agosecond / 3600) % 24);

            long agodays = ((agosecond / 86400));
            long month = ((agodays / 30));
            if (month > 0) {
                time.append(month);
                if (month == 1)
                    time.append(" month ago");
                else
                    time.append(" months ago");

                if (month > 12)
                    return (new Date()).toLocaleString();// oGMTString();///Sep
                // 14, 2013 12:33:19
                // AM

                return time.toString();
            } else if (agodays > 0) {
                time.append(agodays);
                if (agodays == 1)
                    time.append(" day ago");
                else
                    time.append(" days ago");
                return time.toString();
            } else if (hours > 0) {
                if (hours == 1) {
                    time.append(hours);
                    time.append("h");
                } else {

                    time.append(hours);
                    time.append("h");
                }
                if (minutes > 0) {
                    // time.append(" and ");
                    time.append(" ");
                    time.append(minutes);

                    if (minutes == 1)
                        time.append("m ago");
                    else
                        time.append("m ago");
                } else {
                    time.append(" ago");
                }
                return time.toString();
            } else if (minutes > 0) {

                time.append(minutes);
                if (minutes == 1)
                    time.append("m");
                else
                    time.append("m");
                if (seconds > 0) {
                    // time.append(" and ");
                    time.append(" ");
                    time.append(seconds);
                    if (seconds == 1)
                        time.append("s ago");
                    else
                        time.append("s ago");
                } else {
                    time.append(" ago");
                }
                return time.toString();
            } else {
                time.append(agosecond);
                if (agosecond == 1)
                    time.append("s ago");
                else
                    time.append("s ago");
                return time.toString();
            }

        } catch (Exception ex) {
            // if (date != null)

            ex.printStackTrace();
            return "" + date;
        }
    }

    /**
     * @param classname
     * @return
     */
    public static String makeJson(Object classname) {
        String makeJson = new Gson().toJson(classname);
        return makeJson;
    }

    //07/12/1981

    /**
     * @param inputFormat
     * @param outputFormat
     * @param inputDate
     * @return
     */
    public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

        Date parsed = null;
        String outputDate = "";

        SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
        SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());
        try {
            parsed = df_input.parse(inputDate);
            outputDate = df_output.format(parsed);

        } catch (ParseException e) {
        }
        return outputDate;
    }

    public static byte[] inputStreamToByteArray(InputStream inputStream) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[100];
        try {
            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            buffer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toByteArray();
    }

    public static void writeFile(byte[] data, File f, int id) {

        FileOutputStream out = null;
        if (f != null && !f.exists() && data != null) {
            try {

                f.createNewFile();
                out = new FileOutputStream(f);
                out.write(data);

            } catch (Exception e) {
            } finally {
                try {
                    if (out != null)
                        out.close();
                } catch (Exception ex) {
                }
            }
        }
    }
		/*public void shareToGMail(String[] email, String subject, String content) {
		    Intent emailIntent = new Intent(Intent.ACTION_SEND);
		    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
		    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		    emailIntent.setType("text/plain");
		    emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, content);
		    final PackageManager pm = getPackageManager();
		    final List<ResolveInfo> matches = pm.queryIntentActivities(emailIntent, 0);
		    ResolveInfo best = null;
		    for(final ResolveInfo info : matches)
		        if (info.activityInfo.packageName.endsWith(".gm") || info.activityInfo.name.toLowerCase().contains("gmail"))
		            best = info;
		    if (best != null)
		        emailIntent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
		    this.startActivity(emailIntent);
		}*/

    public static String getTopActivityStackName(Context context) {
        ActivityManager mActivityManager = (ActivityManager) context.
                getSystemService(Activity.ACTIVITY_SERVICE);
        PackageManager mPackageManager = context.getPackageManager();
        String packageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        ApplicationInfo mApplicationInfo;
        try {
            mApplicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
        } catch (NameNotFoundException e) {
            mApplicationInfo = null;
        }
        String appName = (String) (mApplicationInfo != null ?
                mPackageManager.getApplicationLabel(mApplicationInfo) : "(unknown)");

        return appName;
    }

    public static String encodeText(String query) {
        String result = "";
        try {
            result = URLEncoder.encode(query, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static void startNewActivity(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (intent == null) {
            // Bring user to the market or let them choose an app?
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=" + packageName));
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String convertDate(String date, String currentFormat, String newFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(currentFormat);
        Date d = formatter.parse(date);
        SimpleDateFormat newFormatter = new SimpleDateFormat(newFormat);
        return newFormatter.format(d);
    }

    public static String convertGMTDateToCurrentGMTDate(String date, String currentFormat, String newFormat) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(currentFormat);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date d = formatter.parse(date);
        SimpleDateFormat newFormatter = new SimpleDateFormat(newFormat);
        newFormatter.setTimeZone(TimeZone.getDefault());
        return newFormatter.format(d);
    }


    public static void share_app(Context context, String packageName) {
        try {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("text/plain");
            // intent.setType("image/*");
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            // Add data to the intent, the receiving app will decide what to do with it.
            // intent.putExtra(Intent.EXTRA_STREAM,uri);
            intent.putExtra(Intent.EXTRA_SUBJECT,
                    context.getResources().getString(R.string.shareApp_Subject));
            intent.putExtra(Intent.EXTRA_EMAIL,"moviedb@gmail.com");

            String s = "" +
                    "Download App Now!!" +
                    "Check it out!"
                    + "\r\n\r\n "
                    + AppUtils.getAppDownloadLink(context)
                    + "";
            intent.putExtra(Intent.EXTRA_TEXT, s);

            context.startActivity(Intent.createChooser(intent, "share this app"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSnackBar(View coordinatorLayout, String str, Activity activity) {
        Snackbar snack = Snackbar.make(coordinatorLayout, str, Snackbar.LENGTH_LONG);
        snack.setActionTextColor(ContextCompat.getColor(activity, R.color.black));
        View view = snack.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(params);
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.orange));
        snack.show();
    }

    public static void showSnackBarCordinate(View coordinatorLayout, String str, Activity activity) {
        Snackbar snack = Snackbar.make(coordinatorLayout, str, Snackbar.LENGTH_LONG);
        snack.setActionTextColor(ContextCompat.getColor(activity, R.color.black));
        View view = snack.getView();
        TextView textView = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
        view.setLayoutParams(params);
        view.setBackgroundColor(ContextCompat.getColor(activity, R.color.orange));
        snack.show();
    }


    public static void hideKeywoead(Activity activity) {
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
