package com.example.jessica.venus_match.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sintiaaa on 12/09/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URL = "http://10.0.0.78/venusmatchrequests/index.php";
    //private static final String REGISTER_REQUEST_URL = "http://54.66.210.220/venusmatch/register.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String email, Response.Listener<String> listener)
    {

        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("tag", "register");
        params.put("username", username);
        params.put("password", password);
        params.put("email",email);
    }

    @Override
    public Map<String, String> getParams() {return params;}
}
