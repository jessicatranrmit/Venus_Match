package com.example.jessica.venus_match;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jessica on 25/09/2017.
 */

public class ProfilesAdapter extends BaseAdapter {

    private final Context context;

    private ArrayList<String> usernames;

    public ProfilesAdapter(Context context, ArrayList<String> usernames) {
        this.context = context;
        this.usernames = usernames;
    }

    @Override
    public int getCount() {
        return usernames.size();
    }

    @Override
    public Object getItem(int position) {
        return usernames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        NetworkImageView networkImageView = new NetworkImageView(context);

        TextView textView = new TextView(context);
        textView.setText(usernames.get(position));

        linearLayout.addView(textView);

        return linearLayout;
    }
}
