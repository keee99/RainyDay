package com.example.loginattempt1;

import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.Transaction;
import com.example.loginattempt1.schemas.User;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Map;

public class UIClass {

    static ArrayList<PieEntry> entries = new ArrayList<>();
    static final String TAG = "UIClass";


    // =============================================================================================
    // Pie Chart Functions =========================================================================
    // =============================================================================================

    /**
     * Set various pieChart settings
     * @param pieChart A Pie Chart Object
     **/
    protected static void setupPieChart(PieChart pieChart)
    {
        pieChart.setDrawHoleEnabled(true);
        pieChart.setUsePercentValues(true);
        pieChart.setEntryLabelTextSize(12);
        pieChart.setEntryLabelColor(Color.parseColor("#000000"));
        pieChart.setCenterText("Spending by Category");
        pieChart.setCenterTextSize(15);
        pieChart.getDescription().setEnabled(false);

        Legend l= pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }


    /**
     * Loads the various fields on the pieChart based on transactions read from Firestore
     * @param pieChart A Pie Chart object
     * @param transactions A list of transactions, with String as key and BigDecimal as value
     *
     */
    public static void loadPieChartData(PieChart pieChart, Map<String, BigDecimal> transactions) {
        // Clear current pie chart entries
        entries.clear();

        // For each transaction type, create a pir chart entry
        for (Map.Entry<String, BigDecimal> transaction: transactions.entrySet()){
            float value = transaction.getValue().floatValue();
            PieEntry pieEntry = new PieEntry(value, transaction.getKey());
            entries.add(pieEntry);
        }

        // Colour customization
        ArrayList<Integer> colors = new ArrayList<>();


        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);


        // Create the pie chart dataset
        PieDataSet dataSet = new PieDataSet(entries, "Expense Category");
        dataSet.setColors(colors);
        PieData data =new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        pieChart.setData(data);
        pieChart.invalidate();


    }

    // =============================================================================================
    // Text Functions ==============================================================================
    // =============================================================================================

    /**
     * Format name to capitalize 1st letter
     * @param name: String
     * @return reformatted name String
     */
    public static String reformattedName(String name) {
        return name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
    }

    /**
     * @return Name of current user, if exists
     */
    public static String getName() {
        FirebaseUser user = AuthClass.getCurrentUser();

        if (user != null) {
            String name = AuthClass.getCurrentUser().getDisplayName();
            assert name != null;
            return UIClass.reformattedName(name);
        } else {
            return "User";
        }
    }

    /**
     * Set the "Hello ____ " header in given context
     * @param view: view context
     */
    private static void setMainText(TextView view) {
        String username = "Hello, " + UIClass.getName() + "!";
        Log.d(TAG, username);
        view.setText(username);
    }

    /**
     * Set the "Hello ____ " header in given context for a parent Activity
     * @param view: view context
     * @param childName: String of child name
     */
    private static void setParentsChildText(TextView view, String childName) {
        String childString = "You are viewing " +
                reformattedName(childName) +
                "'s account.";
        view.setText(childString);
    }
    /**
     * Overloaded from above method: Set the header child text, with a custom message
     * @param view: context
     * @param childName: String of child name
     */
    private static void setParentsChildText(TextView view, String childName,
                                            String prefix, String suffix) {
        String childString = prefix +
                reformattedName(childName) +
                suffix;
        view.setText(childString);
    }

    /**
     * Set all texts needed for a child's activity
     * @param activity: context
     */
    public static void setChildText(AppActivity activity) {
        TextView nameView = activity.findViewById(R.id.hello_tina_);
        setMainText(nameView);
    }

    /**
     * Set all texts needed for a parent's activity
     * @param activity: context
     */
    public static void setParentText(AppActivity activity, String childName) {
        TextView nameView = activity.findViewById(R.id.hello_tina_);
        setMainText(nameView);

        // Child Name
        TextView childNameView = activity.findViewById(R.id.tommy_is_on);
        setParentsChildText(childNameView, childName);
    }
    /**
     * Overloaded from above: Set all texts needed for parent's activity,
     * with custom message for child header
     * @param activity: context
     */
    public static void setParentText(AppActivity activity, String childName,
                                     String prefix, String suffix) {
        TextView nameView = activity.findViewById(R.id.hello_tina_);
        setMainText(nameView);

        // Child Name
        TextView childNameView = activity.findViewById(R.id.tommy_is_on);
        setParentsChildText(childNameView, childName, prefix, suffix);
    }

    /**
     * ASYNC: Edit the Balance header of the page
     * @param activity: context
     * @param child: User instance of a child
     */
    public static void setBalance(AppActivity activity, User child) {
        TextView balance = activity.findViewById(R.id.balance_79_);

        child.read(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {
                BigDecimal amt = child.getAmount(documents);
                balance.setText("Balance:\n " + amt.toString());
            }
        });
    }


    // =============================================================================================
    // Transaction History Functions ===============================================================
    // =============================================================================================


    /**
     * Read transaction with the child UID --> Get a transactions as a Map<String, Object> of arrays
     * For each transaction (indexed), create a new TableRow and add it to TableLayout
     * @param activity: context
     * @param tt: Transaction object
     * @param isParent: boolean
     */
    protected static void readTransaction(AppActivity activity, Transaction tt, boolean isParent) {


        tt.read(new FirebaseCallback() {
            @Override
            public void onResponse(Map<String, Object> documents) {
                TableLayout tl = activity.findViewById(R.id.table);

                ArrayList<Map<String, Object>> transactions = (ArrayList<Map<String, Object>>) documents.get("Record");
                BigDecimal totalSpending = new BigDecimal(0.00);

                for (Map<String, Object> transaction : transactions) {

                    String item = (String) transaction.get("Item");

                    BigDecimal amount = tt.getAmount(transaction);
                    totalSpending = totalSpending.add(amount);

                    /* Create a new row to be added. */
                    TableRow tr = new TableRow(activity);

                    /* Create TextViews to add to row */
                    TextView key = new TextView(activity);
                    key.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    key.setText(item);

                    key.setTextColor(Color.parseColor("#000000"));
                    key.setTextSize(16);
                    if(isParent) {
                        key.setBackgroundResource(R.drawable.table_border_content_p);
                    }
                    else{
                        key.setBackgroundResource(R.drawable.table_border_content_c);
                    }


                    TextView value = new TextView(activity);
                    value.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                    value.setGravity(Gravity.RIGHT);
                    value.setText("$" + amount.toString());

                    value.setTextColor(Color.parseColor("#000000"));
                    value.setTextSize(16);
                    if(isParent) {
                        value.setBackgroundResource(R.drawable.table_border_content_p);
                    }
                    else{
                        value.setBackgroundResource(R.drawable.table_border_content_c);
                    }


                    /* Add TextViews to row */
                    tr.addView(key);
                    tr.addView(value);

                    /* Add row to TableLayout. */
                    tl.addView(tr, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                }
                TextView spending = activity.findViewById(R.id.Spending_Amount);
                spending.setText("$" + totalSpending.toString());

            }
        });


    }

}

