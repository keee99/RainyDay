package com.example.loginattempt1.schemas;

import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class User extends Schema implements FirebaseCallback{

    private static final String TAG = "User";
    private ArrayList<Map<String,Object>>  readData = new ArrayList<>();

    // Retrieve attributes from builder
    private User(Builder B){
        setData(B.ohyeah);
        setCollection("User");
        setUID(B.UID);
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

    /**
     * Read Money from the Map. ONLY USE IT with the Map you get from read() of this class object.
     * @return BigDecimal , which is money.
     */
    public BigDecimal getAmount(Map<String,Object> OB) {
        if (OB!= null) Log.d(TAG, OB.toString());
        else Log.d(TAG, "null");

        if (OB.get("Amount") == null) return new BigDecimal(0);

        return BigDecimal.valueOf((long)OB.get("Amount"),2);
    }


    /**
     * Read IsParent from the Map. ONLY USE IT with the Map you get from read() of this class object.
     * @return IsParent , boolean to check is parent or not.
     */
    public boolean isParent(Map<String,Object> OB) {
        return (boolean)OB.get("IsParent");
    }

    /**
     * Read Username from the Map. ONLY USE IT with the Map you get from read() of this class object.
     * @return Username , String .
     */
    public String getUsername(Map<String,Object> OB) {
        return OB.get("Username").toString();
    }


    /**
     * Read Children from the Map. ONLY USE IT with the Map you get from read() of this class object.
     * @return a String array that contains Firebase DocumentUID, which can be used as param of setUid().
     */
    public ArrayList<String> getRelationship(Map<String,Object> OB) {
        try {
            return (ArrayList<String>) OB.get("Relationship");
        } catch (Exception e) {
            Log.w(TAG, "Exception in casting relationship to arraylist");
            return null;
        }

    }


    /**
     * Read one or multiple record based on the value set before.
     * @return an ArrayList with Map<String,Object> as element, where the key is the field, and the value is the data.
     */
    public ArrayList<Map<String,Object>> read()
    {
        read(this);
        return readData;
    }


    /**
     * Read one or multiple record based on the value set before with custom callback function.
     */
    public void read(FirebaseCallback callback)
    {
        super.read(callback);
    }


    /**
     * Update current record value by replacing value where field is specified.
     */
    public void update()
    {
        super.write();
    }


    /**
     * inserting record value by append value into the array where field is specified.
     */
    public void update(String field, Object ok)
    {
        update_Array(field,ok);
    }
    public void update(String field, Object ok, FirebaseCallback callback)
    {
        update_Array(field,ok,callback);
    }


    /**
     * update record value by doing arithmetic operation in Firestore where field is specified.
     */
    public void update(String field, int ok)
    {
        update_int(field,ok);
    }
    public void update(String field, int ok, FirebaseCallback callback)
    {
        update_int(field,ok, callback);
    }


    /**
     * Create new record in Firestore with a Fixed schema.
     * Fields that haven't been set will be replaced by default value for symmetry purposes.
     */
    public void create()
    {
        Map<String,Object> temp = new HashMap<String,Object>()
        {{
            put("Amount",0);
            put("IsParent",false);
            put("Username","");
            put("CurrentChild", null);
        }};
        for(String key : temp.keySet())
        {
            if(!getData().containsKey(key))
            {
                setData(key,temp.get(key));
            }
        }
        super.write();
    }
    // Overloaded Create() to accept and call a FirebaseCallback
    public void create(FirebaseCallback callback)
    {
        Map<String,Object> temp = new HashMap<String,Object>()
        {{
            put("Amount",0);
            put("IsParent",false);
            put("Username","");
        }};
        for(String key : temp.keySet())
        {
            if(!getData().containsKey(key))
            {
                setData(key,temp.get(key));
            }
        }
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
     * Schema Builder Class
     */
    public static class Builder {
        private final ArrayList<String> Relationship = new ArrayList<String>();
        private final Map<String,Object>ohyeah = new HashMap<>();
        private String UID=null;
        /**
         * Set Money for Filtering / creating new User.
         * @param money
         */
        public Builder SetAmount(BigDecimal money)
        {
            ohyeah.put("Amount",money.setScale(2).intValue());
            return this;
        }
        /**
         * Set uid for getting specific document (a.k.a get certain user).
         * @param Uid
         */
        public Builder SetUid(String Uid)
        {
            UID = Uid;
            return this;
        }
        /**
         * Set this User to be Parent or not for Filtering / creating new User.
         * @param IsParent
         */
        public Builder SetType(boolean IsParent)
        {
            ohyeah.put("IsParent",IsParent);
            return this;
        }
        /**
         * Set username for Filtering / creating new User.
         * @param username
         */
        public Builder SetUsername(String username)
        {
            ohyeah.put("Username",username);
            return this;
        }

        /**
         * Specify Children of this User. ONLY USE IT when you want to create/update this user.
         * @param uuid, the Firebase DocumentUid of children.
         */
        public Builder SetRelationship(String uuid)
        {
            if(!Relationship.contains(uuid)){
                Relationship.add(uuid);
            }
            return this;
        }
        public User Build()
        {
            if (!Relationship.isEmpty()){
                ohyeah.put("Relationship",Relationship.toArray());
            }

            return new User(this);
        }
    }

}