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
import com.google.firebase.auth.UserProfileChangeRequest;
import com.imf.famtree.Home;
import com.imf.famtree.ManejadorBD;
import com.imf.famtree.R;
import com.imf.famtree.Validaciones;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre, txtEmail, txtPass1, txtPass2;
    private Button btnRegistro, btnVolver;

    private Intent iEntrar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ManejadorBD bd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_registro);

        txtNombre = findViewById(R.id.editTxtNombre);
        txtEmail = findViewById(R.id.editTxtEmail);
        txtPass1 = findViewById(R.id.editTxtPass1);
        txtPass2 = findViewById(R.id.editTxtPass2);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnVolver = findViewById(R.id.btnVolver);

        mAuth = FirebaseAuth.getInstance();
        bd = new ManejadorBD();

        // ---------------- INTENTS ----------------
        iEntrar = new Intent(this, Home.class);

        // ---------------- LISTENERS ----------------
        btnRegistro.setOnClickListener(this);

        btnVolver.setOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnRegistro) {
            try {
                // Validamos los EditText
                if (!Validaciones.validarNombre(txtNombre.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "El nombre introducido es incorrecto", Toast.LENGTH_LONG).show();

                } else if (!Validaciones.validarCorreo(txtEmail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "El email es incorrecto", Toast.LENGTH_SHORT).show();

                } else if (!Validaciones.validarPass(txtPass1, txtPass2)) {
                    Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden o tienen menos de 6 caracteres", Toast.LENGTH_LONG).show();

                } else {
                    // creamos usuario
                    mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), txtPass1.getText().toString())
                            .addOnCompleteListener(this, task -> {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    user = mAuth.getCurrentUser();
                                    updateUI(user);

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getApplicationContext(), "Error autentificación", Toast.LENGTH_SHORT).show();
                                    updateUI(null);
                                }
                            });
                }

            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Ha ocurrido algún error", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }

    }

    private void updateUI(FirebaseUser user) {
        try {
            if (user != null) {
                // User is signed in
                // añadimo nombre
                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(txtNombre.getText().toString())
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Nombre añadido");
                            } else {
                                Log.w(TAG, "addDisplayName:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "El nombre no se pudo añadir", Toast.LENGTH_SHORT).show();
                            }
                        });
                // metemos usuario en la bd
                bd.crearUsuario(user.getUid(), txtNombre.getText().toString(), txtEmail.getText().toString());

                // entramos en la app
                startActivity(iEntrar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}