package com.example.loginattempt1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.User;

import java.util.Map;



public abstract class AuthAbstractActivity extends AppActivity {

    /*
    Check if user is logged in and update UI accordingly
     */
    void updateUIAuth() {

        // If user is logged in,
        if (AuthClass.getCurrentUser() != null) {
            // Build User schema from current UID
            User user = new User.Builder()
                    .SetUid(AuthClass.getCurrentUser().getUid())
                    .Build();

            Log.d(TAG,  user.getUID());

            // Read the user data from Firestore
            user.read(new FirebaseCallback() {
                @Override
                public void onResponse(Map<String, Object> documents) {

                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();

                    Log.d(TAG, String.valueOf(documents));
                    Intent intent = null;

                    // Edit the various SharedPreferences according to user data read
                    try{
                        if ((boolean) documents.get("IsParent")) {
                            preferencesEditor.putBoolean("IsParent", true);


                            intent = new Intent(
                                    AuthAbstractActivity.this,
                                    HomeActivity.class);

                            preferencesEditor.putString("CurrentChild", "Null");

                        } else {
                            preferencesEditor.putBoolean("IsParent", false);
                            intent = new Intent(
                                    AuthAbstractActivity.this,
                                    HomeChildActivity.class);
                        }

                        preferencesEditor.apply();

                        // Clear all previous activities: we don't want the user to navigate to
                        // Authentication pages again
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                |Intent.FLAG_ACTIVITY_NEW_TASK);

                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /*
    updateUI calls the above updateUIAuth
     */
    @Override
    void updateUI() {
        updateUIAuth();
    }


}
