package com.example.loginattempt1;

// Android
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Async
import com.example.loginattempt1.schemas.FirebaseCallback;

// Util
import java.util.Map;


public class RegisterActivity extends AuthAbstractActivity {

    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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

        // Get views needed
        EditText emailView = findViewById(R.id.email);
        EditText passwordView = findViewById(R.id.password);
        EditText usernameView = findViewById(R.id.username);

        // Convert views into necessary strings
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String username = usernameView.getText().toString();

        // I love peanut butter and jam toast
        Toast.makeText(RegisterActivity.this, "Registering...",
                Toast.LENGTH_LONG).show();

        // Call AuthClass to register account
        AuthClass.registerNewAccount(
                email,
                password,
                username,
                true,
                this,
                new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {
                updateUI();
            }
        });


    }




}