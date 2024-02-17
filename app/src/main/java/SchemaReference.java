
// Firebase Auth stuff
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


// Reference Schemas for Firebase Firestore
class SchemaReference {

    // =============================================================================================

    // Get UID --> Put as static method in user class maybe
    // the shorter function was Js not Java oops :p
    public static String getUID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            return uid;
        }
        return null;
    }

    // =============================================================================================
    private static void main(String[] args) {

        // User schema --> "Users" collection  --> Man to many
        Map<String, Object> User = new HashMap<>();
        // User.add("username", ... );
        // User.add("UID", ... ); //
        // User.add("type", ... ); //--> Parent/Child
        // User.add("relationships", ...ArrayList<String> of UIDs of relationships...);
        // User.add("Bank", ... );

        // -----------------------------------------------------------------------------------------



        // -----------------------------------------------------------------------------------------

        // Transaction schema --> "Transactions" collection  --> 1 to many
        Map<String, Object> Transaction = new HashMap<>();
        //
        // Money
        // Categories
        // Subcollection of transactions

        // -----------------------------------------------------------------------------------------

        // Target Schema --> "Targets" collection  --> 1 to many
        Map<String, Object> Target = new HashMap<>();
        // Target.add("UID", ... );

        // -----------------------------------------------------------------------------------------

        // Reward Schema --> "Rewards" collection  --> 1 to many

        // ...



    }
}
