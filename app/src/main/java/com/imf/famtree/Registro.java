package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity {

    private EditText txtEmail, txtPassword;
    private Button btnRegistro, btnAcceder;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_registro);

        txtEmail = findViewById(R.id.editTxtEmail);
        txtPassword = findViewById(R.id.editTxtContraseña);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnAcceder = findViewById(R.id.btnAcceder);



    }
}