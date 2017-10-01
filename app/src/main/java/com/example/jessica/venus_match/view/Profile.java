package com.example.jessica.venus_match.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.jessica.venus_match.*;
import com.example.jessica.venus_match.sessions.SessionManager;

import java.io.InputStream;
import java.util.HashMap;


public class Profile extends AppCompatActivity {

    SessionManager session;
    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        session = new SessionManager(getApplicationContext());

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        /**Get edit profile intent */
        Intent save_intent = getIntent();
        String about_message = save_intent.getStringExtra(Edit_Profile.ABOUT_DESC);

        /** Display about description */
        TextView about = (TextView) findViewById(R.id.about);
        about.setText(about_message);

        /** Get "get_profile" intent from menu */
        Intent get_profile = getIntent();

        session.checkLoginStatus();
        user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String email = user.get(SessionManager.KEY_EMAIL);
        String getLocation = user.get(SessionManager.KEY_LOCATION);
        String getGender = user.get(SessionManager.KEY_GENDER);
        String birthday = user.get(SessionManager.KEY_AGE);
        int age = Integer.parseInt(birthday);

        /** Get login intent */
        //Intent intent = getIntent();
        //String uname = intent.getStringExtra("username");
        //String getLocation = intent.getStringExtra("location");
        //String getProfilePic = intent.getStringExtra("profilePicPath");
        //String getGender = intent.getStringExtra("gender");
        //int birthday = intent.getIntExtra("age", -1);

        if(age != 0) {
            TextView tvage = (TextView) findViewById(R.id.age);
            tvage.setText(birthday);
        }
        TextView tvusername = (TextView) findViewById(R.id.username);
        TextView location = (TextView) findViewById(R.id.location);
        TextView tvgender = (TextView) findViewById(R.id.gender);
        ImageView profile_pic = (ImageView) findViewById(R.id.profile_pic);
        TextView aboutText = (TextView) findViewById(R.id.about);
        new DownloadImageTask(profile_pic).execute("http://54.66.210.220/venusmatch/images/profiles/"
                +user.get(SessionManager.KEY_IMAGE_FILE_NAME));
        tvusername.setText(username);
        location.setText(getLocation);
        tvgender.setText(getGender);
        aboutText.setText(user.get(SessionManager.KEY_ABOUT));
    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                Intent get_dashboard = new Intent(this,DashboardActivity.class);
                startActivityForResult(get_dashboard, 0);
                return true;
            case R.id.user_profile:
                Intent get_profile = new Intent(this,Profile.class);
                startActivityForResult(get_profile, 1);
                return true;
            case R.id.messages:
                return true;
            case R.id.action_settings:
                return true;
            case R.id.sign_out:
                session.logout();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** Edit profile */
    public void edit_profile(View view) {
        Intent edit_intent = new Intent(this, Edit_Profile.class);
        startActivity(edit_intent);
    }

    public void logout(View view)
    {
        session.logout();
        finish();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Closing activity")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
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
