package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.LoginRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Sintiaaa on 5/09/2017.
 */

public class LoginActivity extends Activity {

    public static final String PREFRENCES = "preferences";
    public static final String USERLOGIN = "userLogin";
    SessionManager session;
    //String testUsername = "sintiaaa";
    //String testPassword = "password";
    EditText userType;
    EditText password;
    //Intent intent = getIntent();
    ///ArrayList<User> users = (ArrayList<User>) intent.getSerializableExtra("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        session = new SessionManager(getApplicationContext());
        if(session.checkLoginStatusAtLogin())
        {
            finish();
        }

        userType = (EditText) findViewById(R.id.login_user);
        password = (EditText) findViewById(R.id.login_password);
    }

    public void validate_login(View view) {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Logging in...", false, false);
        final String validate_user = userType.getText().toString();
        final String validate_password = password.getText().toString();

        if (validate_user.isEmpty() || validate_password.isEmpty() || validate_password.isEmpty()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setMessage("Please fill in the empty fields")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        } else {
            // Response received from the server
            Response.Listener<String> responseListener = new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if (success) {

                            int birthday = jsonResponse.optInt("age", 0);
                            String age = Integer.toString(birthday);
                            session.createLoginSession(jsonResponse.getString("username"), jsonResponse.getString("email"),
                                    jsonResponse.getString("location"), jsonResponse.getString("gender"),
                                    age,jsonResponse.getString("image_filename"),jsonResponse.getString("about"),jsonResponse.getString("user_id"),
                                    jsonResponse.getString("prefers_male"), jsonResponse.getString("prefers_female"));

                            if(jsonResponse.getInt("has_activated") == 0)
                            {
                                loading.dismiss();
                                Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, ActivationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            else
                            {
                                //set activation status to true;
                                session.setActivationStatus();
                                loading.dismiss();
                                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                                intent.putExtra("email", jsonResponse.getString("email"));
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            loading.dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage(jsonResponse.getString("error_msg"))
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        loading.dismiss();
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(validate_user, validate_password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);

        }
    }

    public void register(View view)
    {
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
