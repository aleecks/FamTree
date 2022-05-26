package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.imf.famtree.inicio.Inicio;

public class MostrarPerfil extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogOut, btnVolver;
    private TextView lblNombre, lblCorreo, lblId;
    private ImageView imgPerfil;

    private Intent iInicio, iHome;

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_perfil);

        lblNombre = findViewById(R.id.lblNombre);
        lblCorreo = findViewById(R.id.lblCorreo);
        lblId = findViewById(R.id.lblUID);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnVolver = findViewById(R.id.btnVolver);
        imgPerfil = findViewById(R.id.imgPerfil);

        iInicio = new Intent(this, Inicio.class);
        iHome = new Intent(this, Home.class);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/famtree-tfg.appspot.com/o/imagenes%2Ffotos_perfil%2Fimage%3A32?alt=media&token=4615c9f2-9cd7-48e5-9c5b-2d3fd2e6d799").override(176, 142).centerCrop().fitCenter().into(imgPerfil);

        lblNombre.setText(user.getDisplayName());
        lblCorreo.setText(user.getEmail());
        lblId.setText(user.getUid());

        // -------------- LISTENERS --------
        btnLogOut.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()){
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(iInicio);
                break;

            case R.id.btnVolver:
                startActivity(iHome);
        }
    }
}