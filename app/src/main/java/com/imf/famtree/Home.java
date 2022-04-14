package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.inicio.Inicio;

public class Home extends AppCompatActivity {

    private TextView lblNombre, lblCorreo, lblId;
    private Button btnLogOut, btnCrearArbol;

    private Intent iInicio, iArbol;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_home);

        btnLogOut = findViewById(R.id.btnLogOut);
        btnCrearArbol = findViewById(R.id.btnArbol1);
        lblNombre = findViewById(R.id.lblNombre);
        lblCorreo = findViewById(R.id.lblCorreo);
        lblId = findViewById(R.id.lblUID);

        user = FirebaseAuth.getInstance().getCurrentUser();

        iInicio = new Intent(this, Inicio.class);
        iArbol = new Intent(this, CrearMiembro.class);

        // -------------- LISTENERS --------
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(iInicio);
            }
        });

        btnCrearArbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(iArbol);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        lblNombre.setText(user.getDisplayName());
        lblCorreo.setText(user.getEmail());
        lblId.setText(user.getUid());

    }
}