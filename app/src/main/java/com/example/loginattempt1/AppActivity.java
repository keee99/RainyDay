package com.example.loginattempt1;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public abstract class AppActivity extends AppCompatActivity {

    // TAG default setup in case not implemented in Activity
    protected String TAG;
    AppActivity() { TAG = "AppActivity"; }

    // SharedPreferences setup
    protected final String sharedPrefFile = "com.example.loginattempt1.mainsharedprefs";
    SharedPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Instantiate SharedPreferences
        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
    }

    /*
    onResume to Call updateUI on resume to update certain UI elements
     */
    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }


    /*
    updateUI: Updates the page, including certain UI features, or if the user is logged in or not
     */
    abstract void updateUI();


    /*
    onSignOutClick: If a sign out button is clicked, run this function to clear current user.
    AuthClass.signOut calls the updateUI after sign out is complete.
     */
    protected void onSignOutClick () {

        // Clear the shared preferences
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear().apply();

        // Signout the user
        AuthClass.signOut(this);
    }

}
