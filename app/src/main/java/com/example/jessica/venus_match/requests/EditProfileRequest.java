package com.example.jessica.venus_match.requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jessica on 9/10/2017.
 */

public class EditProfileRequest extends StringRequest{

    private static final String EDIT_PROFILE_REQUEST_URL = "http://10.0.0.78/venusmatchrequests/index.php";
    private Map<String, String> params;

    public EditProfileRequest(String userId, String update_about_desc, String update_username, String update_male_pref,
                              String update_female_pref, Response.Listener<String> listener)
    {
        super(Request.Method.POST, EDIT_PROFILE_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("tag", "update");
        params.put("userId", userId);
        params.put("update_about_desc", update_about_desc);
        params.put("update_username", update_username);
        params.put("update_male_pref", update_male_pref);
        params.put("update_female_pref", update_female_pref);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
