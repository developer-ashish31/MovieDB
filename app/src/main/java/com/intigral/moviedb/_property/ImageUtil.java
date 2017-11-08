package com.intigral.moviedb._property;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
	Activity activity;
	static String TAG = "ImageUtil" ;
	public ImageUtil(Activity activity){
		this.activity = activity ;
	}
	long getfilesize(String path){
		File file = new File(path);
		return file.length();
	}
	public String compressImage(String imageUri) {

		long fileLen = getfilesize(imageUri); 
		if (LogFile.requestResponse){
			LogFile.requestResponse("++   fileLen:"+AppUtils.humanReadableByteCount(fileLen,false) );			
		}
		if(fileLen<(1024*(70*2))){
			if (LogFile.requestResponse){
				LogFile.requestResponse(TAG+" ++   !!!!!!!!!!!! NO COMPRESSION REQUERIED !!!!!!!!!!!!");			
			}
			return imageUri;
		}
		
		
		String filePath = getRealPathFromURI(imageUri);
		Bitmap scaledBitmap = null;

		BitmapFactory.Options options = new BitmapFactory.Options();

		options.inJustDecodeBounds = true;
		Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

		int actualHeight = options.outHeight;
		int actualWidth = options.outWidth;
//1280x1024
		float maxHeight = 816.0f;
		float maxWidth = 612.0f;
		
//		float maxHeight = 1024.0f;
//		float maxWidth = 1280.0f;
		
		if (LogFile.requestResponse){
			LogFile.requestResponse(TAG+"++   actualHeight:"+actualHeight );
			LogFile.requestResponse(TAG+"++   actualWidth:"+actualWidth );
		}
		
		float imgRatio = actualWidth / actualHeight;
		float maxRatio = maxWidth / maxHeight;

		if (LogFile.requestResponse){
			LogFile.requestResponse(TAG+"++   imgRatio:"+imgRatio );
			LogFile.requestResponse(TAG+"++   maxRatio:"+maxRatio );
		}
		
		if (actualHeight > maxHeight || actualWidth > maxWidth) {
			if (imgRatio < maxRatio) {
				imgRatio = maxHeight / actualHeight;
				actualWidth = (int) (imgRatio * actualWidth);
				actualHeight = (int) maxHeight;
			} else if (imgRatio > maxRatio) {
				imgRatio = maxWidth / actualWidth;
				actualHeight = (int) (imgRatio * actualHeight);
				actualWidth = (int) maxWidth;
			} else {
				actualHeight = (int) maxHeight;
				actualWidth = (int) maxWidth;

			}
		}

		options.inSampleSize = calculateInSampleSize(options, actualWidth,
				actualHeight);

		options.inJustDecodeBounds = false;

		options.inPurgeable = true;
		options.inInputShareable = true;
		options.inTempStorage = new byte[16 * 1024];

		try {
			bmp = BitmapFactory.decodeFile(filePath, options);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
//			return imageUri ;
System.gc();
		}
		try {
			scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
					Bitmap.Config.ARGB_8888);
		} catch (OutOfMemoryError exception) {
			exception.printStackTrace();
			System.gc();
		}

		float ratioX = actualWidth / (float) options.outWidth;
		float ratioY = actualHeight / (float) options.outHeight;
		float middleX = actualWidth / 2.0f;
		float middleY = actualHeight / 2.0f;

		Matrix scaleMatrix = new Matrix();
		scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

		Canvas canvas = new Canvas(scaledBitmap);
		canvas.setMatrix(scaleMatrix);
		canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
				middleY - bmp.getHeight() / 2, new Paint(
						Paint.FILTER_BITMAP_FLAG));

		// check the rotation of the image and display it properly
		ExifInterface exif;
		try {
			exif = new ExifInterface(filePath);

			
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, 0);
			Log.d("EXIF", "Exif: " + orientation);
			
			if (LogFile.requestResponse){
				LogFile.requestResponse(TAG+"++   orientation:"+orientation );			
			}
			
			Matrix matrix = new Matrix();
			if (orientation == 6) {
				matrix.postRotate(90);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 3) {
				matrix.postRotate(180);
				Log.d("EXIF", "Exif: " + orientation);
			} else if (orientation == 8) {
				matrix.postRotate(270);
				Log.d("EXIF", "Exif: " + orientation);
			}
			if (LogFile.requestResponse){
				LogFile.requestResponse(TAG+"++   scaledBitmap.getWidth()111:"+scaledBitmap.getWidth() );
				LogFile.requestResponse(TAG+"++   scaledBitmap.getHeight()111:"+scaledBitmap.getHeight() );
			}
			Bitmap scaledBitmapTemp = Bitmap.createBitmap(scaledBitmap, 0, 0,
					scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
					true);
			scaledBitmap.recycle();
			scaledBitmap = scaledBitmapTemp ;
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileOutputStream out = null;
		String filename = getFilename();
		try {
			
			if (LogFile.requestResponse){
				LogFile.requestResponse("++   scaledBitmap.getWidth()222:"+scaledBitmap.getWidth() );
				LogFile.requestResponse("++   scaledBitmap.getHeight()222:"+scaledBitmap.getHeight() );
			}
			
			out = new FileOutputStream(filename);

			// write the compressed bitmap at the destination specified by
			// filename.
			//scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
			scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 98, out);
			scaledBitmap.recycle();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		scaledBitmap.recycle();
		
		return filename;

	}

	public String getFilename() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), "hungama/Images");
		if (!file.exists()) {
			file.mkdirs();
		}
		String uriSting = (file.getAbsolutePath() + "/"
				+ System.currentTimeMillis() + ".jpg");
		return uriSting;

	}
	public void deleteAllImage() {
		File file = new File(Environment.getExternalStorageDirectory()
				.getPath(), "hungama/Images");
		if (!file.exists()) {
			file.mkdirs();
		}
		if (file.isDirectory()) {
	        String[] children = file.list();
	        for (int i = 0; i < children.length; i++) {
	            new File(file, children[i]).delete();
	        }
	    }
	}

	private String getRealPathFromURI(String contentURI) {
		Uri contentUri = Uri.parse(contentURI);
		Cursor cursor = activity.getContentResolver().query(contentUri, null,
				null, null, null);
		if (cursor == null) {
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int index = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(index);
		}
	}

	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		final float totalPixels = width * height;
		final float totalReqPixelsCap = reqWidth * reqHeight * 2;
		while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
			inSampleSize++;
		}

		return inSampleSize;
	}
	
	public static int calculateInSampleSizeAndroid(
			BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
}
