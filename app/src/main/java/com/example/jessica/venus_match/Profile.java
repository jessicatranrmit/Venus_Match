package com.example.jessica.venus_match;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /** Set default values*/
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        /**Get edit profile intent */
        Intent save_intent = getIntent();
        String about_message = save_intent.getStringExtra(Edit_Profile.ABOUT_DESC);

        /** Display about description */
        TextView about = (TextView) findViewById(R.id.about);
        about.setText(about_message);
    }

    /** Edit profile */
    public void edit_profile(View view) {
        Intent edit_intent = new Intent(this, Edit_Profile.class);
        startActivity(edit_intent);
    }
}
