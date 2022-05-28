package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;
import com.imf.famtree.utilidades.Img;

public class MostrarMiembro extends AppCompatActivity implements View.OnClickListener {

    private TextView lblNombre, lblApellido1, lblApellido2, lblFecha1, lblFecha2;
    private Button btnVolver, btnEditar;
    private ImageView img;

    private Miembro miembro;

    private Intent iEditar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_mostrar_miembro);

        lblNombre = findViewById(R.id.lblNombre);
        lblApellido1 = findViewById(R.id.lblApellido1);
        lblApellido2 = findViewById(R.id.lblApellido2);
        lblFecha1 = findViewById(R.id.lblFechaNacimiento);
        lblFecha2 = findViewById(R.id.lblFechaDefuncion);
        btnVolver = findViewById(R.id.btnVolver);
        btnEditar = findViewById(R.id.btnEditar);
        img = findViewById(R.id.btnImg);

        miembro = (Miembro) getIntent().getSerializableExtra("miembro");
        iEditar = new Intent(this, EditarMiembro.class);

        // ------- RELLENAR LABELS -------
        img.setImageBitmap(Img.getImgBitmap(miembro.getUrlFoto()));
        lblNombre.setText(miembro.getNombre());
        lblApellido1.setText(miembro.getApellido1());
        lblApellido2.setText(miembro.getApellido2());
        lblFecha1.setText(miembro.getFechaNacimiento());
        lblFecha2.setText(miembro.getFechaDefuncion());

        // -------------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnEditar.setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                onBackPressed();
                break;

            case R.id.btnEditar:
                iEditar.putExtra("miembro", miembro);
                iEditar.putExtra("tipoMiembro", getIntent().getStringExtra("tipoMiembro"));
                iEditar.putExtra("tipo_arbol", getIntent().getStringExtra("tipo_arbol"));
                startActivity(iEditar);

        }
    }
}