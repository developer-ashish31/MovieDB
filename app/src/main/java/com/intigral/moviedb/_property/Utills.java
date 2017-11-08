package com.intigral.moviedb._property;
/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Utills {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    // validating password with retype password
    public  static boolean isValidPassword(String pass) {
        if (pass != null && pass.length() > 5) {
            return true;
        }
        return false;
    }

    public static boolean isFirstName(String name){
        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
    }

    public static boolean isLastName(String name){
        if (name != null && name.length() > 0) {
            return true;
        }
        return false;
    }


    public static boolean isBothPasswordEqual(String pass, String confirmPass){
        if(pass!=null && confirmPass!=null && pass.endsWith(confirmPass)) {
            return true;
        }
        return false;
    }
    public static boolean isMobileNumber(String pass){
        if(pass!=null  && pass.length()==10) {
            return true;
        }
        return false;
    }


    public static boolean isUserName(String userName){
        if(userName!=null  && userName.length()>8) {
            return true;
        }
        return false;
    }
    public static boolean isfeedbackData(String feedback){
        if(feedback!=null  && feedback.length()>1) {
            return true;
        }
        return false;
    }

    public static byte[] streamToByteArray(FileInputStream stream) throws IOException {
        try {
            byte[] buffer = new byte[1024];

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            int line = 0;
            // read bytes from stream, and store them in buffer
            while ((line = stream.read(buffer)) != -1) {
                // Writes bytes from byte array (buffer) into output stream.
                os.write(buffer, 0, line);
            }
            stream.close();
            os.flush();
            os.close();
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
           return  null;
        }
    }



}
