package com.imf.famtree.inicio;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.Home;
import com.imf.famtree.R;
import com.imf.famtree.utilidades.Validaciones;

public class Login extends AppCompatActivity implements View.OnClickListener {

    private EditText txtEmail, txtPass;
    private Button btnLogin, btnVolver;

    private Intent iEntrar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_login);

        txtEmail = findViewById(R.id.editTxtEmail);
        txtPass = findViewById(R.id.editTxtContraseña);
        btnLogin = findViewById(R.id.btnLogin);
        btnVolver = findViewById(R.id.btnVolver);

        mAuth = FirebaseAuth.getInstance();

        // ---------------- INTENTS ----------------
        iEntrar = new Intent(this, Home.class);

        // ---------------- LISTENERS ----------------
        btnLogin.setOnClickListener(this);
        btnVolver.setOnClickListener(view -> onBackPressed());

    }

    @Override
    public void onClick(View view) {
        try {
            if (view.getId() == R.id.btnLogin) {
                try {
                    // Validamos el email
                    if (Validaciones.validarCorreo(txtEmail.getText().toString())) {
                        // buscamos usuario
                        mAuth.signInWithEmailAndPassword(txtEmail.getText().toString(), txtPass.getText().toString())
                                .addOnCompleteListener(this, task -> {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        user = mAuth.getCurrentUser();
                                        updateUI(user);

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        Toast.makeText(getApplicationContext(), "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                                        updateUI(null);
                                    }
                                });

                    } else {
                        Toast.makeText(getApplicationContext(), "El email es incorrecto", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Ha ocurrido algún error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            startActivity(iEntrar);
        }
    }
}