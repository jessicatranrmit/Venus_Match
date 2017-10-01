package com.example.jessica.venus_match.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jessica.venus_match.R;
import com.example.jessica.venus_match.model.Profile;

import java.io.InputStream;
import java.util.ArrayList;
//import model.Profile;

/**
 * Created by Sintiaaa on 28/09/2017.
 */

public class DashboardAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<Profile> profiles;


    public DashboardAdapter(Context context, ArrayList<Profile> profiles) {
        this.mContext = context;
        this.profiles = profiles;
    }

    @Override
    public int getCount() {
        return profiles.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final Profile profile = profiles.get(i);
        if (view == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            view = layoutInflater.inflate(R.layout.layout_profiles, null);
        }

        final ImageView gender = (ImageView) view.findViewById(R.id.imageview_gender);
        final TextView username = (TextView) view.findViewById(R.id.textview_user_name);
        final TextView country = (TextView) view.findViewById(R.id.user_country);
        final TextView age = (TextView) view.findViewById(R.id.textview_user_age);
        ImageView profile_pic = (ImageView) view.findViewById(R.id.imageview_profile_pic);

        country.setText(profile.getCountry());
        age.setText(profile.getAge());
        username.setText(profile.getUsername());
        if(profile.getGender().equals("Male"))
        {
            gender.setImageResource(R.drawable.male);
        }
        else if(profile.getGender().equals("Female"))
        {
            gender.setImageResource(R.drawable.female);
        }
        else
        {
            gender.setImageResource(R.drawable.venus_logo);
        }
        //setting the profile pictures

        new DownloadImageTask(profile_pic, view).execute("http://54.66.210.220/venusmatch/images/profiles/"+profile.getImageID());


        return view;

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView bmImage;
        private View view;

        public DownloadImageTask(ImageView bmImage, View view) {
            this.bmImage = bmImage;
            this.view = view;
        }
        protected Bitmap doInBackground(String... urls) {

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {

                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            //set image of your imageview
            bmImage.setImageBitmap(result);


        }
    }
}
