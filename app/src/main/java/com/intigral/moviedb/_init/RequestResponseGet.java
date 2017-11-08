package com.intigral.moviedb._init;

import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by AKM on 27,January,2016
 * Source soft solution india pvt. ltd company,
 * Noida, India.
 */
public interface RequestResponseGet {
    public void onErrorResponseGet(VolleyError volleyError, String requestType);
    public void onResponseGet(JSONObject response, String requestType);
    public void onResponseGet(JSONArray response, String requestType);
    public void onResponseGet(String s, String requestType);
}
