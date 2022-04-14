package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.ArrayList;
import java.util.Calendar;

public class CrearMiembro extends AppCompatActivity implements View.OnClickListener {

    private Arbol arbol;
    private ArrayList<Miembro> bisabuelos, abuelos, padres, hijos;

    DatePickerDialog datePicker;
    int dia, mes, ano;
    String fechaN1, fechaN2, fechaD1, fechaD2;

    private EditText txtNombre1, txtNombre2, txtApellido11, txtApellido12, txtApellido21, txtApellido22;
    private TextView lblTitulo, lblMiembro1, lblMiembro2;
    private Button btnFechaN1, btnFechaN2, btnFechaD1, btnFechaD2, btnAnterior, btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_crear_miembro);

        txtNombre1 = findViewById(R.id.txtNombre1);
        txtNombre2 = findViewById(R.id.txtNombre2);
        txtApellido11 = findViewById(R.id.txtApellido1_1);
        txtApellido12 = findViewById(R.id.txtApellido1_2);
        txtApellido21 = findViewById(R.id.txtApellido2_1);
        txtApellido22 = findViewById(R.id.txtApellido2_2);
        lblTitulo = findViewById(R.id.lblTitulo);
        lblMiembro1 = findViewById(R.id.lblMiembro1);
        lblMiembro2 = findViewById(R.id.lblMiembro2);
        btnFechaN1 = findViewById(R.id.btnNacimiento1);
        btnFechaN2 = findViewById(R.id.btnNacimiento2);
        btnFechaD1 = findViewById(R.id.btnDefuncion1);
        btnFechaD2 = findViewById(R.id.btnDefuncion2);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // -------------- LISTENERS --------
        btnSiguiente.setOnClickListener(this);
        btnAnterior.setOnClickListener(this);
        btnFechaN1.setOnClickListener(this);
        btnFechaN2.setOnClickListener(this);
        btnFechaD1.setOnClickListener(this);
        btnFechaD2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        lblTitulo.setText("1º Pareja de Bisabuelos");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnterior:
                break;

            case R.id.btnSiguiente:
                break;

            case R.id.btnNacimiento1:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaN1 = day + "/" + month + "/" + year;
                    }
                }
                        , dia, mes, ano);

                datePicker.show();

                break;

            case R.id.btnNacimiento2:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaN2 = day + "/" + month + "/" + year;
                    }
                }
                        , dia, mes, ano);

                datePicker.show();

                break;

            case R.id.btnDefuncion1:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaD1 = day + "/" + month + "/" + year;
                    }
                }
                        , dia, mes, ano);

                datePicker.show();

                break;


            case R.id.btnDefuncion2:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaD2 = day + "/" + month + "/" + year;
                    }
                }
                        , dia, mes, ano);

                datePicker.show();

        }
    }
}