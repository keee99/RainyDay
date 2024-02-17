package com.example.loginattempt1;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.example.loginattempt1.schemas.FirebaseCallback;
import com.example.loginattempt1.schemas.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;


public class AuthClass {

    // Get instance of FirebaseAuth object
    private static final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static final String TAG = "AuthClass";



    // getCurrentUser -----------------
    // Returns currently logged in user
    /**
     * @return Current Firebase User logged in.
     */
    public static FirebaseUser getCurrentUser() {
        return mAuth.getCurrentUser();
    }



    // signIn -------------------------------------------------------
    // Handles the firebase async code to log into a firebase account
    /**
     * @param context: AppActivity context from which the function was called
     */
    // Called within onLoginClick function
    static void signOut(AppActivity context) {

        FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    //After signout is complete
                    context.updateUI();
                }
            }
        };

        mAuth.addAuthStateListener(authStateListener);
        mAuth.signOut();
    }



    // signIn -------------------------------------------------------
    // Handles the firebase async code to log into a firebase account
    /**
     * @param email: String containing email
     * @param password: String containing password
     * @param context: AppActivity context from which the function was called
     */
    // Called within onLoginClick function
    static void signIn(String email, String password, AppActivity context) {
        // Sign_in_with_email. Referenced from
        // https://firebase.google.com/docs/auth/android/password-auth
        mAuth.signInWithEmailAndPassword(email, password)
                // Listener checks if process is complete
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    // Once complete:
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in successful: update UI with user information
                            Log.d(TAG, "Sign-in success");
                            context.updateUI();

                        } else {
                            // If sign in fails, display a message (as a Toast)
                            Log.w(TAG, "Login failed", task.getException());

                            Exception e = task.getException();

                            // TODO: cleanup: stuff the strings into strings.xml res
                            if (e instanceof FirebaseAuthInvalidUserException) {
                                Toast.makeText(
                                        context,
                                        "User not registered!",
                                        Toast.LENGTH_LONG
                                ).show();
                            } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(
                                        context,
                                        "Invalid Password",
                                        Toast.LENGTH_LONG
                                ).show();
                            }

                        }
                    }
                });

    }



    // registerNewAccount ---------------------------------------------
    // Handles the firebase async code to create a new firebase account
    /**
     * @param email: String containing email
     * @param password: String containing password
     * @param username: String containing username
     */
    // Called within onRegisterClick function
    static void registerNewAccount(String email,
                                   String password,
                                   String username,
                                   boolean isParent,
                                   AppActivity context,
                                   FirebaseCallback callback)
    {
        // Sign_in_with_email. Referenced from
        // https://firebase.google.com/docs/auth/android/password-auth
        mAuth.createUserWithEmailAndPassword(email, password)
                // Listener checks if process is complete
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    // Once complete:
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Call updateName to update name of the user that just registered.
                            updateName(username, context);

                            // Sign in successful
                            Log.d(TAG, "Sign-in success");

                            String uid = AuthClass.getCurrentUser().getUid();
                            User user = new User.Builder()
                                    .SetUid(uid)
                                    .SetType(isParent)
                                    .SetUsername(username)
                                    .Build();

                            user.create(callback);


                        } else {
                            // If sign in fails, display a message (as a Toast)
                            Log.w(TAG, "register account failed", task.getException());
                            Toast.makeText( context, "Sign in failed! Please check your parameters.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    // updateName -------------------------------------------------------
    // Updates the current user username.
    // Cancels execution if user is not logged in, displaying Toast + log.
    /**
     * @param username: String containing username to update to
     * @param context: Context of action: displays Toast at context accordingly
     */
    private static void updateName(String username, AppActivity context){

        // Create change request object
        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                .setDisplayName(username)
                .build();

        // Get firebase user and check if null
        FirebaseUser user = AuthClass.getCurrentUser();

        if (user == null) {
            Toast.makeText( context, "User not logged in", Toast.LENGTH_LONG).show();
            Log.w(TAG, "User not logged in. Update Username Failed.");
            return;
        }

        // Update profile (async). If successful, do nothing. else, log + toast
        user.updateProfile(profileUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Update Username failed", task.getException());
                        }
                    }
                });
    }




}
