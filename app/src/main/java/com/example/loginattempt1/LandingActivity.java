package com.example.loginattempt1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.User;

import java.util.Map;


// "Loading screen" during app startup
// Can be seen on app startup while logged in
public class LandingActivity extends AppActivity {

    public static final String TAG = "LandingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_landing);

    }

    /*
    Check if user is logged in, brings the user to different pages accordingly
     */
    @Override
    void updateUI() {
        // If user is logged in,
        if (AuthClass.getCurrentUser() != null) {

            User user = new User.Builder()
                    .SetUid(AuthClass.getCurrentUser().getUid())
                    .Build();

            Log.d(TAG,  user.getUID());

            // Read user data and update SharedPreferences (failsafe if not updated)
            // Navigates the user to their corresponding user activities depending on account type
            user.read(new FirebaseCallback() {
                @Override
                public void onResponse(Map<String, Object> documents) {

                    SharedPreferences.Editor preferencesEditor = mPreferences.edit();

                    Log.d(TAG, String.valueOf(documents));
                    Intent intent = null;
                    try{
                        if ((boolean) documents.get("IsParent")) {
                            preferencesEditor.putBoolean("IsParent", true);
                            intent = new Intent(LandingActivity.this, HomeActivity.class);
                            preferencesEditor.putString("CurrentChild", "Null");

                        } else {
                            preferencesEditor.putBoolean("IsParent", false);
                            intent = new Intent(LandingActivity.this,HomeChildActivity.class);
                        }

                        preferencesEditor.apply();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
            });

        } else {
            // If user not logged in, navigate to Login
            Intent intent = new Intent(LandingActivity.this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }


    }


}

