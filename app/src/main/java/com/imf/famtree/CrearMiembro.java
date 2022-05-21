package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.ArrayList;
import java.util.Calendar;

public class CrearMiembro extends AppCompatActivity implements View.OnClickListener {

    private Arbol arbol;
    private Miembro miembro1, miembro2;
    private ArrayList<Miembro> bisabuelos, abuelos, padres;

    private String urlFoto;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano, contador;
    private String fechaN1, fechaN2, fechaD1, fechaD2;

    private ScrollView scrollView;
    private EditText txtNombre1, txtNombre2, txtApellido11, txtApellido12, txtApellido21, txtApellido22;
    private ArrayList<EditText> editTexts;
    private TextView lblTitulo;
    private Button btnFechaN1, btnFechaN2, btnFechaD1, btnFechaD2, btnSiguiente;

    private Intent iSeguir;
    private Bundle extras;
    private String nombreArbol, tipoArbol;

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
        btnFechaN1 = findViewById(R.id.btnNacimiento1);
        btnFechaN2 = findViewById(R.id.btnNacimiento2);
        btnFechaD1 = findViewById(R.id.btnDefuncion1);
        btnFechaD2 = findViewById(R.id.btnDefuncion2);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        scrollView = findViewById(R.id.scrollView);

        // --------- MOSTRAMOS TITULO --------
        lblTitulo.setText("1º/4 Pareja de Bisabuelos");

        //------- INICIAMOS VARIABLES --------
        bisabuelos = new ArrayList<>();
        abuelos = new ArrayList<>();
        padres = new ArrayList<>();
        arbol = new Arbol();
        contador = 0;
        extras = getIntent().getExtras();
        nombreArbol = extras.getString("nombreArbol");
        tipoArbol = extras.getString("tipoArbol");
        urlFoto = "imagenes/fotos_perfil/image:32";

        // ----------- CALENDARIO --------
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // ----- RELLENAR ARRAYLIST -------
        editTexts = new ArrayList<>();
        editTexts.add(txtNombre1);
        editTexts.add(txtNombre2);
        editTexts.add(txtApellido11);
        editTexts.add(txtApellido12);
        editTexts.add(txtApellido21);
        editTexts.add(txtApellido22);

        // ----------- INTENTS --------
        iSeguir = new Intent(this, SubirArbol.class);

        // ----------- LISTENERS --------
        btnSiguiente.setOnClickListener(this);
        btnFechaN1.setOnClickListener(this);
        btnFechaN2.setOnClickListener(this);
        btnFechaD1.setOnClickListener(this);
        btnFechaD2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();

        // ----- SUBIR SCROLLVIEW -------
        scrollView.fullScroll(View.FOCUS_UP);

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
    public void onClick(@NonNull View view) {
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
                    if (contador < 4) {
                        // ------ BISABUELOS -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto);

                        // agregar al array correspondiente
                        bisabuelos.add(miembro1);
                        bisabuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador + 2) + "º/4 Pareja de Bisabuelos");
                        if (contador == 3) {
                            lblTitulo.setText((contador - 2) + "º/2 Pareja de Abuelos");
                        }

                        // reiniciar vista
                        onRestart();

                    } else if (contador < 6) {
                        // ------ ABUELOS -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto);

                        // agregar al array correspondiente
                        abuelos.add(miembro1);
                        abuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador - 2) + "º/2 Pareja de Abuelos");
                        if (contador == 5) {
                            lblTitulo.setText("Padres");
                        }

                        // reiniciar vista
                        onRestart();

                    } else {
                        // ------ PADRES -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto);

                        // agregar al array correspondiente
                        padres.add(miembro1);
                        padres.add(miembro2);

                        // rellenamos arbol
                        arbol = new Arbol(tipoArbol, nombreArbol, bisabuelos, abuelos, padres);
                        iSeguir.putExtra("arbol", arbol);
                        startActivity(iSeguir);

                    }

                }

                break;

            case R.id.btnNacimiento1:
                datePicker = new DatePickerDialog(this, (view1, year, month, day) -> fechaN1 = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnNacimiento2:
                datePicker = new DatePickerDialog(this, (view12, year, month, day) -> fechaN2 = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnDefuncion1:
                datePicker = new DatePickerDialog(this, (view13, year, month, day) -> fechaD1 = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnDefuncion2:
                datePicker = new DatePickerDialog(this, (view14, year, month, day) -> fechaD2 = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();

        }
    }

}