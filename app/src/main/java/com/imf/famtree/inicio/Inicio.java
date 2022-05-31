package com.imf.famtree.inicio;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.Home;
import com.imf.famtree.R;

public class Inicio extends AppCompatActivity {

    private LinearLayout vista;
    private Button btnLogin, btnRegistro;
    private Intent iLogin, iRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_inicio);

        vista = findViewById(R.id.vistaInicio);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnLogin = findViewById(R.id.btnLogin);

        // ---------------- INTENTS ----------------
        iLogin = new Intent(this, Login.class);
        iRegistro = new Intent(this, Registro.class);

        // ---------------- LISTENERS ----------------
        btnLogin.setOnClickListener(view -> startActivity(iLogin));

        btnRegistro.setOnClickListener(view -> startActivity(iRegistro));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update state accordingly.
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            vista.setVisibility(View.INVISIBLE);
            startActivity(new Intent(this, Home.class));
        }
    }
}