package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.sessions.SessionManager;
import com.example.jessica.venus_match.model.Profile;

import java.io.InputStream;

/**
 * Created by Sintiaaa on 29/09/2017.
 */

public class ShowProfileActivity extends Activity {

    SessionManager session;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_profile);

        //Get intent for the specific profile

        Intent intent = getIntent();

         Profile selectedUser = (Profile) intent.getSerializableExtra("user");

        session = new SessionManager(getApplicationContext());

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        //setSupportActionBar(toolbar);

        /**Get edit profile intent */
        Intent save_intent = getIntent();
        String about_message = save_intent.getStringExtra(Edit_Profile.ABOUT_DESC);

        /** Display about description */
        TextView about = (TextView) findViewById(R.id.about);
        about.setText(about_message);

        /** Get "get_profile" intent from menu */
        Intent get_profile = getIntent();

        session.checkLoginStatus();

        String username = selectedUser.getUsername();
        String getLocation = selectedUser.getCountry();
        String getGender = selectedUser.getGender();
        String age = selectedUser.getAge();

        TextView tvusername = (TextView) findViewById(R.id.username);
        TextView tvage = (TextView) findViewById(R.id.age);
        TextView location = (TextView) findViewById(R.id.location);
        TextView tvgender = (TextView) findViewById(R.id.gender);
        TextView aboutText = (TextView) findViewById(R.id.about);
        ImageView profile_pic = (ImageView) findViewById(R.id.profile_pic);
        new DownloadImageTask(profile_pic).execute("http://54.66.210.220/venusmatch/images/profiles/"+selectedUser.getImageID());

        tvusername.setText(username);
        tvage.setText(age);
        location.setText(getLocation);
        tvgender.setText(getGender);
        aboutText.setText(selectedUser.getAbout());

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
