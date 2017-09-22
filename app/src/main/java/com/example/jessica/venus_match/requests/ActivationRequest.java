package com.example.jessica.venus_match.requests;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sintiaaa on 19/09/2017.
 */

public class ActivationRequest extends StringRequest {
    private static final String ACTIVATION_REQUEST_URL = "http://10.0.0.78/venusmatchrequests/index.php";
    private Map<String, String> params;

    public ActivationRequest(String email, Response.Listener<String> listener)
    {
        super(Method.POST, ACTIVATION_REQUEST_URL, listener,null);
        params = new HashMap<>();
        params.put("tag", "check_activation");
        params.put("email", email);
    }
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
