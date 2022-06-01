package com.imf.famtree;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import com.imf.famtree.inicio.Inicio;
import com.imf.famtree.utilidades.Img;
import com.makeramen.roundedimageview.RoundedImageView;

public class MostrarPerfil extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogOut, btnVolver;
    private TextView lblNombre, lblCorreo;
    private RoundedImageView imgPerfil;

    private Intent iInicio, iHome;

    private FirebaseUser user;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_perfil);

        lblNombre = findViewById(R.id.lblNombre);
        lblCorreo = findViewById(R.id.lblCorreo);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnVolver = findViewById(R.id.btnVolver);
        imgPerfil = findViewById(R.id.imgPerfil);

        iInicio = new Intent(this, Inicio.class);
        iHome = new Intent(this, Home.class);
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();

        lblNombre.setText(user.getDisplayName());
        lblCorreo.setText(user.getEmail());
        mostrarFoto(user.getEmail());

        // -------------- LISTENERS --------
        btnLogOut.setOnClickListener(this);
        btnVolver.setOnClickListener(this);

    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                startActivity(iInicio);
                break;

            case R.id.btnVolver:
                startActivity(iHome);
        }
    }

    private void mostrarFoto(String userEmail) {
        DocumentReference docRef = db.collection("users").document(userEmail);

        try {
            docRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        String photo = document.getString("url_foto");
                        imgPerfil.setImageBitmap(Img.getImgBitmap(photo));

                    } else {
                        Log.e(TAG, "No such document");
                    }

                } else {
                    Log.e(TAG, "get failed with ", task.getException());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}