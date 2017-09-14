package com.example.jessica.venus_match;

import android.content.Intent;
import android.preference.*;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import static com.example.jessica.venus_match.R.id.about_desc;

public class Edit_Profile extends AppCompatActivity {

    public static final String ABOUT_DESC = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        /** Get profile intent */
        Intent edit_intent = getIntent();
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

    /** Save profile */
    public void save_profile(View view) {
        Intent save_intent = new Intent(this, Profile.class);
        EditText edit_about = (EditText) findViewById(about_desc);
        String about_message = edit_about.getText().toString();
        save_intent.putExtra(ABOUT_DESC, about_message);
        startActivity(save_intent);
    }

    /** Edit preferences */
    public void edit_preference(View view) {
        Intent preference_intent = new Intent(this, Edit_Preferences.class);
        startActivity(preference_intent);
    }
}
