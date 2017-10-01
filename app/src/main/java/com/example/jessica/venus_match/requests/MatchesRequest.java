package com.example.jessica.venus_match.requests;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sintiaaa on 28/09/2017.
 *
 * Call this to fetch the matches from the database
 */

public class MatchesRequest extends Request<JSONObject> {

    //URL to the php file
    private static final String MATCHES_REQUEST_URL = "http://10.0.0.130/venusmatchrequests/index.php";
    private Map<String, String> params;
    private Response.Listener<JSONObject> listener;

    public MatchesRequest(String userID, Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        super(Method.POST, MATCHES_REQUEST_URL, errorListener);
        this.listener = responseListener;
        params = new HashMap<>();
        params.put("tag", "get_matches");
        params.put("user_id",userID);
    }

    @Override
    public Map<String, String> getParams() {return params;}

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));

            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
