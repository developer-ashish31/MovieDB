package com.intigral.moviedb._property;

/**
 * Created by AKM on 02,February,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.TimeZone;


public class ClientParam {
    public static final String HEADER_CLIENT_PARAM = "client_param";
    public String VENDOR = "vendor";
    public String TIME_ZONE = "timezone";
    public String MOBILE_MODEL = "mobilemodel";
    public String PLATFORM = "platform";
    public String PLATFORM_VERSION = "platformversion";
    public String DEVICE_TOKEN = "devicetoken";
    public String CLIENT_VERSION = "clientversion";
    public String IMEI = "imei";
    public String DISTRIBUTOR = "distributor";
    public String LOCALIZATION = "localization";
    public String REQUEST_TYPE = "requesttype";
    public String LONGITUDE = "longitude";
    public String LATITUDE = "latitude";
    public String LOCATION_ACCURACY = "accuracy";
    public String COUNTRY_ISOCODE = "isocountrycode";
    public String CLIENT_REQUEST_ID = "clientrequestid";
    public String SOURCE = "source";
    public String NETWORK_TYPE = "networkType";
    public String VERSION_CODE = "version_code";

    static ClientParam clientParam ;


    public String timeZone ;
    public String mobileModel= Build.MODEL ;
    public String platform ="ANDROID";
    public String platformVersion = Build.VERSION.RELEASE;
    public String deviceToken ;
    public static final String client_version="PFUNZO_AND_1_0_0" ;//live
//    public String client_version="JB_AND_1_1_8" ;//hard
//    public String client_version="JB_AND_1_1_9" ;//soft

    // public String distributor = "Mobango" ;
    // public String distributor = "Mobile9" ;
    //public String distributor = "Brothersoft" ;
    //public String distributor = "Nexva" ;
    // public String distributor = "Softonics" ;
    //public String distributor = "Appsapk" ;
    //public String distributor = "appszoom" ;
    //public String distributor = "mobile24" ;
    // public String distributor = "Amazon" ;
    // public String distributor = "opera browser";
    public String distributor = "playstore" ;
//    public String distributor = "blackberry" ;





    public String imei ;
    public String localization= "en"; ;
    public String requestType ;
    public String longitude="0.0" ;
    public String latitude="0.0" ;
    public String vendor = "SOURCE_SOFT_SOLUTION" ;
    public String locationAccuracy="0.0" ;
    public String countryIsocode ;
    public String clientRequest_id ;
    public String source ;
    public String networkType ;
    public String versionCode ;



    public static ClientParam getInstance(Context context){
        if(clientParam==null){
            clientParam = new ClientParam() ;
            clientParam.getPhoneIMEINumber(context) ;

            try {
                clientParam.versionCode = ""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode ;
                String verson = ""+context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName ;
            } catch (NameNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            TelephonyManager telephonyManager =((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE));
            if(telephonyManager!=null){
//			  operatorName = telephonyManager.getNetworkOperatorName();
//		      operatorSimName = telephonyManager.getSimOperatorName();
                clientParam.countryIsocode = telephonyManager.getNetworkCountryIso();
//		     // mobileNumber=telephonyManager.getLine1Number();
//		      //simSerialNumber=telephonyManager.getSimSerialNumber();
//		     setInstance(new ImeiGenrator(context));
                // String IMEI = instance.getIMEINumber();

            }
        }

        return clientParam;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getVendor() {
        return vendor;
    }
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
    public String getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }
    public String getTimeZone() {
        TimeZone tz = TimeZone.getDefault();
        timeZone = tz.getID();//tz.toString() ;
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;

    }
    public String getMobileModel() {
        return mobileModel;
    }
    public void setMobileModel(String mobileModel) {
        this.mobileModel = mobileModel;
    }
    public String getPlatform() {
        return platform;
    }
    public void setPlatform(String platform) {
        this.platform = platform;
    }
    public String getPlatformVersion() {
        return platformVersion;
    }
    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }
    public String getDeviceToken() {
        return deviceToken;
    }
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    public String getClient_version() {
        return client_version;
    }
    //	public void setClient_version(String client_version) {
//		this.client_version = client_version;
//	}
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }
    public String getDistributor() {
        return distributor;
    }
    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }
    public String getLocalization() {
        return localization;
    }
    public void setLocalization(String localization) {
        this.localization = localization;
    }
    public String getRequestType() {
        return requestType;
    }
    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLocationAccuracy() {
        return locationAccuracy;
    }
    public void setLocationAccuracy(String locationAccuracy) {
        this.locationAccuracy = locationAccuracy;
    }
    public String getCountryIsocode() {
        return countryIsocode;
    }
    public void setCountryIsocode(String countryIsocode) {
        this.countryIsocode = countryIsocode;
    }
    public String getClientRequest_id() {
        return clientRequest_id;
    }
    public void setClientRequest_id(String clientRequest_id) {
        this.clientRequest_id = clientRequest_id;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getNetworkType() {
        networkType ="gprs";//=  NetworkChangeReceiver.NET_TYPE;
        return networkType;
    }
    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    public String getPhoneIMEINumber(Context context) {
        if (null != imei)
            return imei;
        try {
            TelephonyManager telphonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            imei = telphonyManager.getDeviceId();
        } catch (Exception _ex) {

        }
        return imei;
    }
    @Override
    public String toString() {
        return "\n\nClientParam [timeZone=" + getTimeZone() + ", mobileModel="
                + mobileModel + ", platform=" + platform + ", platformVersion="
                + platformVersion + ", deviceToken=" + deviceToken
                + ", client_version=" + client_version + ", distributor="
                + distributor + ", imei=" + imei + ", localization="
                + localization + ", requestType=" + requestType
                + ", longitude=" + longitude + ", latitude=" + latitude
                + ", vendor=" + vendor + ", locationAccuracy="
                + locationAccuracy + ", countryIsocode=" + countryIsocode
                + ", clientRequest_id=" + clientRequest_id + ", source="
                + source + ", networkType=" + getNetworkType()+ "]";
    }
    //PLATFORM##ANDROID::CLIENTVERSION##JB_AND_1_1_8
    String colSep = "##";
    String rowSep = "::";
    public String getParam(String requestId){

        Log.d(HEADER_CLIENT_PARAM, "requestId:"+requestId);
        StringBuffer buffer = new StringBuffer() ;
        buffer.append(VENDOR+colSep+getVendor()+rowSep);
        buffer.append(TIME_ZONE+colSep+getTimeZone()+rowSep);
        buffer.append(MOBILE_MODEL+colSep+getMobileModel()+rowSep);
        buffer.append(PLATFORM+colSep+getPlatform()+rowSep);
        buffer.append(PLATFORM_VERSION+colSep+getPlatformVersion()+rowSep);
        if(getDeviceToken()!=null && getDeviceToken().length()>0 && !getDeviceToken().trim().equalsIgnoreCase("null"))
            buffer.append(DEVICE_TOKEN+colSep+getDeviceToken()+rowSep);
        buffer.append(CLIENT_VERSION+colSep+getClient_version()+rowSep);
        buffer.append(IMEI+colSep+getImei()+rowSep);
        buffer.append(DISTRIBUTOR+colSep+getDistributor()+rowSep);
        buffer.append(LOCALIZATION+colSep+getLocalization()+rowSep);
        buffer.append(REQUEST_TYPE+colSep+getRequestType()+rowSep);
        buffer.append(LONGITUDE+colSep+getLongitude()+rowSep);
        buffer.append(LATITUDE+colSep+getLatitude()+rowSep);
        buffer.append(LOCATION_ACCURACY+colSep+getLocationAccuracy()+rowSep);
        buffer.append(COUNTRY_ISOCODE+colSep+getCountryIsocode()+rowSep);
        buffer.append(CLIENT_REQUEST_ID+colSep+getRequestType()+rowSep);
        buffer.append(SOURCE+colSep+getSource()+rowSep);
        buffer.append(NETWORK_TYPE+colSep+getNetworkType()+rowSep);
        buffer.append(VERSION_CODE+colSep+getVersionCode());


        return buffer.toString();
    }
}
