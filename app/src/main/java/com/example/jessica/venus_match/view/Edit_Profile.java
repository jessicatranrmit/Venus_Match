package com.example.jessica.venus_match.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.EditProfileRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class Edit_Profile extends AppCompatActivity {

    EditText update_about_desc;
    EditText update_username;
    Switch update_male_pref;
    Switch update_female_pref;

    SessionManager session;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        session = new SessionManager(getApplicationContext());

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        /* Get user details */
        session.checkLoginStatus();
        user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String get_prefer_male = user.get(SessionManager.KEY_PREFERS_MALE);
        String get_prefer_female = user.get(SessionManager.KEY_PREFERS_FEMALE);

        TextView aboutText = (TextView) findViewById(R.id.about_desc);
        TextView tvusername = (TextView) findViewById(R.id.username);
        Switch prefers_male_switch = (Switch) findViewById(R.id.male_pref);
        Switch prefers_female_switch = (Switch) findViewById(R.id.female_pref);
        int pref_male = Integer.parseInt(get_prefer_male);
        int pref_female = Integer.parseInt(get_prefer_female);

        /* Set and display profile picture */
        ImageView profile_pic = (ImageView) findViewById(R.id.profile_pic);
        new Edit_Profile.DownloadImageTask(profile_pic).execute("http://54.66.210.220/venusmatch/images/profiles/"
                +user.get(SessionManager.KEY_IMAGE_FILE_NAME));

        /* Set username */
        tvusername.setText(username);

        /* Set about description */
        if(aboutText!=null) {
            aboutText.setText(user.get(SessionManager.KEY_ABOUT));
        }

        /* Set gender preference switches */
        if(pref_male != 0) {
            prefers_male_switch.setChecked(true);
        }
        if(pref_female != 0) {
            prefers_female_switch.setChecked(true);
        }
    }

    public void update_profile(View view) {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Updating profile...", false, false);
        update_about_desc = (EditText) findViewById(R.id.about_desc);
        update_username = (EditText) findViewById(R.id.username);
        update_male_pref = (Switch) findViewById(R.id.male_pref);
        update_female_pref = (Switch) findViewById(R.id.female_pref);
        final String update_about = update_about_desc.getText().toString();
        final String edit_username = update_username.getText().toString();
        final String male_pref;
        final String female_pref;

        // Getting switch values
        if(update_male_pref.isChecked()) {
            male_pref = "1";
        }
        else {
            male_pref = "0";
        }
        if(update_female_pref.isChecked()) {
            female_pref = "1";
        }
        else {
            female_pref = "0";
        }

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        loading.dismiss();
                        // Update session
                        session.updateSession(jsonResponse.getString("about"), jsonResponse.getString("username"), jsonResponse.getString("prefers_male"),
                                jsonResponse.getString("prefers_female"));
                        Intent intent = new Intent(Edit_Profile.this, ProfileActivity.class);
                        Toast.makeText(getApplicationContext(), "Update profile successful!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    loading.dismiss();
                    e.printStackTrace();
                }
            }
        };
        EditProfileRequest editProfileRequest = new EditProfileRequest(user.get(SessionManager.KEY_ID), update_about, edit_username, male_pref,
                female_pref, responseListener);
        RequestQueue queue = Volley.newRequestQueue(Edit_Profile.this);
        queue.add(editProfileRequest);
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Menu items */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                Intent get_dashboard = new Intent(this,DashboardActivity.class);
                startActivityForResult(get_dashboard, 0);
                finish();
                return true;
            case R.id.user_profile:
                Intent get_profile = new Intent(this,ProfileActivity.class);
                startActivityForResult(get_profile, 1);
                finish();
                return true;
            case R.id.sign_out:
                session.logout();
                Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Cancel profile update */
    public void cancel_update(View view) {
        Intent cancel_edit_intent = new Intent(this, ProfileActivity.class);
        startActivity(cancel_edit_intent);
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //set image of your imageview
            bmImage.setImageBitmap(result);
        }
    }
}
