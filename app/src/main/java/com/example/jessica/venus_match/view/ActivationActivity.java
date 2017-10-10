package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.ActivationRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by Sintiaaa on 19/09/2017.
 */

public class ActivationActivity extends Activity {
    protected String email;
    protected TextView activationMessage;
    SessionManager session;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activation_page);
        Intent intent = getIntent();

        //email = intent.getStringExtra("email");
        activationMessage = (TextView) findViewById(R.id.activation_messages);
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        email = user.get(SessionManager.KEY_EMAIL);
        progressDialog = new ProgressDialog(ActivationActivity.this);

        activationMessage.setText("Your account needs to be activated to use this feature.\n" +
                "If your inbox is empty, try checking your junk mail.\t Click to refresh the page once activated");
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
                        Toast.makeText(getApplicationContext(), "Activation successful!", Toast.LENGTH_SHORT).show();
                        session.setActivationStatus();
                        //go to the profile page
                        Intent intent = new Intent(ActivationActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
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

    //send the activation email again
    public void reload(View view)
    {
        finish();
        startActivity(getIntent());
    }

    public void logout_activation(View view)
    {
        session.logout();
        Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
