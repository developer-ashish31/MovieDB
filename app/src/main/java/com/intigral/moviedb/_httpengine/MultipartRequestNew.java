package com.intigral.moviedb._httpengine;

/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.intigral.moviedb._property.LogFile;
import com.intigral.moviedb._property.Utills;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;


public class MultipartRequestNew extends Request<String> {
    private static final String FILE_PART_NAME = "file";
    private final Response.Listener<String> mListener;
    private final Map<String, File> mFilePart;
    private final Map<String, String> mStringPart;
    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;

    public MultipartRequestNew(String url, Response.ErrorListener errorListener,
                               Response.Listener<String> listener, Map<String, File> file,
                               Map<String, String> mStringPart) {
        super(Method.POST, url, errorListener);
        mListener = listener;
        mFilePart = file;
        this.mStringPart = mStringPart;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        buildMultipartEntity();
    }

    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        for (Map.Entry<String, File> entry : mFilePart.entrySet()) {
            //entity.addPart(entry.getKey(), new FileBody(entry.getValue(), ContentType.create("image/jpeg"), entry.getKey()));
            try {
                entity.addBinaryBody(entry.getKey(), Utills.streamToByteArray(new FileInputStream(entry.getValue())), ContentType.create("image/jpeg"), entry.getKey() + ".JPG");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (IOException e){

                e.printStackTrace();
            }
        }
        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                entity.addTextBody(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            httpentity = entity.build();
            httpentity.writeTo(bos);
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        Log.d("Response", new String(response.data));
        if(LogFile.LoggerFlag){
            LogFile.requestResponse("Response from server:=" + response.toString());
        }
        return Response.success(new String(response.data), getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response) {


        mListener.onResponse(response);
        if(LogFile.LoggerFlag){
            LogFile.requestResponse("Response from server:=" + response.toString());
        }
    }
}