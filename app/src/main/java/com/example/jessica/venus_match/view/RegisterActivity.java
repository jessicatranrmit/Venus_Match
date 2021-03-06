package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.RegisterRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sintiaaa on 5/09/2017.
 */

public class RegisterActivity extends Activity {
    //ArrayList<User> users;
    EditText username;
    EditText password;
    EditText password_confirm;
    EditText email;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        //Intent intent = getIntent();
        //users = (ArrayList<User>) intent.getSerializableExtra("users");

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        password_confirm = (EditText) findViewById(R.id.password_confirm);
        email = (EditText) findViewById(R.id.email);
        session = new SessionManager(getApplicationContext());
    }

    public void validateRegister(View view)
    {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Creating account...", false, false);

        final String validate_username = username.getText().toString();
        final String validate_password = password.getText().toString();
        final String validate_email = email.getText().toString();
        final String validate_password_confirm = password_confirm.getText().toString();
        if(validate_email.isEmpty() || validate_password.isEmpty() || validate_password_confirm.isEmpty() || validate_email.isEmpty() || validate_username.isEmpty())
        {
            loading.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Please fill in the empty fields")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }
        else if(validate_password.equals(password_confirm.getText().toString()))
        {
            //creating a response listener to parse onto register request
            Response.Listener<String> responseListener = new Response.Listener<String>(){

                @Override
                public void onResponse(String response) {
                    try {
                        System.out.println("in onResponse!");
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            session.createLoginSession(jsonResponse.getString("username"), jsonResponse.getString("email"),null, "Mysterious",
                                    null,null,null,jsonResponse.getString("user_id"), null, null);
                            loading.dismiss();
                            Toast.makeText(getApplicationContext(), "Created account!", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), ActivationActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            loading.dismiss();

                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage(jsonResponse.getString("error_msg"))
                                    .setNegativeButton("Retry", null)
                                    .create()
                                    .show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            RegisterRequest registerRequest = new RegisterRequest(validate_username, validate_password,validate_email,responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
            builder.setMessage("Password must be the same")
                    .setNegativeButton("Retry", null)
                    .create()
                    .show();
        }
    }

    public void go_to_login(View view)
    {

        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
