package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MostrarMiembro extends AppCompatActivity implements View.OnClickListener {

    private TextView lblNombre, lblApellido1, lblApellido2, lblFecha1, lblFecha2;

    private Button btnVolver, btnEditar;

    private Intent iVoler, iEditar;

    private Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mostrar_miembro);

        lblNombre = findViewById(R.id.lblNombre);
        lblApellido1 = findViewById(R.id.lblApellido1);
        lblApellido2 = findViewById(R.id.lblApellido2);
        lblFecha1 = findViewById(R.id.lblFechaNacimiento);
        lblFecha2 = findViewById(R.id.lblFechaDefuncion);

        iVoler = new Intent(this, MostrarArbol.class);
        iEditar = new Intent(this, EditarMiembro.class);

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnVolver:
                startActivity(iVoler);
                break;

            case R.id.btnEditar:

        }
    }
}