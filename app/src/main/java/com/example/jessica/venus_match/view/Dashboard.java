package com.example.jessica.venus_match.view;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.Profiles;
import com.example.jessica.venus_match.ProfilesAdapter;
import com.example.jessica.venus_match.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    public static final String DATA_URL = ("http://10.132.64.4/venusmatchrequests/get_matches.php");

    public static final String TAG_UNAME = "username";

    private GridView gridView;

    private ArrayList<String> usernames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gridView = (GridView) findViewById(R.id.gridview);

        usernames = new ArrayList<>();

        //getData();

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        /** Get "get_dashboard" intent */
        Intent get_dashboard = getIntent();
    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Fetching matches...", false, false);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(DATA_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        loading.dismiss();

                        showGrid(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
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
                Intent get_dashboard = new Intent(this,Dashboard.class);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showGrid(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = null;
            try {
                obj = jsonArray.getJSONObject(i);
                usernames.add(obj.getString(TAG_UNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ProfilesAdapter profilesAdapter = new ProfilesAdapter(this, usernames);

        gridView.setAdapter(profilesAdapter);
    }

}

