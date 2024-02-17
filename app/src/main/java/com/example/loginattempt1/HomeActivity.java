package com.example.loginattempt1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;

// This page is the parent homepage: the page with
// a single button "Select Child".
public class HomeActivity extends FunctionAbstractActivity {

        private static final String TAG = "HomeActivity";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_home);


                Button chooseChildButton = findViewById(R.id.selChildButton);
                chooseChildButton.setOnClickListener(new View.OnClickListener (){
                        public void onClick(View v){
                                Intent intent = new Intent(HomeActivity.this,SelctChildActivity.class);
                                startActivity(intent);
                        }
                });

        }


        // No UI elements to update: empty body
        @Override
        void updateUIElements() {}

}
