package com.example.loginattempt1;

import android.os.Bundle;
import com.example.loginattempt1.schemas.Transaction;

public class TransactionHistoryChild extends ChildAbstractActivity {

    // Transaction object initialization
    Transaction tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transation_history_c);

        // Instantiate transaction object for current child user
        tt = new Transaction.Builder()
                .SetUid(AuthClass.getCurrentUser().getUid())
                .Build();

        // Creates the Transaction table
        UIClass.readTransaction(this, tt, false);
    }


}
