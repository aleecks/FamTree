package com.imf.famtree;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;
import com.imf.famtree.inicio.Inicio;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private String tipoArbol, nombreArbol;

    private boolean isArbol1, isArbol2, isArbol3, comprobador;

    private TextView lblNombre, lblCorreo, lblId;
    private Button btnLogOut, btnCrearArbol1, btnCrearArbol2, btnCrearArbol3;

    private Intent iInicio, iArbol, iMostrarArbol;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private ManejadorBD bd;
    private Arbol arbol;
    private Miembro miembro;
    private Map<String, Object> miembroObtenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_home);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnCrearArbol1 = findViewById(R.id.btnArbol1);
        btnCrearArbol2 = findViewById(R.id.btnArbol2);
        btnCrearArbol3 = findViewById(R.id.btnArbol3);
        lblNombre = findViewById(R.id.lblNombre);
        lblCorreo = findViewById(R.id.lblCorreo);
        lblId = findViewById(R.id.lblUID);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        bd = new ManejadorBD();

        iInicio = new Intent(this, Inicio.class);
        iArbol = new Intent(this, CrearMiembro.class);
        iMostrarArbol = new Intent(this, MostrarArbol.class);

        lblNombre.setText(user.getDisplayName());
        lblCorreo.setText(user.getEmail());
        lblId.setText(user.getUid());

        // -------------- LISTENERS --------
        btnLogOut.setOnClickListener(this);
        btnCrearArbol1.setOnClickListener(this);
        btnCrearArbol2.setOnClickListener(this);
        btnCrearArbol3.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        comprobarArboles();
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(iInicio);
                break;

            case R.id.btnArbol1:
                if (!isArbol1) {
                    tipoArbol = "arbol1";
                    mostrarAlert();
                } else {
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol2:
                if (!isArbol2) {
                    tipoArbol = "arbol2";
                    mostrarAlert();
                } else {
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol3:
                if (!isArbol3) {
                    tipoArbol = "arbol3";
                    mostrarAlert();
                } else {
                    startActivity(iMostrarArbol);
                }
        }
    }


    // ----------------------------- COMPROVACIONES Y UTILIDADES -------------------------
    private void comprobarArboles() {
        db.collection("users").document(user.getEmail()).collection("tree").document("arbol1").collection("bisabuelos").document("0.1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol1 existe");
                    isArbol1 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol1.setTextColor(Color.rgb(200, 100, 220));

                } else {
                    Log.d(TAG, "arbol1 no existe");
                    isArbol1 = false;
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        db.collection("users").document(user.getEmail()).collection("tree").document("arbol2").collection("bisabuelos").document("0.1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol2 existe");
                    isArbol2 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol2.setTextColor(Color.rgb(200, 100, 220));
                } else {
                    Log.d(TAG, "arbol2 no existe");
                    isArbol2 = false;
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        db.collection("users").document(user.getEmail()).collection("tree").document("arbol3").collection("bisabuelos").document("0.1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol3 existe");
                    isArbol3 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol3.setTextColor(Color.rgb(200, 100, 220));
                } else {
                    Log.d(TAG, "arbol3 no existe");
                    isArbol3 = false;
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

    }

    private void mostrarAlert() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        alert.setTitle("Introduce el nombre de tu FamTree");

        EditText txtNombreArbol = new EditText(this);
        txtNombreArbol.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        alert.setView(txtNombreArbol);

        alert.setPositiveButton("Continuar", (dialogInterface, i) -> {
            nombreArbol = txtNombreArbol.getText().toString();

            if (nombreArbol.length() > 3) {
                iArbol.putExtra("nombreArbol", nombreArbol);
                iArbol.putExtra("tipoArbol", tipoArbol);
                startActivity(iArbol);
            } else {
                Toast.makeText(getApplicationContext(), "El nombre del arbol es muy pequeño", Toast.LENGTH_SHORT).show();
            }

        });

        alert.setNeutralButton("Cancelar", (dialogInterface, i) -> {

        });

        alert.show();

    }

    @NonNull
    private Arbol obtenerArbol(String tipoArbol, String userEmail) {
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
        try {
            if (rellenarMiembro(tipoArbol, userEmail, tipoMiembro, numeroMiembro)) {
                miembro.setTipo(numeroMiembro);
                miembro.setNombre(miembroObtenido.get("nombre").toString());
                miembro.setApellido1(miembroObtenido.get("apellido_1").toString());
                miembro.setApellido2(miembroObtenido.get("apellido_2").toString());
                miembro.setFechaNacimiento(miembroObtenido.get("fecha_nacimiento").toString());
                miembro.setFechaDefuncion(miembroObtenido.get("fecha_defuncion").toString());
                miembro.setUrlFoto(miembroObtenido.get("url_foto").toString());
            } else {
                Log.d(TAG, "No se encontro miembro");
            }

        } catch (NullPointerException e) {
            Log.d(TAG, "No se encontro miembro");
            e.printStackTrace();

        } catch (
                Exception e) {
            e.printStackTrace();
        }

        return miembro;
    }

    private boolean rellenarMiembro(String tipoArbol, String userEmail, String tipoMiembro, String numeroMiembro) {
        DocumentReference docRef = db.collection("users").document(userEmail).collection("tree").document(tipoArbol).collection(tipoMiembro).document(numeroMiembro);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        miembroObtenido = document.getData();
                        comprobador = true;
                    } else {
                        Log.d(TAG, "No such document");
                        comprobador = false;
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                    comprobador = false;
                }
            });
        } catch (Exception e) {

        }
        return comprobador;
    }
}




















