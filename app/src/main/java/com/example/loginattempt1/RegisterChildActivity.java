package com.example.loginattempt1;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.Transaction;
import com.example.loginattempt1.schemas.User;

import java.util.Map;


public class RegisterChildActivity extends AuthAbstractActivity {

    private static final String TAG = "RegisterChildActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerchild);

        // Login Button onClickListener
        Button registerButton = findViewById(R.id.cirLoginButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRegisterClick();
            }
        });

    }



    /*
    OnClick implementation for Register button.
    Called from the onClickListener set in onCreate.
     */
    public void onRegisterClick() {
        Log.i(TAG, "Register Clicked");

        // Get Parent Values
        EditText parentUsernameView = findViewById(R.id.parentUsername);
        EditText parentEmailView = findViewById(R.id.parentEmailChildAcc);

        // Get views needed
        EditText emailView = findViewById(R.id.email);
        EditText passwordView = findViewById(R.id.password);
        EditText usernameView = findViewById(R.id.username);


        // Convert views into necessary strings
        String parentUsername = parentUsernameView.getText().toString();
        String parentEmail = parentEmailView.getText().toString();
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();


        // TODO: if parent does not exist, send toast
        // (existing: Will not process but will not notify either)
        // Cant seem to find a workaround, set to low priority
        User parent = new User.Builder()
                .SetUsername(parentUsername)
                .Build();

        try {
            // Check if parent exists. If exists, call registerChild()
            parent.read(new FirebaseCallback() {
                @Override
                public void onResponse(Map<String, Object> documents) {
                    String parentUID;
                    try{
                        Toast.makeText(RegisterChildActivity.this, "Registering...",
                                Toast.LENGTH_LONG).show();

                        parentUID = documents.get("UID").toString();

                        // Call registerChild
                        registerChild(
                                email,
                                password,
                                username,
                                parentUID
                        );

                    } catch (NullPointerException e) {
                        Toast.makeText(
                                RegisterChildActivity.this,
                                "Parent does not exist!",
                                Toast.LENGTH_SHORT
                        );
                        Log.w(TAG, "Parent does not exist!");
                    }
                }
            });
        } catch (Exception e) {
            Log.w(TAG, e.toString());
        }



    }


    /**
     * register child account
     * @param email: email String
     * @param password: password String
     * @param username: Username String
     * @param parentUID: parentUID String
     */
    private void registerChild(
            String email,
            String password,
            String username,
            String parentUID
    ) {
        AuthClass.registerNewAccount(
                email,
                password,
                username,
                false,
                RegisterChildActivity.this,
                new FirebaseCallback() {

                    // on Complete, update both parent and children by appending to
                    // both their Relationship array attributes in Firestore
                    @Override
                    public void onResponse(Map<String, Object> NULL) {

                        // Extract relevant UIDs
                        String childUID = AuthClass.getCurrentUser().getUid();

                        // Update both parent and child relationships and the same time.
                        User parent = new User.Builder()
                                .SetUid(parentUID)
                                .Build();
                        User child = new User.Builder()
                                .SetUid(childUID)
                                .Build();

                        parent.update("Relationship", childUID);
                        child.update("Relationship", parentUID, new FirebaseCallback() {

                            // On complete, updateUI()
                            @Override
                            public void onResponse(Map<String, Object> documents) {
                                Transaction ok = new Transaction.Builder().SetUid(childUID).Build();
                                ok.create(new FirebaseCallback() {
                                    @Override
                                    public void onResponse(Map<String, Object> documents) {
                                        Log.w("ok","success");
                                        updateUI();
                                    }
                                });
                            }
                        });


                    }
                });
    }



}