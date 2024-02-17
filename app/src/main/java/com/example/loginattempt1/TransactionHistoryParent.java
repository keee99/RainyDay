package com.example.loginattempt1;

import android.os.Bundle;
import com.example.loginattempt1.schemas.Transaction;

public class TransactionHistoryParent extends ParentAbstractActivity {

    // Transaction object initialization
    Transaction tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transation_history_p);


        UIClass.setParentText(this,
                mPreferences.getString("CurrentChildName", "your Child"),
                "Here is an account of ",
                "'s spendings and savings."
                );


        // Transaction instance linked to child
        tt = new Transaction.Builder()
                 .SetUid(mPreferences.getString("CurrentChild", "null"))
                 .Build();

        // Creates the Transaction table
        UIClass.readTransaction(this, tt, true);
    }



}
