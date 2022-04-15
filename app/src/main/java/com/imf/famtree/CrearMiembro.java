package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CrearMiembro extends AppCompatActivity implements View.OnClickListener {

    private FirebaseUser user;
    private Arbol arbol;
    private Miembro miembro1, miembro2;
    private ArrayList<Miembro> bisabuelos, abuelos, padres, hijos;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano, contador;
    private String fechaN1, fechaN2, fechaD1, fechaD2;

    private EditText txtNombre1, txtNombre2, txtApellido11, txtApellido12, txtApellido21, txtApellido22;
    private ArrayList<EditText> editTexts;
    private TextView lblTitulo, lblMiembro1, lblMiembro2;
    private Button btnFechaN1, btnFechaN2, btnFechaD1, btnFechaD2, btnSiguiente, btnImg1, btnImg2;

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
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnImg1 = findViewById(R.id.btnImg1);
        btnImg2 = findViewById(R.id.btnImg2);

        user = FirebaseAuth.getInstance().getCurrentUser();
        bisabuelos = new ArrayList<>();
        abuelos = new ArrayList<>();
        padres = new ArrayList<>();
        arbol = new Arbol();
        contador = 0;

        // mostramos titulo
        lblTitulo.setText("1º Pareja de Bisabuelos");

        // ----------- CALENDARIO --------
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // ----------- LISTENERS --------
        btnSiguiente.setOnClickListener(this);
        btnFechaN1.setOnClickListener(this);
        btnFechaN2.setOnClickListener(this);
        btnFechaD1.setOnClickListener(this);
        btnFechaD2.setOnClickListener(this);
        btnImg1.setOnClickListener(this);
        btnImg2.setOnClickListener(this);

        // ----- RELLENAR ARRAYLIST -------
        editTexts = new ArrayList<>();
        editTexts.add(txtNombre1);
        editTexts.add(txtNombre2);
        editTexts.add(txtApellido11);
        editTexts.add(txtApellido12);
        editTexts.add(txtApellido21);
        editTexts.add(txtApellido22);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // ----- INICILIZAR VARIABLES -------
        fechaN1 = "";
        fechaN2 = "";
        fechaD1 = "vivo";
        fechaD2 = "vivo";

        // ------ RELLENAR EDITTEXT ----------
        txtNombre1.getText().clear();
        txtNombre2.getText().clear();
        txtApellido11.getText().clear();
        txtApellido12.getText().clear();
        txtApellido21.getText().clear();
        txtApellido22.getText().clear();

        txtNombre1.setHint("Nombre");
        txtNombre2.setHint("Nombre");
        txtApellido11.setHint("Primer Apellido");
        txtApellido21.setHint("Primer Apellido");
        txtApellido12.setHint("Segundo Apellido");
        txtApellido22.setHint("Segundo Apellido");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        // sumamos contador
        contador++;

        // reiniciamos vista
        onStart();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSiguiente:
                // comprobar si text fields estan vacios
                if (!Validaciones.validarEditText(editTexts)) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    // comprobar si las fechas estan vacias
                } else if (fechaN1.isEmpty() || fechaN2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar las fechas de nacimineto", Toast.LENGTH_SHORT).show();

                } else {
                    // elegir tipo de miembro
                    if (contador < 3) {
                        // ------ BISABUELOS -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2);

                        // agregar al array correspondiente
                        bisabuelos.add(miembro1);
                        bisabuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador + 2) + "º Pareja de Bisabuelos");

                        // reiniciar vista
                        onRestart();

                        // mostrar info
                        Toast.makeText(getApplicationContext(), miembro1.toString(), Toast.LENGTH_LONG).show();

                    } else if (contador < 5) {
                        // ------ ABUELOS -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2);

                        // agregar al array correspondiente
                        abuelos.add(miembro1);
                        abuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador - 2) + "º Pareja de Abuelos");

                        // reiniciar variables
                        onRestart();

                    } else if (contador < 6) {
                        // ------ PADRES -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2);

                        // agregar al array correspondiente
                        padres.add(miembro1);
                        padres.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText("Padres");

                        // reiniciar variables
                        onRestart();

                    } else {
                        // reiniciar variables
                        onRestart();

                        // rellenamos arbol
                        arbol = new Arbol(user.getUid(), bisabuelos, abuelos, padres);

                    }

                }
                break;

            case R.id.btnImg1:
                break;

            case R.id.btnImg2:
                break;

            case R.id.btnNacimiento1:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaN1 = day + "/" + (month + 1) + "/" + year;
                    }
                }, dia, mes, ano);

                datePicker.show();
                break;

            case R.id.btnNacimiento2:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaN2 = day + "/" + (month + 1) + "/" + year;
                    }
                }, dia, mes, ano);

                datePicker.show();
                break;

            case R.id.btnDefuncion1:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaD1 = day + "/" + (month + 1) + "/" + year;
                    }
                }, dia, mes, ano);

                datePicker.show();
                break;

            case R.id.btnDefuncion2:
                datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        fechaD2 = day + "/" + (month + 1) + "/" + year;
                    }
                }, dia, mes, ano);

                datePicker.show();

        }
    }

}