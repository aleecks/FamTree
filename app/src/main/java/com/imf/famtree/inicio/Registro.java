package com.imf.famtree.inicio;

import static android.content.ContentValues.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.imf.famtree.Home;
import com.imf.famtree.ManejadorBD;
import com.imf.famtree.R;
import com.imf.famtree.utilidades.Img;
import com.imf.famtree.utilidades.Validaciones;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre, txtEmail, txtPass1, txtPass2;
    private Button btnRegistro, btnVolver;
    private String urlFoto;
    private ImageView btnImg;

    private Intent iEntrar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private ManejadorBD bd;

    private boolean fotoSubida;


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
        btnImg = findViewById(R.id.btnImg);

        mAuth = FirebaseAuth.getInstance();
        bd = new ManejadorBD();
        urlFoto = "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";
        fotoSubida = false;

        // ---------------- INTENTS ----------------
        iEntrar = new Intent(this, Home.class);

        // ---------------- LISTENERS ----------------
        btnRegistro.setOnClickListener(this);
        btnImg.setOnClickListener(this);
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

        } else if (view.getId() == R.id.btnImg) {
            if (!fotoSubida) {
                lay_addImagen();
            }
        }

    }

    private void updateUI(FirebaseUser user) {
        try {
            if (user != null) {
                // User is signed in
                // añadimo nombre foto uri
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

                // introducimos usuario en la bd
                bd.crearUsuario(user.getUid(), txtNombre.getText().toString(), txtEmail.getText().toString(), urlFoto);

                // entramos en la app
                startActivity(iEntrar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------- SUBIR FOTOGRAFIA --------
    public void lay_addImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();

                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        btnImg.setImageBitmap(bitmap);
                        btnImg.setBackgroundResource(R.drawable.style_circulo_imagen);
                        btnImg.setCropToPadding(false);
                        urlFoto = Img.getImgString(bitmap);
                        fotoSubida = true;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );
}