package com.example.jessica.venus_match.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.jessica.venus_match.R;

public class Edit_Preferences extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__preferences);

        /** Get edit_preferences intent */
        Intent preference_intent = getIntent();
    }
}
