package com.example.loginattempt1.schemas;

import android.nfc.Tag;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;


// Firebase Firestore stuff
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

// Java Util
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public abstract class Schema {

    // collection String has a getter and setter method.
    // Call all setters here when calling subclass constructor
    private String collection;
    private String UID=null;


    private Map<String,Object> data = new HashMap<>();
    protected Schema () {
        setCollection(null);
    }


    // Getters and Setters =========================================================================
    protected Map<String, Object> getData() {
        return data;
    }
    // Overload setData
    protected void setData(Map<String, Object> data) {
        this.data = data;
    }
    protected void setData(String str , Object T){data.put(str,T);}

    protected String getCollection() { return collection; }
    protected void setCollection(String collection) { this.collection = collection; }

    public String getUID() { return UID; }
    public void setUID(String UID) { this.UID = UID; }

    // =============================================================================================
    // Instantiate important stuff
    // private FirebaseUser user = AuthClass.getCurrentUser(); // To retrieve UID
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "DBOperations";



    // useCurrentUserdata -----------------------------------------------------------
    // Uses the current user data of this particular schema in the callback function
    // Passes a List of DocumentSnapshots into the callback function.
    /**
     *
     * @param callback: callback function to run after async returns.
     */
    protected void read(FirebaseCallback callback) {

        if(UID==null||!data.isEmpty())
        {
           CollectionReference Q= db.collection( getCollection() );
            Task<QuerySnapshot> Task;
           if(!data.isEmpty())
           {
               Query AQ = Q;
               for(String k : data.keySet())
               {
                   AQ = AQ.whereEqualTo(k,data.get(k));
               }
               Task = AQ.get();
           }
           else
               Task = Q.get();
                    Task.addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Map<String, Object> doc = document.getData();

                        // If the schema doesnt contain a UID, add it into the response map
                        if (UID == null) { doc.put("UID", document.getId()); }

                        // Call callback with the returned Map
                        callback.onResponse(doc);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        }
        else if(UID!=null)
        {
            Log.i(TAG, "UID is not null------------------------------------------");
            db.collection( getCollection() )
                    .document( getUID() )
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                Map<String, Object> documents = task.getResult().getData();
                                callback.onResponse(documents);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        }
        else
            Log.w(TAG,"why you get with UID and filter ? ");

    }


    /**
     *  Updates integer data on Firestore by INCREMENTING value attribute
     * @param field: data field String
     * @param value: value increment int
     */
    protected void update_int(String field,int value)
    {
        db.collection( getCollection() )
                .document( getUID() ) // Change CollectionPath accordingly
                .update(field, FieldValue.increment(value))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "transaction record added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    /**
     *  Updates integer data on Firestore by INCREMENTING value attribute, then calls callback
     * @param field: data field String
     * @param value: value increment int
     * @param callback: FirebaseCallback to run callback function
     */
    protected void update_int(String field,int value, FirebaseCallback callback)
    {
        db.collection( getCollection() )
                .document( getUID() ) // Change CollectionPath accordingly
                .update(field, FieldValue.increment(value))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "transaction record added");
                        callback.onResponse(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    /**
     * Updates array data on Firestore by union with value attribute object
     * @param field: data field String
     * @param value: Array to Union with existing data
     */
    protected void update_Array(String field,Object value)
    {
        db.collection( getCollection() )
                .document( getUID() ) // Change CollectionPath accordingly
                .update(field, FieldValue.arrayUnion(value))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "transaction record added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    /**
     * Updates array data on Firestore by union with value attribute object, then calls callback
     * @param field: data field String
     * @param value: Array to Union with existing data
     * @param callback: FirebaseCallback to run callback function
     */
    protected void update_Array(String field,Object value, FirebaseCallback callback)
    {
        db.collection( getCollection() )
                .document( getUID() ) // Change CollectionPath accordingly
                .update(field, FieldValue.arrayUnion(value))
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "transaction record added");
                        callback.onResponse(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    /**
     * Writes the data within the schema instance into the corresponding DB collection
     */
    protected void write() {
        if(getUID()!=null)
        {
            // Add a new document with a generated ID
            db.collection( getCollection() )
                    .document( getUID() ) // Change CollectionPath accordingly
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void documentReference) {
                            Log.d(TAG, "DocumentSnapshot added");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        } else {
            // Add a new document with a generated ID
            db.collection( getCollection() )
                    .document() // Change CollectionPath accordingly
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void documentReference) {
                            Log.d(TAG, "DocumentSnapshot added");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }


    /**
     * Writes the data within the schema instance into the corresponding DB collection
     * @param callback: Firebase callback, which it runs with null param
     */
    protected void write(FirebaseCallback callback) {
        if(getUID()!=null)
        {
            // Add a new document with a generated ID
            db.collection( getCollection() )
                    .document( getUID() ) // Change CollectionPath accordingly
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void documentReference) {
                            Log.d(TAG, "DocumentSnapshot added");
                            callback.onResponse(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        } else {
            // Add a new document with a generated ID
            db.collection( getCollection() )
                    .document() // Change CollectionPath accordingly
                    .set(data, SetOptions.merge())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void documentReference) {
                            Log.d(TAG, "DocumentSnapshot added");
                            callback.onResponse(null);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }









}
