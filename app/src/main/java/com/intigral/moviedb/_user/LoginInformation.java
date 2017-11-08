package com.intigral.moviedb._user;
/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */

import android.content.Context;
import android.content.SharedPreferences;


public class LoginInformation {
//    public  String AGENCY_ID;
    public String USER_NAME;
    public Boolean LOGIN_STATUS;
    public String CONTACT_NUMBER;
    public String EMAIL;
    public String USER_ID;
    public String USERNAME;
    public String FIRSTNAME;
//    public String LASTNAME;
    private String SETTING_DB = "cycleLaw";
//    public String REGISTRATION_TYPE;
    public String PROFILEIMAGE;
//    public String PASSWORD;
    public String GCM_KEY;
    public static LoginInformation self;
    public Context context;
//    public String USER_TYPE_ID;
    public String DEVICE_TYPE;
    public String COMPANY_NAME;
    public String COMPANY_PHONE;
    public String COMPANY_EMAIL;
    public String COMPANY_MOBILE;
    public String COMPANY_FAX;
    public String COMPANY_ADDRESS;
    public String COMPANY_CITY;
    public String COMPANY_COUNTRY;
    public String VEHICLE_NO;

    public static LoginInformation getInstance() {
        if (self == null || self.context == null) {
            self = new LoginInformation();

            if (self.context != null)
                self.loadSetting();
        }
        return self;
    }

    public static void createInstance(Context context) {
        synchronized (LoginInformation.class) {
            if (null == self) {
                self = new LoginInformation();
                self.context = context;
                self.loadSetting();
            }
        }
    }

    public void loadSetting() {
        if (this.context != null) {
            try {
                SharedPreferences pref = this.context.getSharedPreferences(SETTING_DB, Context.MODE_PRIVATE);
                this.USER_ID = pref.getString("USERID", null);
                this.USERNAME = pref.getString("USERNAME", null);
//                this.REGISTERATIONID = pref.getString("REGISTERATIONID", null);
                this.FIRSTNAME = pref.getString("FIRSTNAME", null);
//                this.LASTNAME = pref.getString("LASTNAME", null);

                this.PROFILEIMAGE = pref.getString("PROFILEIMAGE", null);
//                this.PASSWORD = pref.getString("PASSWORD", null);
                this.CONTACT_NUMBER = pref.getString("CONTACTNUMBER", null);
                this.EMAIL = pref.getString("EMAIL", null);
//                this.REGISTRATION_TYPE = pref.getString("REGISTRATION_TYPE", null);

//                this.SCHOOLID = pref.getString("SCHOOLID", null);
//                this.SCHOOLNAME = pref.getString("SCHOOLNAME", null);
//                this.MIDLENAME = pref.getString("MIDLENAME", null);
                this.LOGIN_STATUS = pref.getBoolean("LOGIN_STATUS", false);
//                this.PASSWORD = pref.getString("PASSWORD", null);
                this.GCM_KEY = pref.getString("GCM_KEY", null);
//                this.USER_TYPE_ID = pref.getString("USER_TYPE_ID", null);
                this.DEVICE_TYPE = pref.getString("DEVICE_TYPE", null);
                this.USER_NAME = pref.getString("USER_NAME", null);
//                this.AGENCY_ID = pref.getString("AGENCY_ID", null);
                this.COMPANY_NAME = pref.getString("COMPANY_NAME", null);
                this.COMPANY_PHONE = pref.getString("COMPANY_PHONE", null);
                this.COMPANY_EMAIL = pref.getString("COMPANY_EMAIL", null);
                this.COMPANY_MOBILE = pref.getString("COMPANY_MOBILE", null);
                this.COMPANY_FAX = pref.getString("COMPANY_FAX", null);
                this.COMPANY_ADDRESS = pref.getString("COMPANY_ADDRESS", null);
                this.COMPANY_CITY = pref.getString("COMPANY_CITY", null);
                this.COMPANY_COUNTRY = pref.getString("COMPANY_COUNTRY", null);
                this.VEHICLE_NO = pref.getString("VEHICLE_NO", null);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void clear() {
        SharedPreferences.Editor editor = this.context.getSharedPreferences(SETTING_DB, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();
    }

    public boolean updateSetting() {

        SharedPreferences.Editor editor = null;
        if (this.context != null) {
            try {
                editor = this.context.getSharedPreferences(SETTING_DB, Context.MODE_PRIVATE).edit();
                editor.putString("USERID", this.USER_ID);
                editor.putString("USERNAME", this.USERNAME);
                editor.putString("FIRSTNAME", this.FIRSTNAME);
//                editor.putString("LASTNAME", this.LASTNAME);
                editor.putString("PROFILEIMAGE", this.PROFILEIMAGE);
//                editor.putString("PASSWORD", this.PASSWORD);
                editor.putString("CONTACTNUMBER", this.CONTACT_NUMBER);
                editor.putString("EMAIL", this.EMAIL);
//                editor.putString("REGISTRATION_TYPE", this.REGISTRATION_TYPE);
                editor.putBoolean("LOGIN_STATUS", this.LOGIN_STATUS);// to save the login instance here in order to get auto login
//                editor.putString("PASSWORD", this.PASSWORD);
                editor.putString("GCM_KEY", this.GCM_KEY);
//                editor.putString("USER_TYPE_ID", this.USER_TYPE_ID);
                editor.putString("DEVICE_TYPE", this.DEVICE_TYPE);
                editor.putString("USER_NAME", this.USER_NAME);
//                editor.putString("AGENCY_ID", this.AGENCY_ID);
                editor.putString("COMPANY_NAME", this.COMPANY_NAME);
                editor.putString("COMPANY_PHONE", this.COMPANY_PHONE);
                editor.putString("COMPANY_EMAIL", this.COMPANY_EMAIL);
                editor.putString("COMPANY_MOBILE", this.COMPANY_MOBILE);
                editor.putString("COMPANY_FAX", this.COMPANY_FAX);
                editor.putString("COMPANY_ADDRESS", this.COMPANY_ADDRESS);
                editor.putString("COMPANY_CITY", this.COMPANY_CITY);
                editor.putString("COMPANY_COUNTRY", this.COMPANY_COUNTRY);
                editor.putString("VEHICLE_NO", this.VEHICLE_NO);


                return editor.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //System.out.println("this.USERNAME==update=="+this.USERNAME);
        return false;
    }


}
