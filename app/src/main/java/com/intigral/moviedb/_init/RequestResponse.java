package com.intigral.moviedb._init;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public interface RequestResponse {

    public void onErrorResponse(VolleyError volleyError, String requestType);
    public void onResponse(JSONObject response, String requestType);
    public void onResponse(String s, String requestType);
    public void onResponse(JSONArray jsonArray, String requestType);
}
