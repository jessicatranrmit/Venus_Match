package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.ActivationRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sintiaaa on 19/09/2017.
 */

public class ActivationActivity extends Activity {
    protected String email;
    protected TextView activationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_page);
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        activationMessage = (TextView) findViewById(R.id.activation_messages);

        checkActivationStatus();

    }

    //check whether the user has activated their account or not
    private void checkActivationStatus() {

        System.out.println("in method");
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success)
                    {
                        //go to the profile page
                        Intent intent = new Intent(ActivationActivity.this, Profile.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        System.out.println("in else statement method");
                         //set activation message
                        activationMessage.setText(jsonResponse.getString("error_msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        ActivationRequest activationRequest = new ActivationRequest(email, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ActivationActivity.this);
        queue.add(activationRequest);
    }
}
