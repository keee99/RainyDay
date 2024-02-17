package com.example.loginattempt1;

import android.content.Intent;
import android.util.Log;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.Transaction;
import com.github.mikephil.charting.charts.PieChart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class HomeAbstractActivity extends FunctionAbstractActivity{

    // Initialize UI (pie chart) stuff
    public PieChart pieChart;
    public Map<String, BigDecimal> transactions = new HashMap<>();


    /*
    updateUIElements: Updates the PieChart as well as the text tags
     */
    @Override
    void updateUIElements(){
        Log.w("OK","YEYE") ;


        // Update Balance if logged in
        // params: context and User child (FunctionAbstractActivity)
        UIClass.setBalance(this, child);


        // Get Pie Chart
        pieChart=findViewById(R.id.chart1);
        transactions.clear();

        // Build child's Transaction object
        Transaction data = new Transaction.Builder()
                .SetUid(child.getUID())
                .Build();

        // Read transaction data from Firestore
        data.read(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {
                // Get data in ArrayList
                ArrayList<Map<String, Object>> records = (ArrayList<Map<String, Object>>) documents.get("Record");

                // Insert data into a Map
                for(Map<String, Object> record: records){

                    String category = (String) record.get("Action");
                    BigDecimal amount = data.getAmount(record);

                    if (transactions.containsKey(category)) {
                        amount = amount.add(transactions.get(category));
                    }
                    transactions.put(category, amount);
                }

                // Load the Pie Chart according to the data provided
                UIClass.setupPieChart(pieChart);
                UIClass.loadPieChartData(pieChart, transactions);
            }
        });

        UIClass.setupPieChart(pieChart);
        UIClass.loadPieChartData(pieChart, transactions);
    }


}
