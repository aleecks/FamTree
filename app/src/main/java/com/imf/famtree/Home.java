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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.imf.famtree.utilidades.Img;
import com.makeramen.roundedimageview.RoundedImageView;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private String tipoArbol, nombreArbol, userEmail;

    private boolean isArbol1, isArbol2, isArbol3;

    private Button btnCrearArbol1, btnCrearArbol2, btnCrearArbol3;
    private RoundedImageView btnVolver;

    private Intent iPerfil, iArbol, iMostrarArbol;
    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_home);

        btnCrearArbol1 = findViewById(R.id.btnArbol1);
        btnCrearArbol2 = findViewById(R.id.btnArbol2);
        btnCrearArbol3 = findViewById(R.id.btnArbol3);
        btnVolver = findViewById(R.id.btnPerfil);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        iPerfil = new Intent(this, MostrarPerfil.class);
        iArbol = new Intent(this, CrearMiembro.class);
        iMostrarArbol = new Intent(this, MostrarArbol.class);

        userEmail = user.getEmail();
        mostrarFoto(userEmail);

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
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
            case R.id.btnPerfil:
                startActivity(iPerfil);
                break;

            case R.id.btnArbol1:
                if (!isArbol1) {
                    tipoArbol = "arbol1";
                    mostrarAlert();
                } else {
                    iMostrarArbol.putExtra("tipo_arbol", "arbol1");
                    iMostrarArbol.putExtra("nombre_arbol", nombreArbol);
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol2:
                if (!isArbol2) {
                    tipoArbol = "arbol2";
                    mostrarAlert();
                } else {
                    iMostrarArbol.putExtra("tipo_arbol", "arbol2");
                    iMostrarArbol.putExtra("nombre_arbol", nombreArbol);
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol3:
                if (!isArbol3) {
                    tipoArbol = "arbol3";
                    mostrarAlert();
                } else {
                    iMostrarArbol.putExtra("tipo_arbol", "arbol3");
                    iMostrarArbol.putExtra("nombre_arbol", nombreArbol);
                    startActivity(iMostrarArbol);
                }

        }
    }

    // ----------------------------- COMPROVACIONES Y UTILIDADES -------------------------
    private void comprobarArboles() {
        db.collection("users").document(userEmail).collection("tree").document("arbol1")
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol1 existe");
                    isArbol1 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol1.setBackgroundResource(R.drawable.style_btn_home);
                    btnCrearArbol1.setTextColor(Color.rgb(255, 255, 255));
                    btnCrearArbol1.setTextSize(24F);
                    setNombreArbol(userEmail, "arbol1");

                } else {
                    btnCrearArbol1.setBackgroundResource(R.drawable.style_btn_home_1);
                    btnCrearArbol1.setTextColor(Color.rgb(0, 0, 0));
                    btnCrearArbol1.setTextSize(24F);
                    Log.d(TAG, "arbol1 no existe");
                    isArbol1 = false;
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        db.collection("users").document(userEmail).collection("tree").document("arbol2").collection("bisabuelos").document("0.1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol2 existe");
                    isArbol2 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol2.setBackgroundResource(R.drawable.style_btn_home);
                    btnCrearArbol2.setTextColor(Color.rgb(255, 255, 255));
                    btnCrearArbol2.setTextSize(24F);
                    setNombreArbol(userEmail, "arbol2");

                } else {
                    btnCrearArbol2.setBackgroundResource(R.drawable.style_btn_home_1);
                    btnCrearArbol2.setTextColor(Color.rgb(0, 0, 0));
                    btnCrearArbol2.setTextSize(24F);
                    Log.d(TAG, "arbol2 no existe");
                    isArbol2 = false;
                }
            } else {
                Log.d(TAG, "get failed with ", task.getException());
            }
        });

        db.collection("users").document(userEmail).collection("tree").document("arbol3").collection("bisabuelos").document("0.1").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    Log.d(TAG, "arbol3 existe");
                    isArbol3 = true;
                    // ---- CAMBIAR NOMBRE BOTONES ---
                    btnCrearArbol3.setBackgroundResource(R.drawable.style_btn_home);
                    btnCrearArbol3.setTextColor(Color.rgb(255, 255, 255));
                    btnCrearArbol3.setTextSize(24F);
                    setNombreArbol(userEmail, "arbol3");

                } else {
                    btnCrearArbol3.setBackgroundResource(R.drawable.style_btn_home_1);
                    btnCrearArbol3.setTextColor(Color.rgb(0, 0, 0));
                    btnCrearArbol3.setTextSize(24F);
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
                Toast.makeText(getApplicationContext(), "El nombre del arbol es muy peque??o", Toast.LENGTH_SHORT).show();
            }

        });

        alert.setNeutralButton("Cancelar", (dialogInterface, i) -> {

        });

        alert.show();

    }

    private void setNombreArbol(String userEmail, String tipoArbol) {
        DocumentReference docRef = db.collection("users").document(userEmail).collection("tree").document(tipoArbol);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nombreArbol = document.getString("nombre_arbol");
                        switch (tipoArbol) {
                            case "arbol1":
                                btnCrearArbol1.setTextSize(12);
                                btnCrearArbol1.setText("Mostrar: " + nombreArbol);
                                break;

                            case "arbol2":
                                btnCrearArbol2.setTextSize(12);
                                btnCrearArbol2.setText("Mostrar: " + nombreArbol);
                                break;

                            case "arbol3":
                                btnCrearArbol3.setTextSize(12);
                                btnCrearArbol3.setText("Mostrar: " + nombreArbol);
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

    private void mostrarFoto(String userEmail) {
        DocumentReference docRef = db.collection("users").document(userEmail);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String photo = document.getString("url_foto");
                        btnVolver.setImageBitmap(Img.getImgBitmap(photo));
                        btnVolver.setBorderColor(Color.rgb(147, 178, 112));
                        btnVolver.setBorderWidth(5.5F);
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




















