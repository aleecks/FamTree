package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.inicio.Inicio;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private String nombreArbol;

    private Boolean isArbol1, isArbol2, isArbol3;

    private TextView lblNombre, lblCorreo, lblId;
    private Button btnLogOut, btnCrearArbol1, btnCrearArbol2, btnCrearArbol3;

    private Intent iInicio, iArbol, iMostrarArbol;
    private FirebaseUser user;

    private ManejadorBD bd;

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

        // ---- COMPROBAR SI USUARIO TIENE ALGUN ARBOL ---
        if (!bd.existeArbol(user.getEmail(), "arbol1")) {
            isArbol1 = false;
        } else {
            isArbol1 = true;
            // ---- CAMBIAR NOMBRE BOTONES ---
            btnCrearArbol1.setTextColor(Color.rgb(200, 100, 220));
        }

        if (!bd.existeArbol(user.getEmail(), "arbol2")) {
            isArbol2 = false;
        } else {
            isArbol2 = true;
            // ---- CAMBIAR NOMBRE BOTONES ---
            btnCrearArbol2.setTextColor(Color.rgb(200, 100, 220));
        }

        if (!bd.existeArbol(user.getEmail(), "arbol3")) {
            isArbol3 = false;
        } else {
            isArbol3 = true;
            // ---- CAMBIAR NOMBRE BOTONES ---
            btnCrearArbol3.setTextColor(Color.rgb(200, 100, 220));
        }

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
                    nombreArbol = "arbol1";
                    iArbol.putExtra("nombreArbol", nombreArbol);
                    startActivity(iArbol);
                } else {
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol2:
                if (!isArbol2) {
                    nombreArbol = "arbol2";
                    iArbol.putExtra("nombreArbol", nombreArbol);
                    startActivity(iArbol);
                } else {
                    startActivity(iMostrarArbol);
                }
                break;

            case R.id.btnArbol3:
                if (!isArbol3) {
                    nombreArbol = "arbol3";
                    iArbol.putExtra("nombreArbol", nombreArbol);
                    startActivity(iArbol);
                } else {
                    startActivity(iMostrarArbol);
                }
        }
    }
}