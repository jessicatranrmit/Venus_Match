package com.example.jessica.venus_match.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.requests.EditProfileRequest;
import com.example.jessica.venus_match.sessions.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;

public class Edit_Profile extends AppCompatActivity {

    private static int RESULT_LOAD_IMAGE = 1;
    EditText update_about_desc;
    EditText update_username;

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

        /*Get user details*/
        session.checkLoginStatus();
        user = session.getUserDetails();
        String username = user.get(SessionManager.KEY_NAME);
        String getLocation = user.get(SessionManager.KEY_LOCATION);
        String getGender = user.get(SessionManager.KEY_GENDER);

        TextView aboutText = (TextView) findViewById(R.id.about_desc);
        TextView tvusername = (TextView) findViewById(R.id.username);

        //Spinner location = (Spinner) findViewById(R.id.country_picker);
        //TextView tvgender = (TextView) findViewById(R.id.gender);
        ImageView profile_pic = (ImageView) findViewById(R.id.profile_pic);
        new Edit_Profile.DownloadImageTask(profile_pic).execute("http://54.66.210.220/venusmatch/images/profiles/"
                +user.get(SessionManager.KEY_IMAGE_FILE_NAME));

        tvusername.setText(username);

        if(aboutText!=null) {
            aboutText.setText(user.get(SessionManager.KEY_ABOUT));
        }
        //location.setText(getLocation);

        //tvgender.setText(getGender);

        ImageButton upload = (ImageButton) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent upload = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(upload, RESULT_LOAD_IMAGE);
            }
        });

        /** Get profile intent */
        Intent edit_intent = getIntent();
    }

    public void update_profile(View view){
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Updating profile...", false, false);
        update_about_desc = (EditText) findViewById(R.id.about_desc);
        update_username = (EditText) findViewById(R.id.username);
        final String update_about = update_about_desc.getText().toString();
        final String edit_username = update_username.getText().toString();

        // Response received from the server
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("Update success");
                loading.dismiss();
                Intent intent = new Intent(Edit_Profile.this, ProfileActivity.class);
                Toast.makeText(getApplicationContext(), "Update profile successful!", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        };

        EditProfileRequest editProfileRequest = new EditProfileRequest(user.get(SessionManager.KEY_ID), update_about, edit_username, responseListener);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.dashboard:
                Intent get_dashboard = new Intent(this,DashboardActivity.class);
                startActivityForResult(get_dashboard, 0);
                return true;
            case R.id.user_profile:
                Intent get_profile = new Intent(this,ProfileActivity.class);
                startActivityForResult(get_profile, 1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            ImageView profile_pic = (ImageView) findViewById(R.id.profile_pic);
            profile_pic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
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
