package com.imf.famtree;

import static android.content.ContentValues.TAG;


import android.util.Log;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.HashMap;
import java.util.Map;

public class ManejadorBD {

    public ManejadorBD() {
    }

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    // private final FirebaseStorage storage = FirebaseStorage.getInstance();


    public void crearUsuario(String uid, String displayName, String email, String photoUrl) {
        Map<String, Object> user = new HashMap<>();
        user.put("uid", uid);
        user.put("nombre", displayName);
        user.put("email", email);
        user.put("url_foto", photoUrl);

        db.collection("users").document(email)
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario añadido"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo añadir el usuario", e));
    }

    public void crearArbol(String userEmail, @NonNull Arbol arbol) {
        String nombreArbol = arbol.getNombreArbol();
        String tipoArbol = arbol.getTipoArbol();
        Miembro miembro;
        Map<String, Object> nuevoMiembro = new HashMap<>();
        Map<String, Object> nombreHM = new HashMap<>();

        // guardar nombre del arbol
        nombreHM.put("nombre_arbol", nombreArbol);
        db.collection("users").document(userEmail).collection("tree").document(tipoArbol)
                .set(nombreHM)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Bisabuelo añadido"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el bisabuelo", e));

        // subir bisabuelos
        for (int i = 0; i < arbol.getBisabuelos().size(); i++) {
            miembro = arbol.getBisabuelos().get(i);
            rellenarMiembro(miembro, nuevoMiembro);

            db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection("bisabuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Bisabuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el bisabuelo", e));

        }

        // subir abuelos
        for (int i = 0; i < arbol.getAbuelos().size(); i++) {
            miembro = arbol.getAbuelos().get(i);
            rellenarMiembro(miembro, nuevoMiembro);

            db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection("abuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Abuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el abuelo", e));

        }

        // subir padres
        for (int i = 0; i < arbol.getPadres().size(); i++) {
            miembro = arbol.getPadres().get(i);
            rellenarMiembro(miembro, nuevoMiembro);

            db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection("padres").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Padre añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el abuelo", e));

        }

        miembro = arbol.getTu();
        rellenarMiembro(miembro, nuevoMiembro);
        db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection("usuario").document(miembro.getTipo())
                .set(nuevoMiembro)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario añadido al arbol"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el usuario en el arbol", e));
    }

    private void rellenarMiembro(@NonNull Miembro miembro, @NonNull Map<String, Object> nuevoMiembro) {
        nuevoMiembro.put("nombre", miembro.getNombre());
        nuevoMiembro.put("apellido_1", miembro.getApellido1());
        nuevoMiembro.put("apellido_2", miembro.getApellido2());
        nuevoMiembro.put("fecha_nacimiento", miembro.getFechaNacimiento());
        nuevoMiembro.put("fecha_defuncion", miembro.getFechaDefuncion());
        nuevoMiembro.put("url_foto", miembro.getUrlFoto());

    }

    /*public void anadirMiembro(String email, String nombreArbol, @NonNull Miembro miembro) {
        Map<String, Object> nuevoMiembro = new HashMap<>();
        nuevoMiembro.put("nombre", miembro.getNombre());
        nuevoMiembro.put("apellido_1", miembro.getApellido1());
        nuevoMiembro.put("apellido_2", miembro.getApellido2());
        nuevoMiembro.put("fecha_nacimiento", miembro.getFechaNacimiento());
        nuevoMiembro.put("fecha_defuncion", miembro.getFechaDefuncion());
        nuevoMiembro.put("url_foto", miembro.getUrlFoto());

        if (Double.parseDouble(miembro.getTipo()) < 4) {
            db.collection("users").document(email).collection("tree").document(nombreArbol).collection("bisabuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo bisabuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el bisabuelo", e));

        } else if (Double.parseDouble(miembro.getTipo()) < 6) {
            db.collection("users").document(email).collection("tree").document(nombreArbol).collection("abuelos").document(miembro.getTipo())
                    .set(nuevoMiembro)
                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Nuevo abuelo añadido"))
                    .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el abuelo", e));
        } else {
            db.collection("users").document(email).collection("tree").document(nombreArbol).collection("padres").document(miembro.getTipo())
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

    }*/


}













