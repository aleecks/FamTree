package com.imf.famtree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;

import java.util.Calendar;

public class SubirArbol extends AppCompatActivity implements View.OnClickListener {

    private Miembro miembro;
    private Arbol arbol;
    private ManejadorBD bd;
    private FirebaseUser user;

    private Intent iSubir;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano;
    private String fecha;

    private EditText txtNombre, txtApellido1, txtApellido2, txtDescripcion;
    private Button btnImg, btnSubir, btnFecha;

    // subir fotografia
    private String urlFoto;
    private boolean fotoSubida;
    private static final int File = 1;
    private Uri fileUri;
    private StorageReference carpetaFoto;
    private StorageReference nombreFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_subir_arbol);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido1 = findViewById(R.id.txtApellido1);
        txtApellido2 = findViewById(R.id.txtApellido2);
        txtDescripcion = findViewById(R.id.txtInfo);
        btnImg = findViewById(R.id.btnImg);
        btnSubir = findViewById(R.id.btnSubir);
        btnFecha = findViewById(R.id.btnFecha);

        // ----- INICILIZAR VARIABLES -------
        arbol = (Arbol) getIntent().getSerializableExtra("arbol");
        fecha = "";
        bd = new ManejadorBD();
        user = FirebaseAuth.getInstance().getCurrentUser();
        iSubir = new Intent(this, Home.class);
        urlFoto = "imagenes/fotos_perfil/image:32";
        fotoSubida = false;

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
    public void onClick(@NonNull View view) {
        switch (view.getId()) {
            case R.id.btnImg:
                if (!fotoSubida) {
                    fileUpload();
                }
                break;

            case R.id.btnFecha:
                datePicker = new DatePickerDialog(this, (view1, year, month, day) -> fecha = day + "/" + (month + 1) + "/" + year, dia, mes, ano);
                datePicker.show();
                break;

            case R.id.btnSubir:
                // comprobar si text fields estan vacios
                if (txtNombre.getText().toString().isEmpty() || txtApellido1.getText().toString().isEmpty() || txtApellido2.getText().toString().isEmpty() || txtDescripcion.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();

                    // comprobar si las fechas estan vacias
                } else if (fecha.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar la fechas de nacimineto", Toast.LENGTH_SHORT).show();

                } else {
                    miembro = new Miembro("usuario", txtNombre.getText().toString(), txtApellido1.getText().toString(), txtApellido2.getText().toString(), fecha, "vivo", urlFoto, txtDescripcion.getText().toString());
                    arbol.setTu(miembro);

                    // subimos arbol
                    bd.crearArbol(user.getEmail(), arbol);
                    startActivity(iSubir);

                    // subimos fotografía
                    if (fotoSubida) {
                        nombreFoto.putFile(fileUri).addOnSuccessListener(taskSnapshot -> {
                            Log.d("Mensaje", "Se subió correctamente");
                            Toast.makeText(getApplicationContext(), "Foto subida correctamente", Toast.LENGTH_SHORT).show();
                        });
                    }
                }

        }
    }

    // ------- SUBIR FOTOGRAFIA --------
    private void fileUpload() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent, File);
        // https://stackoverflow.com/questions/62671106/onactivityresult-method-is-deprecated-what-is-the-alternative
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == File) {
            if (resultCode == RESULT_OK) {
                fileUri = data.getData();
                carpetaFoto = FirebaseStorage.getInstance().getReference().child("imagenes/fotos_perfil");
                nombreFoto = carpetaFoto.child(fileUri.getLastPathSegment());
                urlFoto = "imagenes/fotos_perfil/" + fileUri.getLastPathSegment();
                fotoSubida = true;

            }

        }
    }
}