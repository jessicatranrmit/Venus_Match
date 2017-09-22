package com.example.jessica.venus_match.sessions;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.jessica.venus_match.view.Profile;
import com.example.jessica.venus_match.view.WelcomeActivity;

import java.util.HashMap;



/**
 * Created by Sintiaaa on 19/09/2017.
 */

public class SessionManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    int PRIVATE_MODE = 0;

    private static final String IS_LOGGED_IN = "IsLoggedIn";
    private static final String PREF_NAME = "VenusMatchPreferences";
    public static final String KEY_NAME = "username";
    public static final String KEY_EMAIL = "email";
    public static final String HAS_ACTIVATED = "hasActivated";

    public SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    /**
     * create a login session for the current logged in user
     **/

    public void createLoginSession(String name, String email) {
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putBoolean(HAS_ACTIVATED, false);
        editor.commit();
    }

    /**
     * return the stored session data
     **/
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));

        return user;
    }

    /**
     * Check to see if user is logged in
     * If false is returned it will redirect to login
     * Else it will stay on current activity
     */

    public void checkLoginStatus() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public boolean checkLoginStatusAtLogin() {
        if (this.isLoggedIn()) {
            Intent intent = new Intent(context, Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;

        }
        return false;
    }

    /**
     * clear user's session details
     **/
    public void logout() {
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }

    public boolean checkActivationStatus()
    {
        if (this.isActivated()) {
            Intent intent = new Intent(context, Profile.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;

        }
        return false;
    }

    // change activation status to true
    public void setActivationStatus()
    {
        editor.putBoolean(HAS_ACTIVATED, true);
        editor.apply();
    }


    public boolean isActivated() {
        return preferences.getBoolean(HAS_ACTIVATED, false);
    }
}
