package com.example.jessica.venus_match;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Profile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /** Set default values*/
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

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

        /** Get "get_profile" intent */
        Intent get_profile = getIntent();
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
                return true;
            case R.id.user_profile:
                Intent get_profile = new Intent(this,Profile.class);
                startActivityForResult(get_profile, 1);
                return true;
            case R.id.messages:
                return true;
            case R.id.action_settings:
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
}
