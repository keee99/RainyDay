
import com.example.loginattempt1.AuthClass;

import android.view.View;
import android.util.Log;
import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

// Firebase Auth stuff
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Firebase Firestore stuff
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

// Java Util
import java.util.HashMap;
import java.util.Map;


// Reference methods to use for Firebase Firestore
// Can either create method within the class to improve modularity,
// or just copy and paste the code over.
// RMB import whatever necessary
class FirebaseReference {

    // =============================================================================================
    // Instantiate important stuff
    private FirebaseAuth mAuth; // To retrieve UID
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String TAG = "FirebaseRef";

    // Get Current User:
    FirebaseUser user = AuthClass.getCurrentUser();

    // =============================================================================================
    // Change write data accordingly
    // Change collection name accordingly
    private void WRITE() {

        // Cloud Firestore supports writing documents with custom classes. Cloud Firestore converts the objects to supported data types.
        // Each custom class must have a public constructor that takes no arguments. In addition, the class must include a public getter for each property.

        // Create a new user with a first and last name
        // Any object to add as a document needs to be a Map<String, Object>
        // Copy over the schema from schema.java and fill in accordingly
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

        // Add a new document with a generated ID
        db.collection("testColle").document() // Change CollectionPath accordingly
                .set(user, SetOptions.merge())

                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void documentReference) {
                        Log.d(TAG, "DocumentSnapshot added");
                        // ...
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        // ...
                    }
                });

    }

    // =============================================================================================
    // Change query accordingly
    // Change collection name accordingly
    private void READ() {

        // Read data from collection
        db.collection("users")
                .get() // gets the ENTIRE collection -- Change query accordingly

                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            // ...
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                            // ...
                        }
                    }
                });
    }

    // =============================================================================================
    // Todos
    private void UPDATE() {

    }

    // =============================================================================================
    // Todos
    private void DELETE() {

    }



    // Firebase get if logged in ===================================================================
    private void checkAuth() {
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        });
    }



    // Firebase get current user's username ========================================================
    private void getUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String username = user.getDisplayName();
    }


    // Firebase change current user's username =====================================================
    private void changeUsername() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        String username = user.getDisplayName();
    }



    // Ignore this, this just to make the words coloured lol
    void filler(){
        WRITE();
        READ();
    }
}
