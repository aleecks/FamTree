package com.imf.famtree;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.imf.famtree.beans.Miembro;
import com.imf.famtree.utilidades.Img;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class EditarMiembro extends AppCompatActivity implements View.OnClickListener {

    private Intent iEditar;

    private EditText txtNombre, txtApellido1, txtApellido2;
    private Button btnVolver, btnSubir, btnFecha1, btnFecha2;
    private ImageView btnImg;

    private Miembro miembro, miembroActualizado;
    private ManejadorBD bd;
    private FirebaseUser user;
    private String tipoArbol, tipoMiembro;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano;
    private String fechaN, fechaD;

    // subir fotografia
    private String urlFoto;
    private boolean fotoSubida;

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
        btnImg = findViewById(R.id.btnImg);

        miembro = (Miembro) getIntent().getSerializableExtra("miembro");
        tipoMiembro = getIntent().getStringExtra("tipoMiembro");
        tipoArbol = getIntent().getStringExtra("tipo_arbol");

        iEditar = new Intent(this, MostrarArbol.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        urlFoto = miembro.getUrlFoto();
        fotoSubida = false;
        bd = new ManejadorBD();

        // ---------- RELLENAR TXT --------
        btnImg.setImageBitmap(Img.getImgBitmap(urlFoto));
        txtNombre.setText(miembro.getNombre());
        txtApellido1.setText(miembro.getApellido1());
        txtApellido2.setText(miembro.getApellido2());

        // ----------- CALENDARIO --------
        fechaN = miembro.getFechaNacimiento();
        fechaD = miembro.getFechaDefuncion();
        c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        // ---------- LISTENERS --------
        btnVolver.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
        btnFecha1.setOnClickListener(this);
        btnFecha2.setOnClickListener(this);
        btnImg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnImg:
                if (!fotoSubida) {
                    lay_addImagen();
                }
                break;

            case R.id.btnVolver:
                onBackPressed();
                break;

            case R.id.btnSubir:
                // comprobar si text fields estan vacios
                if (txtNombre.getText().toString().isEmpty() || txtApellido1.getText().toString().isEmpty() || txtApellido2.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    miembroActualizado = new Miembro(miembro.getTipo(), txtNombre.getText().toString(), txtApellido1.getText().toString(), txtApellido2.getText().toString(), fechaN, fechaD, urlFoto);
                    bd.subirMiembro(miembroActualizado, user.getEmail(), tipoArbol, tipoMiembro);
                    iEditar.putExtra("tipo_arbol", tipoArbol);
                    startActivity(iEditar);
                }
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

    // ------- SUBIR FOTOGRAFIA --------
    public void lay_addImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();

                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        btnImg.setImageBitmap(bitmap);
                        urlFoto = Img.getImgString(bitmap);
                        fotoSubida = true;

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

}