package com.example.jessica.venus_match;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.jessica.venus_match.R.id.about_desc;

public class Edit_Profile extends AppCompatActivity {

    public static final String ABOUT_DESC = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        /** Get profile intent*/
        Intent edit_intent = getIntent();
    }

    /** Save profile*/
    public void save_profile(View view) {
        Intent save_intent = new Intent(this, Profile.class);
        EditText edit_about = (EditText) findViewById(about_desc);
        String about_message = edit_about.getText().toString();
        save_intent.putExtra(ABOUT_DESC, about_message);
        startActivity(save_intent);
    }
}
