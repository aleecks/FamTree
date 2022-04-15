package com.imf.famtree;

import static android.content.ContentValues.TAG;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;
import java.util.Map;

public class ManejadorBD {

    public ManejadorBD() {
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void crearUsuario(String uid, String displayName, String email) {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("nombre", displayName);
        user.put("email", email);
        //user.put("foto", photoUrl);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }

                });
    }

    public void crearArbol(User user) {
        // Create a new user with a first and last name
        Map<String, Object> tree = new HashMap<>();

        // Add a new document with a generated ID
        db.collection("users").document(user.getUid()).collection("tree")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
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
