package com.example.jessica.venus_match.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sintiaaa on 12/09/2017.
 */

public class LoginRequest extends StringRequest {

    //private static final String LOGIN_REQUEST_URL = "http://10.0.0.78/venusmatchrequests/index.php";
    //private static final String LOGIN_REQUEST_URL = "http://54.66.210.220/venusmatch/login.php";
    private static final String LOGIN_REQUEST_URL = "http://10.132.64.4/venusmatchrequests/index.php";
    private Map<String, String> params;

    public LoginRequest(String userType, String password, Response.Listener<String> listener)
    {
        super(Method.POST, LOGIN_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("tag", "login");
        params.put("userType", userType);
        params.put("password", password);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }

}
