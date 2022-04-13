package com.imf.famtree;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail, txtPass1, txtPass2;
    private Button btnRegistro, btnVolver;

    private Intent iEntrar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_registro);

        txtEmail = findViewById(R.id.editTxtEmail);
        txtPass1 = findViewById(R.id.editTxtPass1);
        txtPass2 = findViewById(R.id.editTxtPass2);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnVolver = findViewById(R.id.btnVolver);

        mAuth = FirebaseAuth.getInstance();

        // ---------------- INTENTS ----------------
        iEntrar = new Intent(this, Home.class);

        // ---------------- LISTENERS ----------------
        btnRegistro.setOnClickListener(this);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onClick(View view) {
        try {
            if (view.getId() == R.id.btnRegistro) {
                try {
                    // Validamos los EditText
                    if (Validaciones.validarCorreo(txtEmail.getText().toString())){
                        if (Validaciones.validarPass(txtPass1, txtPass2)) {
                            // creamos usuario
                            mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPass1.getText().toString())
                                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                // Sign in success, update UI with the signed-in user's information
                                                Log.d(TAG, "createUserWithEmail:success");
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUI(user);
                                            } else {
                                                // If sign in fails, display a message to the user.
                                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                                Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                                                updateUI(null);
                                            }
                                        }
                                    });

                        } else {
                            Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden o estan vacias", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "El email es incorrecto", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido algún error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this,"Error al entrar al formulario", Toast.LENGTH_SHORT).show();

        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            iEntrar.putExtra("email", txtEmail.getText().toString());
            startActivity(iEntrar);
        } else {
            // No user is signed in
        }
    }
}