package com.imf.famtree;

import static android.content.ContentValues.TAG;


import android.util.Log;


import androidx.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        String tipoArbol = arbol.getTipoArbol();
        String nombreArbol = arbol.getNombreArbol();
        Miembro miembro;
        Map<String, Object> nuevoMiembro = new HashMap<>();
        Map<String, Object> nombreHM = new HashMap<>();

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

        // subir miembro principal
        miembro = arbol.getTu();
        rellenarMiembro(miembro, nuevoMiembro);
        nuevoMiembro.put("descripcion", miembro.getDescripcion());
        db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection("usuario").document(miembro.getTipo())
                .set(nuevoMiembro)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Usuario añadido al arbol"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el usuario en el arbol", e));

        // anadir nombre
        nombreHM.put("nombre_arbol", nombreArbol);
        db.collection("users").document(userEmail).collection("tree").document(tipoArbol)
                .set(nombreHM)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Nombre arbol añadido"))
                .addOnFailureListener(e -> Log.w(TAG, "No se pudo guardar el nombre del arbol", e));

    }

    private void rellenarMiembro(@NonNull Miembro miembro, @NonNull Map<String, Object> nuevoMiembro) {
        nuevoMiembro.put("nombre", miembro.getNombre());
        nuevoMiembro.put("apellido_1", miembro.getApellido1());
        nuevoMiembro.put("apellido_2", miembro.getApellido2());
        nuevoMiembro.put("fecha_nacimiento", miembro.getFechaNacimiento());
        nuevoMiembro.put("fecha_defuncion", miembro.getFechaDefuncion());
        nuevoMiembro.put("url_foto", miembro.getUrlFoto());

    }

    public Arbol obtenerArbol(String tipoArbol, String userEmail) {
        Arbol arbol = new Arbol();
        Miembro miembro;
        List<String> numeroMiembro = Arrays.asList("0.1", "0.2", "1.1", "1.2", "2.1", "2.2", "3.1", "3.2");

        try {
            // añadir bisabuelos
            for (int i = 0; i < 8; i++) {
                miembro = obtenerMiembro(tipoArbol, userEmail, "bisabuelos", numeroMiembro.get(i));
                arbol.getBisabuelos().add(miembro);

            }

            // añadir abuelos
            numeroMiembro = Arrays.asList("4.1", "4.2", "5.1", "5.2");
            for (int i = 0; i < 4; i++) {
                miembro = obtenerMiembro(tipoArbol, userEmail, "abuelos", numeroMiembro.get(i));
                arbol.getAbuelos().add(miembro);

            }

            // añadir padres
            for (int i = 0; i < 2; i++) {
                numeroMiembro = Arrays.asList("6.1", "6.2");
                miembro = obtenerMiembro(tipoArbol, userEmail, "padres", numeroMiembro.get(i));
                arbol.getPadres().add(miembro);
            }

            // añadir miembro princiapal
            miembro = obtenerMiembro(tipoArbol, userEmail, "usuario", "0.1");
            arbol.setTu(miembro);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return arbol;
    }

    @NonNull
    private Miembro obtenerMiembro(String tipoArbol, String userEmail, String tipoMiembro, String numeroMiembro) {
        Miembro miembro = new Miembro();
        Map<String, Object> miembroObtenido;

        try {

            /*DocumentReference docRef =  db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection(tipoMiembro).document(numeroMiembro);
            docRef.get().addOnSuccessListener(documentSnapshot -> {
                Miembro miembro1 = documentSnapshot.toObject(Miembro.class);
            });*/

            miembroObtenido = db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection(tipoMiembro).document(numeroMiembro)
                    .get().getResult()
                    .getData();

            miembro.setTipo(numeroMiembro);
            miembro.setNombre(miembroObtenido.get("nombre").toString());
            miembro.setApellido1(miembroObtenido.get("apellido_1").toString());
            miembro.setApellido2(miembroObtenido.get("apellido_2").toString());
            miembro.setFechaNacimiento(miembroObtenido.get("fecha_nacimiento").toString());
            miembro.setFechaDefuncion(miembroObtenido.get("fecha_defuncion").toString());
            miembro.setUrlFoto(miembroObtenido.get("url_foto").toString());

        } catch (NullPointerException e) {
            Log.d(TAG, "No se encontro miembro");
            e.printStackTrace();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return miembro;
    }

}













