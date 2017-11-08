package com.intigral.moviedb._property;

import android.content.Intent;
import android.graphics.Bitmap;

/*
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class Const {


    public static final int MEDIA_TYPE_IMAGE_PROFILE = 1;
    public static final int MEDIA_TYPE_VIDEO_PROFILE = 2;
    public static final String TAKE_PHOTO = "5";
    public static final String CHOOSE_FROM_GALLERY = "6";
    public static final String CANCEL = "4";

    //    public static final String ADD_TO_CART_URL = BASE_URL +"add_to_cart";
    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int CLICK_IMAGE_REQUEST = 2;
    //register page keys
    public static final int REGISTER_TEACHER = 3;
    public static final int REGISTER_STUDENT = 4;

    public static final int TIME_OUT_CONNECTION = 60000;
    public static final String KEY_IMAGE = "profile_image";
    public static final String PRODUCT_IMAGES = "pic";
    public static final int NUMBER_OF_RECORDS = 10;
    public static final String ASSIGNMENT = "";
    public static final String GCM_SEND_URL = "";
    public static final String MOVIE_DATA ="data" ;


    public static Long TIME_OUT_VALUE = 5000l;


    public static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int CAMERA_CAPTURE_VIDEO_REQUEST_CODE = 200;

    public static final String MEDIA_TYPE_IMAGE = "image";
    public static final String MEDIA_TYPE_VOICE = "audio";
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final int MEDIA_TYPE_IMAGE_PATH = 1;
    public static final int MEDIA_TYPE_VIDEO_PATH = 2;
    public static final int MEDIA_TYPE_PDF = 3;
    public static final int MEDIA_TYPE_AUDIO = 4;
    public static final int ACTION_VIEW = 0;
    public static final int ACTION_DOWNLAOD = 1;
    public static final Bitmap.CompressFormat COMPRESS_TYPE = Bitmap.CompressFormat.JPEG;
    // directory name to store captured images and videos

    public static final int TYPE_OFF_REQUEST_GET_STRING = 1;
    public static final int TYPE_OFF_REQUEST_GET_JSONARRAY = 2;
    public static final int TYPE_OFF_REQUEST_GET_JSONOBJECT = 3;


    public static final int TYPE_OFF_REQUEST_POST_STRING = 1;
    public static final int TYPE_OFF_REQUEST_POST_JSONARRAY = 2;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART = 3;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_POST = 4;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_POST1 = 15;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_REQUEST_TYPE_JSON_POST = 5;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART_PDF = 6;
    public static final int TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART_MULTIPLE_URL = 7;
    public static final String IMAGE_DIRECTORY_NAME = "Pfunzo";
    public final static int SCREENLAYOUT_SIZE_LARGE = 1;
    public final static int SCREENLAYOUT_SIZE_NORMAL = 2;
    public final static int SCREENLAYOUT_SIZE_SMALL = 3;
    public final static int SCREENLAYOUT_SIZE_UNKNOWN = 4;
    public final static int SCREENLAYOUT_SIZE_X_LARGE = 5;
    public final static int SCREENLAYOUT_SIZE_NORMAL_HTC = 6;
    public static final String QUIZ_DATA = "quiz_data";

    public static final String KEY_GCM = "gcm";
    public static final String DEVICE_TYPE = "Android";

    public static String COUNTRY_CODE = "+91";


    public static final String KEY_DES = "hazardDescription";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_DATE = "selectDate";
    public static final String KEY_TIME = "selectTime";
    public static final String KEY_MESAURED = "mesauredBy";


    public static String KEY_START = "offset";
    public static final String KEY_LENGTH = "limit";

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String IMAGE_BASE_URL="http://image.tmdb.org/t/p/w185/";//w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg"  "w92", "w154", "w185", "w342", "w500", "w780"
    public static final String LARGE_IMAGE_BASE_URL="http://image.tmdb.org/t/p/w500/";
    public static final String API_KEY = "&api_key=114fe6670282f6a632638661e5e86dee";

    public static final String POPULAR_MOVIE_URL = BASE_URL + "discover/movie?sort_by=popularity.desc" + API_KEY;
    public static final String TOP_RATED_MOVIE_URL = BASE_URL + "discover/movie/?sort_by=vote_average.desc" + API_KEY;
    public static final String REVENUE_MOVIE_URL = BASE_URL + "discover/movie?sort_by=revenue.desc" + API_KEY;

}
