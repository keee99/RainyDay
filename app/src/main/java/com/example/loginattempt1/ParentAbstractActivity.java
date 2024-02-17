package com.example.loginattempt1;

import android.os.Bundle;
import android.util.Log;

import com.example.loginattempt1.schemas.User;


public abstract class ParentAbstractActivity extends FunctionAbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate User child
        child = new User.Builder()
                .SetUid(mPreferences.getString("CurrentChild", "null"))
                .Build();

        if (AuthClass.getCurrentUser() == null) updateUI();


    }

    /*
    Update the child name and balance
     */
    @Override
    void updateUIElements() {
        UIClass.setParentText(this,
                mPreferences.getString("CurrentChildName", "your Child"));
        UIClass.setBalance(this, child);
    }
}
