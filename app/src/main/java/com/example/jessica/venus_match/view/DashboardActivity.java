package com.example.jessica.venus_match.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.jessica.venus_match.R;

/**
 * Created by Sintiaaa on 5/09/2017.
 */

public class DashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.dashboard);

        Intent intent = getIntent();
        String username = (String) intent.getSerializableExtra("username");

        TextView usernamePanel = (TextView) findViewById(R.id.dashboard_username);
        usernamePanel.setText("Welcome: " + username);
    }
}
