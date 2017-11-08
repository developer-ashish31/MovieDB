package com.intigral.moviedb._httpengine;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.intigral.moviedb._app.AppController;
import com.intigral.moviedb._init.MultipartDataPostResponse;
import com.intigral.moviedb._init.RequestResponse;
import com.intigral.moviedb._property.AppUtils;
import com.intigral.moviedb._property.Const;
import com.intigral.moviedb._property.LogFile;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public class PostApiEngine {
    int Id;
    Context mCcontext;
    String Url;
    String TAG = "PostApiEngine";
    Map<String, String> params;
    RequestResponse requestApiLis;
    ProgressDialog pDialog;
    String dialogMessage;
    String requestType;
    ArrayList<String> urlArry;
    public void setLisner(RequestResponse lisner) {
        this.requestApiLis = lisner;
    }
MultipartDataPostResponse multipartDataPostResponse;
    public PostApiEngine(Context mCcontext, final String Url, ArrayList<String> urlArry, String message, int id, final Map<String, String> params, final String ImagePtah, RequestResponse requestApiLis, MultipartDataPostResponse multipartDataPostResponse, String dialogMessage, String requestType) {
        this.mCcontext = mCcontext;
        this.Url = Url;
        this.Id = id;
        this.requestType = requestType;
        this.dialogMessage = dialogMessage;
        this.requestApiLis = requestApiLis;
        this.urlArry=urlArry;
        this.multipartDataPostResponse=multipartDataPostResponse;
        Log.v("requestType::Url", Url);
        CallApi(Id, Url, message, params, ImagePtah,urlArry, dialogMessage, requestType);

        if(LogFile.LoggerFlag){
            LogFile.requestResponse(params.toString() + "  Url:=" + Url + " ImagePtah:=" + ImagePtah.toString() + " RequestType:=" + requestType);
        }
    }

    /*private void uploadImage(){
        //Showing the progress dialog

    }*/
    public void CallApi(int in, String url, String message, final Map<String, String> params, String ImagePtah, ArrayList<String> urlArry, String dialogMessage, final String requestType) {
        this.params = params;
        switch (in) {
            case Const.TYPE_OFF_REQUEST_POST_STRING:
                pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                //Disimissing the progress dialog
                                pDialog.dismiss();
                                //Showing toast message of the response
                                Toast.makeText(mCcontext, s, Toast.LENGTH_LONG).show();
                                if (requestApiLis != null) {
                                    requestApiLis.onResponse(s, requestType);
                                }
                                if(LogFile.LoggerFlag){
                                    LogFile.requestResponse("Response from server:="+s);
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                //Dismissing the progress dialog
                                pDialog.dismiss();
                                if (requestApiLis != null) {
                                    requestApiLis.onErrorResponse(volleyError, requestType);
                                }
                                //Showing toast
                                Toast.makeText(mCcontext, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                                if(LogFile.LoggerFlag){
                                    LogFile.requestResponse("Response from server:="+volleyError.toString());
                                }
                            }
                        }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {


                        //returning parameters
                        return params;
                    }
                };

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(mCcontext);
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                //Adding request to the queue
                requestQueue.add(stringRequest);
                break;
            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART_MULTIPLE_URL:
              //  params.put("imageName", pictureName);
                pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);

                for(String str : urlArry) {
                    Map<String, File> files = new HashMap<>();
                    files.put(Const.PRODUCT_IMAGES, new File(str));

                    MultipartRequestNew multipartRequest = new MultipartRequestNew(url, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO Auto-generated method stub
                            Log.d("ErrorTracker: ", error.toString());

                            pDialog.dismiss();
                            if (multipartDataPostResponse != null) {
                                multipartDataPostResponse.onFail(error, requestType);
                            }
                            if (LogFile.LoggerFlag) {
                                LogFile.requestResponse("Response from server:=" + error.toString());
                            }
                            // pDialog.showErrorDialog(mCcontext);
                        }
                    }, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String jsonResponse) {
                            JSONObject response = null;
                            try {
                                Log.d("jsonResponse: ", jsonResponse);
                                response = new JSONObject(jsonResponse);
                                if (multipartDataPostResponse != null) {
                                    multipartDataPostResponse.onSuccess(response, requestType);
                                }
                                if (LogFile.LoggerFlag) {
                                    LogFile.requestResponse("Response from server:=" + jsonResponse.toString());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            try {
                                if (response != null && response.has("statusmessage") && response.getBoolean("statusmessage")) {
                                    //   updateLocalRecord();

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            pDialog.dismiss();
                        }

                    }, files, params);
                    RequestQueue queue = Volley.newRequestQueue(mCcontext);
                    multipartRequest.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(multipartRequest);
                }
                break;
            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART:


                   /* Map<String, String> params = new HashMap<>();
                    params.put("name", name.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("user_id", appPreferences.getInt( Utils.PROPERTY_USER_ID, -1) + "");
                    params.put("password", password.getText().toString());
                    params.put("imageName", pictureName);*/
                pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);
                Map<String, File> files = new HashMap<>();
                files.put(Const.KEY_IMAGE, new File(ImagePtah));
                MultipartRequestNew multipartRequest = new MultipartRequestNew(url, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ErrorTracker: ", error.toString());
                        pDialog.dismiss();
                        if (requestApiLis != null) {
                            requestApiLis.onErrorResponse(error, requestType);
                        }
                        if(LogFile.LoggerFlag){
                            LogFile.requestResponse("Response from server:=" + error.toString());
                        }
                        // pDialog.showErrorDialog(mCcontext);
                    }
                }, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonResponse) {
                        JSONObject response = null;
                        try {
                            Log.d("jsonResponse: ", jsonResponse);
                            response = new JSONObject(jsonResponse);
                            if (requestApiLis != null) {
                                requestApiLis.onResponse(response, requestType);
                            }
                            if(LogFile.LoggerFlag){
                                LogFile.requestResponse("Response from server:=" + jsonResponse.toString());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (response != null && response.has("statusmessage") && response.getBoolean("statusmessage")) {
                                //   updateLocalRecord();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                    }

                }, files, params);
                RequestQueue queue = Volley.newRequestQueue(mCcontext);
                multipartRequest.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(multipartRequest);
                break;
            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_MULTIPART_PDF:


                   /* Map<String, String> params = new HashMap<>();
                    params.put("name", name.getText().toString());
                    params.put("email", email.getText().toString());
                    params.put("user_id", appPreferences.getInt( Utils.PROPERTY_USER_ID, -1) + "");
                    params.put("password", password.getText().toString());
                    params.put("imageName", pictureName);*/
                pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);
                files = new HashMap<>();
                files.put(Const.ASSIGNMENT, new File(ImagePtah));
                MultipartRequest multipartRequestData = new MultipartRequest(url, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub
                        Log.d("ErrorTracker: ", error.toString());
                        pDialog.dismiss();
                        if (requestApiLis != null) {
                            requestApiLis.onErrorResponse(error, requestType);
                        }
                        if(LogFile.LoggerFlag){
                            LogFile.requestResponse("Response from server:=" + error.toString());
                        }
                        // pDialog.showErrorDialog(mCcontext);
                    }
                }, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String jsonResponse) {
                        JSONObject response = null;
                        try {
                            Log.d("jsonResponse: ", jsonResponse);
                            response = new JSONObject(jsonResponse);
                            if (requestApiLis != null) {
                                requestApiLis.onResponse(response, requestType);
                            }
                            if(LogFile.LoggerFlag){
                                LogFile.requestResponse("Response from server:=" + response.toString());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (response != null && response.has("statusmessage") && response.getBoolean("statusmessage")) {
                                //   updateLocalRecord();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.dismiss();
                    }

                }, files, params);
                queue = Volley.newRequestQueue(mCcontext);
                multipartRequestData.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(multipartRequestData);
                break;
            case Const.TYPE_OFF_REQUEST_POST_JSONARRAY:
                   /* try {
                        JSONArray jsonArray = new JSONArray(jsonString);
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, url, jsonArray, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                mTextView.setText(response.toString());
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                               // mTextView.setText(error.toString());
                            }
                        }) {
                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {
                                HashMap<String, String> headers = new HashMap<String, String>();
                                //headers.put("Content-Type", "application/json");
                                headers.put("Content-Type", "application/x-www-form-urlencoded");
                                return headers;
                            }
                        };
                       // requestQueue.add(jsonArrayRequest);
                        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/

                break;

            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_POST:
                if (!url.equalsIgnoreCase(Const.GCM_SEND_URL)) {

                    pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);
                }
                     JSONObject onject = new JSONObject(params);
                JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, onject,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                if (requestApiLis != null) {
                                    requestApiLis.onResponse(response, requestType);
                                }
                                if (pDialog != null)
                                    pDialog.dismiss();
                                if(LogFile.LoggerFlag){
                                    LogFile.requestResponse("Response from server:=" + response.toString());
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "ErrorTracker: " + error.getMessage());
                        if (pDialog != null)
                            pDialog.dismiss();
                        if (requestApiLis != null) {
                            requestApiLis.onErrorResponse(error, requestType);
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
//                      headers.put("Content-Type", "application/json");
                        headers.put("Content-Type","application/x-www-form-urlencoded");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }

                    @Override

                    public byte[] getBody() {
                        if (params != null && params.size() > 0) {
                            return AppUtils.encodeParameters(params, AppUtils.getParamsEncoding());
                        }
                        return null;
                    }
                };

                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(jsonObjReq);

                break;

            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_POST1:
                if (!url.equalsIgnoreCase(Const.GCM_SEND_URL)) {

                    pDialog = ProgressDialog.show(mCcontext, dialogMessage, "Please wait...", false, false);
                }
                JSONObject onject1 = new JSONObject(params);
                JsonObjectRequest jsonObjReq1 = new JsonObjectRequest(Request.Method.POST,
                        url, onject1,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                if (requestApiLis != null) {
                                    requestApiLis.onResponse(response, requestType);
                                }
                                if (pDialog != null)
                                    pDialog.dismiss();
                                if(LogFile.LoggerFlag){
                                    LogFile.requestResponse("Response from server:=" + response.toString());
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "ErrorTracker: " + error.getMessage());
                        if (pDialog != null)
                            pDialog.dismiss();
                        if (requestApiLis != null) {
                            requestApiLis.onErrorResponse(error, requestType);
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
//                        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }

                    @Override

                    public byte[] getBody() {
                        if (params != null && params.size() > 0) {
                            return AppUtils.encodeParameters(params, AppUtils.getParamsEncoding());
                        }
                        return null;
                    }
                };

                jsonObjReq1.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(jsonObjReq1);

                break;
            case Const.TYPE_OFF_REQUEST_POST_JSONOBJECT_REQUEST_TYPE_JSON_POST:

                onject = new JSONObject(params);
                jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                        url, onject,
                        new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d(TAG, response.toString());
                                if (requestApiLis != null) {
                                    requestApiLis.onResponse(response, requestType);
                                }
                                if (pDialog != null)
                                    pDialog.dismiss();
                                if(LogFile.LoggerFlag){
                                    LogFile.requestResponse("Response from server:=" + response.toString());
                                }
                            }
                        }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "ErrorTracker: " + error.getMessage());
                        if (pDialog != null)
                            pDialog.dismiss();
                        if (requestApiLis != null) {
                            requestApiLis.onErrorResponse(error, requestType);
                        }
                        if(LogFile.LoggerFlag){
                            LogFile.requestResponse("Response from server:=" + error.toString());
                        }
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }

                    @Override
                    protected Map<String, String> getParams() {
                        return params;
                    }

                    @Override
                    public byte[] getBody() {
                        if (params != null && params.size() > 0) {
                            return AppUtils.encodeParameters(params, AppUtils.getParamsEncoding());
                        }
                        return null;
                    }
                };

                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(Const.TIME_OUT_CONNECTION,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                AppController.getInstance().addToRequestQueue(jsonObjReq);
                break;
        }
    }

}