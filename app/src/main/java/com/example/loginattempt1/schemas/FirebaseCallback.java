package com.example.loginattempt1.schemas;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;
import java.util.Map;

// Used to pass into firebase read methods to determine what happens onComplete.
// Reference: https://stackoverflow.com/a/66075777
// Implement this as a anonymous interface: see the above link
public interface FirebaseCallback {

    // Runs on onComplete
    void onResponse(Map<String, Object> documents);

}
