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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MostrarArbol extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolver, btnEliminar;
    private Button btn01, btn02, btn11, btn12, btn21, btn22, btn31, btn32, btn41, btn42, btn51, btn52, btn61, btn62, btnUsuario;

    private Intent iVolver;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private Arbol arbol;
    private Map<String, Object> miembroObtenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_arbol);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        btnVolver = findViewById(R.id.btnVolver);
        btnEliminar = findViewById(R.id.btnEliminar);
        btn01 = findViewById(R.id.btn01);
        btn02 = findViewById(R.id.btn02);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn21 = findViewById(R.id.btn21);
        btn22 = findViewById(R.id.btn22);
        btn31 = findViewById(R.id.btn31);
        btn32 = findViewById(R.id.btn32);
        btn41 = findViewById(R.id.btn41);
        btn42 = findViewById(R.id.btn42);
        btn51 = findViewById(R.id.btn51);
        btn52 = findViewById(R.id.btn52);
        btn61 = findViewById(R.id.btn61);
        btn62 = findViewById(R.id.btn62);
        btnUsuario = findViewById(R.id.btnUsuario);

        iVolver = new Intent(this, Home.class);

        arbol = new Arbol();
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        arbol = obtenerArbol(getIntent().getStringExtra("tipo_arbol"), user.getEmail());

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btn01.setOnClickListener(this);
        btn02.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
        btn21.setOnClickListener(this);
        btn22.setOnClickListener(this);
        btn31.setOnClickListener(this);
        btn32.setOnClickListener(this);
        btn41.setOnClickListener(this);
        btn42.setOnClickListener(this);
        btn51.setOnClickListener(this);
        btn52.setOnClickListener(this);
        btn61.setOnClickListener(this);
        btn62.setOnClickListener(this);
        btnUsuario.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                startActivity(iVolver);
                break;

            case R.id.btnEliminar:
                Toast.makeText(this.getApplicationContext(), arbol.toString(), Toast.LENGTH_LONG);
                break;

            case R.id.btn01:

                break;

            case R.id.btn02:

                break;

            case R.id.btn11:

                break;

            case R.id.btn12:

                break;

            case R.id.btn21:

                break;
            case R.id.btn22:

                break;

            case R.id.btn31:

                break;

            case R.id.btn32:

                break;

            case R.id.btn41:

                break;

            case R.id.btn42:

                break;

            case R.id.btn51:

                break;

            case R.id.btn52:

                break;

            case R.id.btn61:

                break;

            case R.id.btn62:

                break;

            case R.id.btnUsuario:


        }
    }

    @NonNull
    private Arbol obtenerArbol(String tipoArbol, String userEmail) {
        Miembro miembro;

        try {
            // añadir bisabuelos
            List<String> numeroMiembro = Arrays.asList("0.1", "0.2", "1.1", "1.2", "2.1", "2.2", "3.1", "3.2");
            for (int i = 0; i < 8; i++) {
                miembro = new Miembro();
                rellenarMiembro(miembro, tipoArbol, userEmail, "bisabuelos", numeroMiembro.get(i));
            }

            // añadir abuelos
            List<String> numeroMiembro1 = Arrays.asList("4.1", "4.2", "5.1", "5.2");
            for (int i = 0; i < 4; i++) {
                miembro = new Miembro();
                rellenarMiembro(miembro, tipoArbol, userEmail, "abuelos", numeroMiembro1.get(i));
            }

            // añadir padres
            List<String> numeroMiembro2 = Arrays.asList("6.1", "6.2");
            for (int i = 0; i < 2; i++) {
                miembro = new Miembro();
                rellenarMiembro(miembro, tipoArbol, userEmail, "padres", numeroMiembro2.get(i));
            }

            // añadir miembro princiapal
            miembro = new Miembro();
            rellenarMiembro(miembro, tipoArbol, userEmail, "usuario", "usuario");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return arbol;
    }

    private void rellenarMiembro(Miembro miembro, String tipoArbol, String userEmail, String tipoMiembro, String numeroMiembro) {
        DocumentReference docRef = db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection(tipoMiembro).document(numeroMiembro);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
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
                                Log.d(TAG, "TERMINDO");
                                Log.d(TAG, arbol.toString());
                                break;

                            case "usuario":
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