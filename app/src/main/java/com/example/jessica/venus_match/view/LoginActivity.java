package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.LoginRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Connection;

import static java.sql.Types.NULL;


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

        userType = (EditText) findViewById(R.id.login_user);
        password = (EditText) findViewById(R.id.login_password);
        session = new SessionManager(getApplicationContext());
    }

    public void validate_login(View view) {
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
                            session.createLoginSession(jsonResponse.getString("username"), jsonResponse.getString("email"));
                            if (session.checkActivationStatus()) {
                                Intent intent = new Intent(LoginActivity.this, ActivationActivity.class);
                                //intent.putExtra("email", jsonResponse.getString("email"));
                                startActivity(intent);
                                finish();
                            } else {
                                String uname = jsonResponse.getString("username");
                                String location = jsonResponse.getString("location");
                                String profilePic = jsonResponse.getString("profilePicPath");
                                String gender = jsonResponse.getString("gender");
                                int birthday = jsonResponse.optInt("age", 0);
                                if (birthday == 0) {
                                    Intent intent = new Intent(LoginActivity.this, Profile.class);
                                    intent.putExtra("email", jsonResponse.getString("email"));
                                    intent.putExtra("username", uname);
                                    intent.putExtra("age", birthday);
                                    intent.putExtra("location", location);
                                    intent.putExtra("profilePicPath", profilePic);
                                    intent.putExtra("gender", gender);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    int age = jsonResponse.getInt("age");
                                    Intent intent = new Intent(LoginActivity.this, Profile.class);
                                    intent.putExtra("email", jsonResponse.getString("email"));
                                    intent.putExtra("username", uname);
                                    intent.putExtra("age", age);
                                    intent.putExtra("location", location);
                                    intent.putExtra("profilePicPath", profilePic);
                                    intent.putExtra("gender", gender);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        } else {
                            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                            builder.setMessage("Login Failed")
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(validate_user, validate_password, responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);

        }

    }
}
