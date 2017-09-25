package com.example.jessica.venus_match;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jessica on 25/09/2017.
 */

public class ProfilesAdapter extends BaseAdapter {

    private final Context mContext;
    private final Profiles[] profiles;

    public ProfilesAdapter(Context mContext, Profiles[] profiles) {

        this.mContext = mContext;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView dummyTextView = new TextView(mContext);
        dummyTextView.setText(String.valueOf(position));
        return dummyTextView;
    }
}
