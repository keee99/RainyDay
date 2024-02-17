package com.example.loginattempt1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.User;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Map;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;


public class SelctChildActivity extends AppActivity {

    // index of child account: changes button behaviours
    // Used during button creation
    // Value doesn't really matter, as long as button indexes differ
    private int index = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selct_child);

        // Sign out button
        Button signOut =  findViewById(R.id.logoutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                onSignOutClick();
            }
        });

        getAllChildrenData();

        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putString("CurrentChild", "Null");
        preferencesEditor.apply();


    }


    /*
    Gets the UID of all children accounts linked, and creates a button for each (createChildButton)
     */
    private void getAllChildrenData() {
        User user = new User.Builder()
                .SetUid(AuthClass.getCurrentUser().getUid())
                .Build();

        Log.d(TAG,  user.getUID());

        user.read(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {
                ArrayList<String> children = user.getRelationship(documents);
                ViewGroup layout = findViewById(R.id.childbuttons);

                // No child accounts linked
                if (children == null || children.isEmpty()) {
                    TextView welcome = findViewById(R.id.welcome);
                    welcome.setText("Link Children Accounts to continue!");

                }
                // Child accounts linked: create buttons
                else {

                    for (String childUID : children) {
                        if (children != null) {
                            createChildButton(childUID, layout);
                        }
                    }
                }

            }
        });

    }


    /**
     *
     * @param childUID: childUID string
     * @param layout: context of button insertion
     */
    private void createChildButton(String childUID, ViewGroup layout){
        User child = new User.Builder()
                .SetUid(childUID)
                .Build();

        // Nested Callback to get child username for display on button
        child.read(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {

                String childUsername = child.getUsername(documents);

                // Create button for each child
                createButton(childUsername, childUID, layout);

                // Removes the loading message
                // Placed here instead of after the synchronous for loop
                // to make UI more responsive
                TextView welcome = findViewById(R.id.welcome);
                welcome.setText("");
            }

        });
    }


    /**
     *
     * @param username: username string to set once button is clicked
     * @param UID: UID string to set once button is clicked
     * @param layout: context of button insertion
     */
    private void createButton(String username, String UID, ViewGroup layout) {

        // Increment index
        index ++;

        // Inflate LinearLayout to house the button
        LinearLayout lay = (LinearLayout) LayoutInflater
                .from(this)
                .inflate(R.layout.child_button_layout, layout, false);

        // Inflate button into the LinearLayout inflated
        CircularProgressButton butt = (CircularProgressButton) LayoutInflater
                .from(this)
                .inflate(R.layout.child_button, lay, false);

        // Configure button
        butt.setText(username);
        butt.setId(index);

        // Configure layout
        lay.addView(butt);
        layout.addView(lay);

        // Set button onClick
        butt.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){

                // Change child related SharedPreferences
                SharedPreferences.Editor preferencesEditor = mPreferences.edit();

                preferencesEditor.putString("CurrentChild", UID);
                preferencesEditor.putString("CurrentChildName", username);

                // Move to homepage activity
                Intent intent = new Intent(SelctChildActivity.this, HomeParentActivity.class);
                preferencesEditor.apply();
                startActivity(intent);
            }
        });


    }


    /*
    if user not logged in, navigate to loginActivity
     */
    @Override
    void updateUI() {
        FirebaseUser user = AuthClass.getCurrentUser();

        if (AuthClass.getCurrentUser() == null) {
            Intent intent = new Intent(SelctChildActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            Log.e(TAG, "User not logged in. Returning to Homepage");
            finish();

        }

    }
}