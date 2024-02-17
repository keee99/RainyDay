package com.example.loginattempt1.schemas;
import android.util.Log;

import com.google.firebase.Timestamp;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transaction extends Schema implements FirebaseCallback{

    private ArrayList<Map<String,Object>>  readData = new ArrayList<>();

    // Retrieve attributes from builder
    private Transaction(Transaction.Builder B){
        setData(B.ohyeah);
        setUID(B.UID);
        setCollection("Transaction");
    }


    /**
     * Read Money from the Map. ONLY USE IT with the Map you get from read() of this class object.
     * @return BigDecimal , which is money.
     */
    public BigDecimal getAmount(Map<String,Object> OB) {
        return BigDecimal.valueOf(Integer.parseInt((String) OB.get("Amount")),2);
    }

    /**
     * Read one or multiple record based on the value set before.
     * @return an ArrayList with Map<String,Object> as element, where the key is the field, and the value is the data.
     */
    public ArrayList<Map<String,Object>> read()
    {setData(new HashMap<>());
        read(this);
        return readData;
    }

    /**
     * Read one or multiple record based on the value set before with custom callback function.
     */
    public void read(FirebaseCallback callback)
    {
        setData(new HashMap<>());
        super.read(callback);
    }

    /**
     * inserting record value by append value into the array where field is always Record.
     */
    public void update(FirebaseCallback callback)
    {
        Map<String,Object> temp = new HashMap<String,Object>()
        {{
            put("Action","");
            put("Amount",0);
            put("Item","");
            put("Timestamp",new Timestamp(new Date()).toString());
        }};
        for(String key : temp.keySet())
        {
            if(!getData().containsKey(key))
            {
                setData(key,temp.get(key));
            }
        }
        update_Array("Record",getData(),callback);
    }

    /**
     * Create new record in Firestore with a Fixed schema.
     * Fields that haven't been set will be replaced by default value for symmetry purposes.
     */
    public void create(FirebaseCallback callback)
    {
        Map<String,Object> ok = new HashMap<>();
        ok.put("Record",new ArrayList<Map<String,String>>());
        setData(ok);
        super.write(callback);
    }

    /**
     * Reset the Schema .
     */
    public void reset()
    {
        readData = new ArrayList<>();
        setData(new HashMap<>());
        setUID(null);
    }

    /**
     * Gets transaction record
     */
    public Map<String,Object>[] getRecord(Map<String,Object> OB)
    {
        HashMap<String,Object>[] yes =(HashMap<String, Object>[]) OB.get("Record");
        for(HashMap<String,Object> k : yes)
        {
            k.put("Amount",BigDecimal.valueOf(Integer.parseInt((String)k.get("Amount")),-2));
        }
        return yes;
    }


    @Override
    public void onResponse(Map<String, Object> documents) {
        try {
            readData.add(documents);
        }
        catch (Exception e)
        {
            Log.w("Missing boi:",e);
        }
    }

    // Static Builder Class
    public  static class Builder {
        private Map<String,Object> ohyeah = new HashMap<>();
        private String UID = null;
        public Builder SetAmount(BigDecimal money)
        {
            String a = Integer.toString(money.scaleByPowerOfTen(2).intValue());
            ohyeah.put("Amount",a);
            return this;
        }
        public Builder SetUid(String Uid)
        {
            UID = Uid;
            return this;
        }
        public Builder SetAction(String Category)
        {
            ohyeah.put("Action", Category);
            return this;
        }
        public Builder SetItem(String Item)
        {
            ohyeah.put("Item",Item);
            return this;
        }

        public Transaction Build()
        {
            Transaction temp = new Transaction(this);
            return temp;
        }
    }
}
