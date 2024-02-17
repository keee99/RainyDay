package com.example.loginattempt1;

import android.content.Intent;
import android.util.Log;

import com.example.loginattempt1.schemas.User;

public abstract class FunctionAbstractActivity extends AppActivity {

    // child -- Represents the SUBJECT of which the pages are about (could've been better named :/)
    // Is assigned in onCreates according to sharedPreferences (if parent) or Auth UID (if child)
    protected User child;


    /*
    Update UI depending on if the user is logged in or not
     */
    @Override
    void updateUI() {

        // On logout: return to homepage
        if (AuthClass.getCurrentUser() == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            Log.e(TAG, "User not logged in. Returning to Homepage");
            finish();

        }
        // If user is logged in, update necessary UI elements
        // UI elements to update onResume or whenever updateUI is called
        else {
            updateUIElements();
        }
    }

    /*
    Updates all necessary UI Elements on the page, depending on the page
     */
    abstract void updateUIElements();
}
