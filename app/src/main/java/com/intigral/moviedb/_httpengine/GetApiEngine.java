package com.intigral.moviedb._httpengine;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.intigral.moviedb._app.AppController;
import com.intigral.moviedb._init.RequestResponseGet;
import com.intigral.moviedb._property.Const;
import com.intigral.moviedb._property.LogFile;

import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class GetApiEngine {
    int Id;
    Context mCcontext;
    String Url;
    String TAG="GetApiEngine";
    String requestType;
    RequestResponseGet requestResponseGet;
    ProgressDialog pDialog;
    public  GetApiEngine(Context mCcontext, String Url, String message, int id, RequestResponseGet requestResponseGet, String requestType){
        this.mCcontext=mCcontext;
        this.requestResponseGet =requestResponseGet;
        this.Url=Url;
        this.Id=id;
        this.requestType=requestType;
        Log.v("requestType::Url" , Url);
        CallApi(Id, Url, message, requestType);


        if(LogFile.LoggerFlag){
            LogFile.requestResponse(message.toString() + "  Url:="+Url  + " RequestType:="+requestType);
        }
    }
        public void CallApi(int in, String url, String message, final String requestType)
        {
        switch(in){
        case Const.TYPE_OFF_REQUEST_GET_JSONOBJECT:
             pDialog = new ProgressDialog(mCcontext);
            pDialog.setMessage("Loading...");
           // if(!requestType.equalsIgnoreCase(RequestType.MEDIA_VIEW_VIDEO) && !requestType.equalsIgnoreCase(RequestType.MEDIA_VIEW_IMAGE))

            pDialog.show();
            //RequestType.SCHOOL_GET_ID_REQUEST


            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, response.toString());
                            if(requestResponseGet!=null)
                                requestResponseGet.onResponseGet(response,requestType);
                            pDialog.dismiss();

                            if(LogFile.LoggerFlag){
                                LogFile.requestResponse("Response from server:="+response);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "ErrorTracker: " + error.getMessage());
                    if(requestResponseGet!=null)
                        requestResponseGet.onErrorResponseGet(error,requestType);
                    // hide the progress dialog
                    pDialog.dismiss();
                    if(LogFile.LoggerFlag){
                        LogFile.requestResponse("Response from server:="+error);
                    }
                }
            });

// Adding request to request queue
            jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReq, message);
            break;
        case Const.TYPE_OFF_REQUEST_GET_JSONARRAY:
            pDialog = new ProgressDialog(mCcontext);
            pDialog.setMessage("Loading...");
            pDialog.show();



            JsonArrayRequest jsonObjReqArray = new JsonArrayRequest(
                    url,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, response.toString());
                            if(requestResponseGet!=null)
                                requestResponseGet.onResponseGet(response,requestType);
                            pDialog.hide();
                            if(LogFile.LoggerFlag){
                                LogFile.requestResponse("Response from server:="+response);
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "ErrorTracker: " + error.getMessage());
                    if(requestResponseGet!=null)
                        requestResponseGet.onErrorResponseGet(error,requestType);
                    // hide the progress dialog
                    pDialog.hide();
                    if(LogFile.LoggerFlag){
                        LogFile.requestResponse("Response from server:="+error);
                    }
                }
            });

// Adding request to request queue
            jsonObjReqArray.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            AppController.getInstance().addToRequestQueue(jsonObjReqArray, message);
            break;
        case Const.TYPE_OFF_REQUEST_GET_STRING:
            break;
    }
}





}
