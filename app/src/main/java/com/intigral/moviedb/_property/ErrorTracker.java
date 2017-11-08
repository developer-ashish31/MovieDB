package com.intigral.moviedb._property;

/**
 * Created by AKM on 01,February,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class ErrorTracker {
    public static final int SUCCESS = 1;
    public static final int ERROR_GENERIC = 2;
    public static final int ERROR_METHOD_NOT_ALLOWED = 3;
    public static final int ERROR_EMAIL_EXISTS = 4;
    public static final int ERROR_USERNAME_EXISTS = 5;
    public static final int ERROR_EMAIL_NOT_EXISTS = 6;
    public static final int ERROR_USERNAME_NOT_EXISTS = 7;
    public static final int ERROR_BLANK = 8;
    public static final int INCORRECT_CREDENTIALS = 9;
    public static final int ERROR_USER_INACTIVE = 10;
    public static final int ERROR_NOT_EXISTS = 11;
    public static final int PERMISSION_DENIED = 12;
    public static final int ERROR_USER_NOT_EXISTS = 13;
    public static final int ERROR_UPLOAD = 14;
    public static final int ERROR_NO_CLASS = 15;

    public static final int ERROR_FNAME_BLANK = 16;
    public static final int ERROR_LNAME_BLANK = 17;
    public static final int ERROR_MOBILE_BLANK = 18;
    public static final int ERROR_EMAIL_BLANK = 19;
    public static final int ERROR_SCHOOLID_BLANK = 20;
    public static final int ERROR_UTID_BLANK = 21;
    public static final int ERROR_PASSWORD_BLANK = 22;
    public static final int ERROR_USERNAME_BLANK = 23;
    public static final int ERROR_REGISTERTYPE_BLANK = 24;
    public static final int ERROR_SCHOOL_NOT_EXISTS = 25;
    public static final int ERROR_UTYPE_NOT_EXISTS = 26;
    public static final int ERROR_NO_RECORDS = 27;
    public static final int ERROR_ALREADY_LIKED = 28;
    public static final int IMAGE_EXISTS =29;
    public static final int ERROR_VIDEO_EXISTS =30;
    public static final int ERROR_ASSIGNMENT_SUBMIT_IN_CORRECT_TIME=36;
    public static final int ERROR_ASSIGNMENT_SUBMIT_IN_LATE=37;
    public static final int ERROR_ALLREADY_SUBMITTED = 38;
    public static final int ERROR_NOT_TRAINER=31;
     public static final int ERROR_CATEGORY_BLANK=32;
    public static final int ERROR_TOPIC_BLANK=33;
    public static final int ERROR_SHORT_DESCRIPTION_BLANK=34;
    public static final int ERROR_LONG_DESCRIPTION_BLANK=35;
    public static String getError(int errorType) {
        switch (errorType) {
            case SUCCESS:
                return "Success";

            case ERROR_METHOD_NOT_ALLOWED:
                return "Not allowed";

            case ERROR_EMAIL_EXISTS:
                return "Email exits";

            case ERROR_USERNAME_EXISTS:
                return "User name exits";

            case ERROR_EMAIL_NOT_EXISTS:
                return "Email not exits";

            case ERROR_USERNAME_NOT_EXISTS:
                return "User name not exits";

            case ERROR_BLANK:
                return "Blank data";

            case INCORRECT_CREDENTIALS:
                return "Incorrect credentials";

            case ERROR_USER_INACTIVE:
                return "User inactive";

            case ERROR_NOT_EXISTS:
                return "Not exists";

            case PERMISSION_DENIED:
                return "Permission Denied";

            case ERROR_USER_NOT_EXISTS:
                return "User not exists";

            case ERROR_UPLOAD:
                return "Upload error";

            case ERROR_NO_CLASS:
                return "No class";
            case ERROR_FNAME_BLANK:
                return "First name blank";
            case ERROR_LNAME_BLANK:
                return "Last name blank";
            case ERROR_MOBILE_BLANK:
                return "Mobile blank";
            case ERROR_EMAIL_BLANK:
                return "Email blank";
            case ERROR_SCHOOLID_BLANK:
                return "School id blank";
            case ERROR_UTID_BLANK:
                return "User type id blank";
            case ERROR_PASSWORD_BLANK:
                return "Password blank";
            case ERROR_USERNAME_BLANK:
                return "User name blank";
            case ERROR_REGISTERTYPE_BLANK:
                return "Registration type blank";
            case ERROR_SCHOOL_NOT_EXISTS:
                return "School not exits";
            case ERROR_UTYPE_NOT_EXISTS:
                return "User type not exits";
            case ERROR_NO_RECORDS:
                return "No records";
            case ERROR_ALREADY_LIKED:
                return "Already liked";
            case IMAGE_EXISTS :
                return "Already viewed";
            case ERROR_VIDEO_EXISTS:
                return "Already viewed";
            case ERROR_ASSIGNMENT_SUBMIT_IN_CORRECT_TIME:
                return "Sumbitted in right time";
            case ERROR_ASSIGNMENT_SUBMIT_IN_LATE:
                return "Sorry! you are late, assignment date expire";
            case ERROR_ALLREADY_SUBMITTED:
                return "Sorry! you have already sumitted this assignamnet";
            case  ERROR_NOT_TRAINER:
                return "Not Trainer";
            case  ERROR_CATEGORY_BLANK:
                return "Category blank";
            case  ERROR_TOPIC_BLANK:
                return "Topic blank";
            case  ERROR_SHORT_DESCRIPTION_BLANK:
                return "Short description blank ";
            case  ERROR_LONG_DESCRIPTION_BLANK:
                   return "Description blank";
            default:
                return "have no signature";


        }

    }
}
