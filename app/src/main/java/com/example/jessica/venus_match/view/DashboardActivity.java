package com.example.jessica.venus_match.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.adapters.DashboardAdapter;
import com.example.jessica.venus_match.requests.MatchesRequest;
import com.example.jessica.venus_match.sessions.SessionManager;
import com.example.jessica.venus_match.model.Profile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Sintiaaa on 5/09/2017.
 */

public class DashboardActivity extends AppCompatActivity {

    SessionManager session;
    HashMap<String, String> user;

    private GridView gridView;

    final ArrayList<Profile> profiles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_dashboard);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        gridView = (GridView) findViewById(R.id.gridview);
        session = new SessionManager(getApplicationContext());
        session.checkLoginStatus();
        user = session.getUserDetails();

        getData();
    }

    private void getData() {
        final ProgressDialog loading = ProgressDialog.show(this, "Please wait...",
                "Fetching matches...", false, false);


        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jUsers = response.getJSONArray("users");

                    for(int i=0; i<jUsers.length(); i++) {
                        JSONObject jsonObject = jUsers.getJSONObject(i);

                        Profile profile = new Profile(jsonObject.getString("first_name"), jsonObject.getString("last_name"),jsonObject.getString("username"),jsonObject.getString("age"),jsonObject.getString("gender"),jsonObject.getString("image_filename"),jsonObject.getString("country_name"),jsonObject.getString("about"));
                        profiles.add(profile);
                    }

                    final DashboardAdapter dashboardAdapter = new DashboardAdapter(DashboardActivity.this,profiles);
                    gridView.setAdapter(dashboardAdapter);

                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                            Intent intent = new Intent(getApplicationContext(), ShowProfileActivity.class);
                            intent.putExtra("user", profiles.get(position));

                            startActivity(intent);
                        }
                    });

                    loading.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error);
            }
        };

        MatchesRequest matchRequest = new MatchesRequest(user.get(SessionManager.KEY_ID),responseListener, errorListener);
        RequestQueue queue = Volley.newRequestQueue(DashboardActivity.this);
        queue.add(matchRequest);
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
                Intent get_dashboard = new Intent(this, DashboardActivity.class);
                startActivityForResult(get_dashboard, 0);
                finish();
                return true;
            case R.id.user_profile:
                Intent get_profile = new Intent(this, com.example.jessica.venus_match.view.ProfileActivity.class);
                startActivityForResult(get_profile, 1);
                finish();
                return true;
            case R.id.sign_out:
                session.logout();
                Toast.makeText(getApplicationContext(), "Logout successful!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
