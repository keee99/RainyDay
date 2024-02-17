package com.example.loginattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginattempt1.schemas.User;



public class HomeChildActivity extends HomeAbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_child);

        // Instantiate Abstract Variable User child
        child = new User.Builder()
                .SetUid(AuthClass.getCurrentUser().getUid())
                .Build();

        // Set name for display
        // Name formatting
        UIClass.setChildText(this);


        // Button Listeners ----------------------------------------------------------------
        Button addTransaction = findViewById(R.id.addTransaction);
        addTransaction.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(HomeChildActivity.this, AddTransactionChild.class);
                startActivity(intent);
            }
        });

        Button viewHistory = findViewById(R.id.viewHistory);
        viewHistory.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(HomeChildActivity.this,TransactionHistoryChild.class);
                startActivity(intent);
            }
        });

        Button signOut = findViewById(R.id.signOut);
        signOut.setOnClickListener(new View.OnClickListener (){
            public void onClick(View view){
                onSignOutClick();
            }
        });


    }


}