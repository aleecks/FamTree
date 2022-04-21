package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.Calendar;

public class SubirArbol extends AppCompatActivity implements View.OnClickListener {

    private Miembro miembro;
    private Arbol arbol;
    private ManejadorBD bd;
    private FirebaseUser user;

    private String urlFoto;

    private Intent iSubir;
    private Bundle extras;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano;
    private String fecha;

    private EditText txtNombre, txtApellido1, txtApellido2;
    private Button btnImg, btnSubir, btnFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_arbol);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido1 = findViewById(R.id.txtApellido1);
        txtApellido2 = findViewById(R.id.txtApellido2);
        btnImg = findViewById(R.id.btnImg);
        btnSubir = findViewById(R.id.btnSubir);
        btnFecha = findViewById(R.id.btnFecha);

        // ----- INICILIZAR VARIABLES -------
        arbol = extras.getParcelable("arbol");
        fecha = "";
        bd = new ManejadorBD();
        user = FirebaseAuth.getInstance().getCurrentUser();
        iSubir = new Intent(this, Home.class);
        urlFoto = "imagenes/fotos_perfil/image:32";

        // ----------- CALENDARIO --------
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // ----------- LISTENERS --------
        btnSubir.setOnClickListener(this);
        btnImg.setOnClickListener(this);
        btnFecha.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnImg:
                break;

            case R.id.btnFecha:
                datePicker = new DatePickerDialog(this, (view1, year, month, day) -> fecha = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnSubir:
                // comprobar si text fields estan vacios
                if (txtNombre.getText().toString().isEmpty() || txtApellido1.getText().toString().isEmpty() || txtApellido2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    // comprobar si las fechas estan vacias
                } else if (fecha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar la fechas de nacimineto", Toast.LENGTH_SHORT).show();

                } else {
                    miembro = new Miembro("usuario", txtNombre.getText().toString(), txtApellido1.getText().toString(), txtApellido2.getText().toString(), fecha, "", urlFoto);
                    arbol.setTu(miembro);
                    bd.crearArbol(user.getEmail(), arbol);
                    startActivity(iSubir);
                }

        }
    }
}