package com.example.loginattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.util.Log;

// XML Widgets
import android.widget.Button;

// Firebase Imports
import com.example.loginattempt1.schemas.User;


public class HomeParentActivity extends HomeAbstractActivity {

    private static final String TAG = "HomeParentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_parent1);

        // Instantiate Abstract Variable User child
        child = new User.Builder()
                .SetUid(mPreferences.getString("CurrentChild", "null"))
                .Build();

        if (AuthClass.getCurrentUser() == null) updateUI();

        // Name Formatting
        UIClass.setParentText(this,
                mPreferences.getString("CurrentChildName", "your Child"));



        // Button Listeners ----------------------------------------------------------------
        Button addTransaction = findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(HomeParentActivity.this,AddTransactionParent.class);
                startActivity(intent);
            }
        });

        Button topUpButton =  findViewById(R.id.topUpButton);
        topUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(HomeParentActivity.this,TopupActivity.class);
                startActivity(intent);
            }
        });

        Button viewHistory = findViewById(R.id.viewHistory);
        viewHistory.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(HomeParentActivity.this,TransactionHistoryParent.class);
                startActivity(intent);
            }
        });

        Button changeAcc = findViewById(R.id.chanegAcc);
        changeAcc.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeParentActivity.this,SelctChildActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        Button signOut =  findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                                onSignOutClick();
                        }
        });

    }




}


