package com.example.loginattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginattempt1.schemas.FirebaseCallback;

import java.math.BigDecimal;
import java.util.Map;

public class TopupActivity extends ParentAbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topupaccount);

        //button onClicks
        Button Home_button = findViewById(R.id.Home_button);
        Home_button.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(TopupActivity.this,HomeParentActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button Next_button = findViewById(R.id.Next_button);
        Next_button.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View view) {
                onTopUpClick();
            }
        });

    }


    /*
    On click for top up
    Update the child balance on Firestore, then updateUI to reflect the change
     */
    public void onTopUpClick(){

        // Retrieve values
        EditText topUpAmountView = findViewById(R.id.Topup_amnt);
        String topUpAmountText = topUpAmountView.getText().toString();

        try {
            // Change to BigDecimal
            BigDecimal topUpAmount = new BigDecimal(topUpAmountText);
            int amount = topUpAmount.scaleByPowerOfTen(2).intValue();

            // Update child balance
            child.update("Amount", amount, new FirebaseCallback() {
                @Override
                public void onResponse(Map<String, Object> documents) {
                    // on Complete, updateUI
                    Toast.makeText(
                            TopupActivity.this,
                            "Top Up Successful!",
                            Toast.LENGTH_SHORT)
                            .show();
                    updateUI();

                }
            });

        }catch (Exception e){
            Toast.makeText(TopupActivity.this,"Please enter a numeric value",Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.toString());
        }

        // refresh textViews
        topUpAmountView.getText().clear();
    }


}
