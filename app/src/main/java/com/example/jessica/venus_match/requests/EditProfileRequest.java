package com.example.jessica.venus_match.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessica on 9/10/2017.
 */

public class EditProfileRequest extends StringRequest{

    //private static final String EDIT_PROFILE_REQUEST_URL = "http://10.0.0.78/venusmatchrequests/index.php";
    private static final String EDIT_PROFILE_REQUEST_URL = "http://10.132.67.150/venusmatchrequests/index.php";
    private Map<String, String> params;

    public EditProfileRequest(String userId, String update_about_desc, String update_username, Response.Listener<String> listener)
    {
        super(Request.Method.POST, EDIT_PROFILE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("tag", "update");
        params.put("userId", userId);
        params.put("update_about_desc", update_about_desc);
        params.put("update_username", update_username);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
