package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditarMiembro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre, txtApellido1, txtApellido2;

    private Button btnVolver, btnSubir, btnFecha1, btnFecha2;

    private Intent iVoler;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_editar_miembro);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido1 = findViewById(R.id.txtApellido1);
        txtApellido2 = findViewById(R.id.txtApellido2);
        btnFecha1 = findViewById(R.id.btnNacimiento);
        btnFecha2 = findViewById(R.id.btnDefuncion);
        btnVolver = findViewById(R.id.btnVolver);
        btnSubir = findViewById(R.id.btnSubir);

        iVoler = new Intent(this, MostrarMiembro.class);

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
        btnFecha1.setOnClickListener(this);
        btnFecha2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                break;

            case R.id.btnSubir:
                break;

            case R.id.btnNacimiento:
                break;

            case R.id.btnDefuncion:

        }
    }
}