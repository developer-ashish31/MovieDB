package com.intigral.moviedb._init;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by source on 4/19/2016.
 */
public interface MultipartDataPostResponse {
    public void onFail(VolleyError volleyError, String requestType);
    public void onSuccess(JSONObject response, String requestType);
}
