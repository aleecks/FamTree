package com.imf.famtree;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
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
import com.imf.famtree.utilidades.Img;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MostrarArbol extends AppCompatActivity implements View.OnClickListener {

    private Button btnVolver, btnEliminar;
    private RoundedImageView btn01, btn02, btn11, btn12, btn21, btn22, btn31, btn32, btn41, btn42, btn51, btn52, btn61, btn62, btnUsuario;

    private Intent iVolver, iMostrar;
    private FirebaseUser user;
    private FirebaseFirestore db;

    private Arbol arbol;
    private Map<String, Object> miembroObtenido;
    private String tipoArbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_arbol);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);

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

        tipoArbol = getIntent().getStringExtra("tipo_arbol");
        iVolver = new Intent(this, Home.class);
        iMostrar = new Intent(this, MostrarMiembro.class);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        arbol = new Arbol();
        arbol.setTipoArbol(tipoArbol);
        arbol.setNombreArbol(getIntent().getStringExtra("nombre_arbol"));
        iMostrar.putExtra("tipo_arbol", tipoArbol);
        arbol = obtenerArbol(tipoArbol, user.getEmail());

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnEliminar.setOnClickListener(this);
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
                db.collection("users").document(user.getEmail()).collection("tree").document(tipoArbol)
                        .delete()
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "DocumentSnapshot successfully deleted!"))
                        .addOnFailureListener(e -> Log.e(TAG, "Error deleting document"));
                startActivity(iVolver);
                break;

            case R.id.btn01:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "0.1"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn02:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "0.2"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn11:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "1.1"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn12:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "1.2"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn21:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "2.1"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn22:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "2.2"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn31:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "3.1"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn32:
                iMostrar.putExtra("miembro", devolverMiembro("bisabuelos", "3.2"));
                iMostrar.putExtra("tipoMiembro", "bisabuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn41:
                iMostrar.putExtra("miembro", devolverMiembro("abuelos", "4.1"));
                iMostrar.putExtra("tipoMiembro", "abuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn42:
                iMostrar.putExtra("miembro", devolverMiembro("abuelos", "4.2"));
                iMostrar.putExtra("tipoMiembro", "abuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn51:
                iMostrar.putExtra("miembro", devolverMiembro("abuelos", "5.1"));
                iMostrar.putExtra("tipoMiembro", "abuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn52:
                iMostrar.putExtra("miembro", devolverMiembro("abuelos", "5.2"));
                iMostrar.putExtra("tipoMiembro", "abuelos");
                startActivity(iMostrar);
                break;

            case R.id.btn61:
                iMostrar.putExtra("miembro", devolverMiembro("padres", "6.1"));
                iMostrar.putExtra("tipoMiembro", "padres");
                startActivity(iMostrar);
                break;

            case R.id.btn62:
                iMostrar.putExtra("miembro", devolverMiembro("padres", "6.2"));
                iMostrar.putExtra("tipoMiembro", "padres");
                startActivity(iMostrar);

                break;

            case R.id.btnUsuario:
                iMostrar.putExtra("miembro", arbol.getTu());
                iMostrar.putExtra("tipoMiembro", "usuario");
                startActivity(iMostrar);

        }
    }


    private Miembro devolverMiembro(String tipoMiembro, String numeroMimebro) {
        Miembro miembroDevolver = new Miembro();
        boolean encontrado = false;
        int i = 0;

        try {
            switch (tipoMiembro) {
                case "bisabuelos":
                    while (!encontrado && i < 8) {
                        if (arbol.getBisabuelos().get(i).getTipo().equals(numeroMimebro)) {
                            encontrado = true;
                            miembroDevolver = arbol.getBisabuelos().get(i);
                        } else {
                            i++;
                        }
                    }
                    break;

                case "abuelos":
                    while (!encontrado && i < 4) {
                        if (arbol.getAbuelos().get(i).getTipo().equals(numeroMimebro)) {
                            encontrado = true;
                            miembroDevolver = arbol.getAbuelos().get(i);
                        } else {
                            i++;
                        }
                    }
                    break;

                case "padres":
                    while (!encontrado && i < 2) {
                        if (arbol.getPadres().get(i).getTipo().equals(numeroMimebro)) {
                            encontrado = true;
                            miembroDevolver = arbol.getPadres().get(i);
                        } else {
                            i++;
                        }
                    }
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return miembroDevolver;
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
                        miembro.setNumeroMiembro(numeroMiembro);
                        miembro.setNombre(Objects.requireNonNull(miembroObtenido.get("nombre")).toString());
                        miembro.setApellido1(Objects.requireNonNull(miembroObtenido.get("apellido_1")).toString());
                        miembro.setApellido2(Objects.requireNonNull(miembroObtenido.get("apellido_2")).toString());
                        miembro.setFechaNacimiento(Objects.requireNonNull(miembroObtenido.get("fecha_nacimiento")).toString());
                        miembro.setFechaDefuncion(Objects.requireNonNull(miembroObtenido.get("fecha_defuncion")).toString());
                        miembro.setUrlFoto(Objects.requireNonNull(miembroObtenido.get("url_foto")).toString());

                        switch (tipoMiembro) {
                            case "bisabuelos":
                                arbol.getBisabuelos().add(miembro);
                                // mostramos fotografía
                                switch (miembro.getNumeroMiembro()) {
                                    case "0.1":
                                        btn01.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn01);
                                        break;

                                    case "0.2":
                                        btn02.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn02);
                                        break;

                                    case "1.1":
                                        btn11.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn11);
                                        break;

                                    case "1.2":
                                        btn12.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn12);
                                        break;

                                    case "2.1":
                                        btn21.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn21);
                                        break;

                                    case "2.2":
                                        btn22.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn22);
                                        break;

                                    case "3.1":
                                        btn31.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn31);
                                        break;

                                    case "3.2":
                                        btn32.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn32);
                                }
                                break;

                            case "abuelos":
                                arbol.getAbuelos().add(miembro);
                                // mostramos fotografía
                                switch (miembro.getNumeroMiembro()) {
                                    case "4.1":
                                        btn41.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn41);
                                        break;

                                    case "4.2":
                                        btn42.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn42);
                                        break;

                                    case "5.1":
                                        btn51.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn51);
                                        break;

                                    case "5.2":
                                        btn52.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                        estilizarBoton(btn52);
                                }
                                break;

                            case "padres":
                                arbol.getPadres().add(miembro);
                                if (miembro.getNumeroMiembro().equals("6.1")) {
                                    btn61.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                    estilizarBoton(btn61);
                                } else {
                                    btn62.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                    estilizarBoton(btn62);
                                }
                                break;

                            case "usuario":
                                arbol.setTu(miembro);
                                btnUsuario.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
                                estilizarBoton(btnUsuario);
                                Log.d(TAG, "TERMINDO");
                                Log.d(TAG, arbol.toString());

                        }

                    } else {
                        Log.e(TAG, "No such document");
                    }
                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "tipoMiembro: " + tipoMiembro + ", numeroMiembro: " + numeroMiembro + ", useremail: " + userEmail);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void estilizarBoton(RoundedImageView btn) {
        btn.setBorderColor(Color.rgb(73, 51, 13));
        btn.setBorderWidth(4F);
    }

}