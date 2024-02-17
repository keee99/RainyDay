package com.example.loginattempt1;

import android.os.Bundle;
import android.util.Log;

import com.example.loginattempt1.schemas.User;


public abstract class ChildAbstractActivity extends FunctionAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate User child
        child = new User.Builder()
                .SetUid(AuthClass.getCurrentUser().getUid())
                .Build();

    }

    /*
    Update the child name and balance
     */
    @Override
    void updateUIElements() {
        // Update UI elements
        UIClass.setChildText(this);
        UIClass.setBalance(this, child);
    }
}
