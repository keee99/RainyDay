package com.example.loginattempt1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.Transaction;

import java.math.BigDecimal;
import java.util.Map;

public class AddTransactionChild extends ChildAbstractActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction_c);

        // Button OnClicks
        Button Home = findViewById(R.id.Home);
        Home.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                Intent intent = new Intent(AddTransactionChild.this, HomeChildActivity.class);
                startActivity(intent);
            }
        });

        Button Next = findViewById(R.id.Next);
        Next.setOnClickListener(new View.OnClickListener (){
            public void onClick(View v){
                addTransaction();
                //start recording the next transaction
            }
        });

    }


    /*
    Parses the EditText fields, then clears them upon recording
    Run on button click.
     */
    private void addTransaction() {

        EditText amountView = findViewById(R.id.Transaction_Amount);
        EditText categoryView = findViewById(R.id.Transaction_Category);
        EditText itemView = findViewById(R.id.Item_Purchased);

        BigDecimal amount = new BigDecimal(amountView.getText().toString());
        String category = categoryView.getText().toString();
        String item = itemView.getText().toString();

        onTopUpClick(amount);
        recordTransaction(amount, category, item);

        amountView.getText().clear();
        categoryView.getText().clear();
        itemView.getText().clear();

    }


    /*
    ASYNC: Records (creates) the transaction inputted.
    Run in addTransaction.
     */
    private void recordTransaction (
            BigDecimal amount,
            String category,
            String Item)
    {
        Transaction ok = new Transaction.Builder().SetUid(AuthClass.getCurrentUser().getUid())
                .SetItem(Item)
                .SetAmount(amount)
                .SetAction(category).Build();
        ok.update(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {

                Toast.makeText(AddTransactionChild.this,
                        "Transaction Added!",
                        Toast.LENGTH_LONG)
                        .show();

                updateUI();
            }
        });
    }


    /*
    ASYNC function: Deduct account balance by transaction cost
     */
    public void onTopUpClick(BigDecimal amount){

        try {

            int amountInt = amount.negate().scaleByPowerOfTen(2).intValue();
            child.update("Amount", amountInt, new FirebaseCallback() {
                @Override
                public void onResponse(Map<String, Object> documents) {

                    updateUI();
                }
            });

        }catch (Exception e){

            Toast.makeText(
                    AddTransactionChild.this,
                    "Please enter a numeric value in amount",
                    Toast.LENGTH_SHORT)
                    .show();

            Log.e(TAG, e.toString());
        }
    }


}
