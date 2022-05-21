package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.Calendar;

public class EditarMiembro extends AppCompatActivity implements View.OnClickListener {

    private EditText txtNombre, txtApellido1, txtApellido2;
    private Button btnVolver, btnSubir, btnFecha1, btnFecha2;

    private Miembro miembro;
    private Arbol arbol;

    private Intent iVoler;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano;
    private String fechaN, fechaD;

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

        miembro = (Miembro) getIntent().getSerializableExtra("miembro");
        arbol = (Arbol) getIntent().getSerializableExtra("arbol");
        iVoler = new Intent(this, MostrarMiembro.class);

        // ---------- RELLENAR TXT --------
        txtNombre.setText(miembro.getNombre());
        txtApellido1.setText(miembro.getApellido1());
        txtApellido2.setText(miembro.getApellido2());

        // ----------- CALENDARIO --------
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // ---------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
        btnFecha1.setOnClickListener(this);
        btnFecha2.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnVolver:
                onBackPressed();
                break;

            case R.id.btnSubir:
                break;

            case R.id.btnNacimiento:
                datePicker = new DatePickerDialog(this, (view1, year, month, day) -> fechaN = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnDefuncion:
                datePicker = new DatePickerDialog(this, (view1, year, month, day) -> fechaD = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();


        }
    }
}