package br.edu.ifrn.tads.caronas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import br.edu.ifrn.tads.caronas.data.User;

public class UserSessionManager {
    private static final String PREFER_NAME = "br.ifrn.caronas.prefs";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private Context context;

    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, 0);
        editor = pref.edit();
    }

    public void createUserLoginSession(User user) {
        if(user != null) {
            editor.putBoolean(IS_USER_LOGIN, true);
            editor.putString("user_id", user.getId());
            editor.putString("user_doc", new Gson().toJson(user));
            editor.commit();
        }
    }

    public boolean requireLogin() {
        if(!this.isUserLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(context, LoginActivity.class);
            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // Staring Login Activity
            context.startActivity(i);
            return true;
        }
        if(App.getCurrentUser() == null) App.setCurrentUser(getUserDetails());
        return false;
    }

    /**
     * Get stored session data
     * */
    public User getUserDetails(){
        String userDoc = pref.getString("user_doc", null);
        return new Gson().fromJson(userDoc, User.class);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();
        requireLogin();
    }

    // Check for login
    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }
}
