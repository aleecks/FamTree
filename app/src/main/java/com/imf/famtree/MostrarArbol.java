package com.imf.famtree;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MostrarArbol extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolver, btnEliminar;

    private Button btn01,btn02, btn11, btn12, btn21, btn22, btn31, btn32, btn41, btn42, btn51, btn52, btn61, btn62, btnTu;

    private Intent iVolver;
    private boolean comprobador;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private Arbol arbol;
    //private ArrayList<Miembro> bisabuelos, abuelos, padres;
    private Miembro miembro;
    private Map<String, Object> miembroObtenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_arbol);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnVolver = findViewById(R.id.btnVolver);
        btnEliminar = findViewById(R.id.btnEliminar);


        iVolver = new Intent(this, Home.class);

        arbol = new Arbol();
        miembro = new Miembro();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        arbol = obtenerArbol("arbol2", user.getEmail());

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                startActivity(iVolver);
        }
    }

    @NonNull
    private Arbol obtenerArbol(String tipoArbol, String userEmail) {
        Arbol arbol = new Arbol();

        try {
            // añadir bisabuelos
            List<String> numeroMiembro = Arrays.asList("0.1", "0.2", "1.1", "1.2", "2.1", "2.2", "3.1", "3.2");
            for (int i = 0; i < 8; i++) {
                rellenarMiembro(tipoArbol, userEmail, "bisabuelos", numeroMiembro.get(i));
            }

            // añadir abuelos
            List<String> numeroMiembro1 = Arrays.asList("4.1", "4.2", "5.1", "5.2");
            for (int i = 0; i < 4; i++) {
                rellenarMiembro(tipoArbol, userEmail, "abuelos", numeroMiembro1.get(i));
            }

            // añadir padres
            List<String> numeroMiembro2 = Arrays.asList("6.1", "6.2");
            for (int i = 0; i < 2; i++) {
                rellenarMiembro(tipoArbol, userEmail, "padres", numeroMiembro2.get(i));
            }

            // añadir miembro princiapal
            rellenarMiembro(tipoArbol, userEmail, "usuario", "usuario");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arbol;
    }

    private void rellenarMiembro(String tipoArbol, String userEmail, String tipoMiembro, String numeroMiembro) {
        DocumentReference docRef = db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection(tipoMiembro).document(numeroMiembro);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        comprobador = true;
                        miembroObtenido = document.getData();
                        miembro.setTipo(numeroMiembro);
                        miembro.setNombre(miembroObtenido.get("nombre").toString());
                        miembro.setApellido1(miembroObtenido.get("apellido_1").toString());
                        miembro.setApellido2(miembroObtenido.get("apellido_2").toString());
                        miembro.setFechaNacimiento(miembroObtenido.get("fecha_nacimiento").toString());
                        miembro.setFechaDefuncion(miembroObtenido.get("fecha_defuncion").toString());
                        miembro.setUrlFoto(miembroObtenido.get("url_foto").toString());

                        switch (tipoMiembro) {
                            case "bisabuelos":
                                arbol.getBisabuelos().add(miembro);
                                break;

                            case "abuelos":
                                arbol.getAbuelos().add(miembro);
                                break;

                            case "padres":
                                arbol.getPadres().add(miembro);
                                break;

                            default:
                                arbol.setTu(miembro);
                        }

                    } else {
                        Log.e(TAG, "No such document");
                    }
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}