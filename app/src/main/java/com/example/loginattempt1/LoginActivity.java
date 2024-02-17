package com.example.loginattempt1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Toast;

import android.widget.Button;
import android.widget.TextView;



public class LoginActivity extends AuthAbstractActivity {

    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);

        // Button Listeners
        // Login Button onClickListener
        Button loginButton = findViewById(R.id.cirLoginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLoginClick();
            }
        });

        // Register Here text link
        TextView registerNav = findViewById(R.id.registerHere);
        registerNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegister(view);
            }
        });


        // Register Children Here text link
        TextView registerChildNav = findViewById(R.id.registerChildHere);
        registerChildNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navigateToRegisterChild(view);
            }
        });

    }



    /*
    OnClick implementation for Login button.
    Called from the onClickListener set in onCreate.
    No params, No return
     */
    public void onLoginClick()
    {
        Log.i(TAG, "Login Clicked");
        TextView emailView = findViewById(R.id.loginEmail);
        TextView passwordView = findViewById(R.id.loginPwd);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        if (email.isEmpty() || email == null
                || password.isEmpty() || password == null) {
            Toast.makeText(LoginActivity.this, "Fill in the blanks!", Toast.LENGTH_SHORT).show();
        } else {
            AuthClass.signIn(email, password, this);
        }

    }



    // UI UPDATING ================================================================================

    /*
    onClick implementation for the Register Here!
    Opens the activity RegisterActivity
     */
    public void navigateToRegister(View view)
    {
        Log.i(TAG, "Register Clicked");
        startActivity(new Intent(this,RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }


    /*
    onClick implementation for the Register for Children!
    Opens the activity RegisterActivity
     */
    public void navigateToRegisterChild(View view)
    {
        Log.i(TAG, "Register Child Clicked");
        startActivity(new Intent(this,RegisterChildActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }



}

