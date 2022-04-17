package com.imf.famtree;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.imf.famtree.beans.Miembro;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ManejadorBD {

    public ManejadorBD() {
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage = FirebaseStorage.getInstance();


    public void crearUsuario(String uid, String displayName, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("nombre", displayName);
        user.put("email", email);
        //user.put("url_foto", photoUrl);

        db.collection("users").document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario añadido"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo añadir el usuario", e));
    }

    @SuppressLint("RestrictedApi")
    public void crearArbol(@NonNull User user) {
        Map<String, Object> tree = new HashMap<>();

        // Add a new document with a generated ID
        db.collection("users").document(user.getEmail()).collection("tree").document("arbol1")
                .set(tree)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo arbol creado"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el arbol", e));
    }

    @SuppressLint("RestrictedApi")
    public void anadirMiembro(@NonNull User user, @NonNull Miembro miembro) {
        Map<String, Object> nuevoMiembro = new HashMap<>();
        nuevoMiembro.put("nombre", miembro.getNombre());
        nuevoMiembro.put("apellido_1", miembro.getApellido1());
        nuevoMiembro.put("apellido_2", miembro.getApellido2());
        nuevoMiembro.put("fecha_nacimiento", miembro.getFechaNacimiento());
        nuevoMiembro.put("fecha_defuncion", miembro.getFechaDefuncion());
        // nuevoMiembro.put("url_foto", miembro.getFoto());
        // nuevoMiembro.put("descripcion", miembro.getDescripcion();


        if (Double.parseDouble(miembro.getTipo()) < 4) {
            db.collection("users").document(user.getEmail()).collection("tree").document("arbol1").collection("bisabuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo bisabuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el bisabuelo", e));

        } else if (Double.parseDouble(miembro.getTipo()) < 6) {
            db.collection("users").document(user.getEmail()).collection("tree").document("arbol1").collection("abuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo abuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el abuelo", e));
        } else {
            db.collection("users").document(user.getEmail()).collection("tree").document("arbol1").collection("padres").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo padre añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el padre", e));
        }

    }

    public void subirFoto() {
        StorageReference storageRef = storage.getReference();

        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());

        UploadTask uploadTask = riversRef.putFile(file);
        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(error -> Log.w(TAG, "No se pudo guardar el abuelo", error))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "La imagen se ha subido correctamente"));

    }


}













