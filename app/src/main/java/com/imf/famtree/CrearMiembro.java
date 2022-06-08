package com.imf.famtree;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.imf.famtree.beans.Arbol;
import com.imf.famtree.beans.Miembro;
import com.imf.famtree.utilidades.Img;
import com.imf.famtree.utilidades.Validaciones;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class CrearMiembro extends AppCompatActivity implements View.OnClickListener {

    private Arbol arbol;
    private Miembro miembro1, miembro2;
    private ArrayList<Miembro> bisabuelos, abuelos, padres;

    private String urlFoto1, urlFoto2;

    private DatePickerDialog datePicker;
    private Calendar c;
    private int dia, mes, ano, contador;
    private String fechaN1, fechaN2, fechaD1, fechaD2;

    private ScrollView scrollView;
    private EditText txtNombre1, txtNombre2, txtApellido11, txtApellido12, txtApellido21, txtApellido22;
    private ArrayList<EditText> editTexts;
    private TextView lblTitulo;
    private Button btnFechaN1, btnFechaN2, btnFechaD1, btnFechaD2, btnSiguiente;
    //private ImageView img1, img2;

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
        //img1 = findViewById(R.id.btnImg1);
        //img2 = findViewById(R.id.btnImg2);
        scrollView = findViewById(R.id.scrollView);

        // --------- MOSTRAMOS TITULO --------
        lblTitulo.setText("1/4 Pareja de Bisabuelos");

        //------- INICIAMOS VARIABLES --------
        bisabuelos = new ArrayList<>();
        abuelos = new ArrayList<>();
        padres = new ArrayList<>();
        arbol = new Arbol();
        contador = 0;
        extras = getIntent().getExtras();
        nombreArbol = extras.getString("nombreArbol");
        tipoArbol = extras.getString("tipoArbol");
        urlFoto1 = "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";
        urlFoto2 = "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";

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
        //img1.setOnClickListener(this);
        //img2.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "contador: " + contador, Toast.LENGTH_LONG);
        // ----- SUBIR SCROLLVIEW -------
        scrollView.fullScroll(View.FOCUS_UP);

        // ----- INICILIZAR VARIABLES -------
        fechaN1 = "";
        fechaN2 = "";
        fechaD1 = "vivo";
        fechaD2 = "vivo";
        //img1.setImageDrawable(Drawable.createFromPath("@android:drawable/ic_menu_camera"));
        //img2.setImageDrawable(Drawable.createFromPath("@android:drawable/ic_menu_camera"));

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
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto2);

                        // agregar al array correspondiente
                        bisabuelos.add(miembro1);
                        bisabuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador + 2) + "/4 Pareja de Bisabuelos");
                        if (contador == 3) {
                            lblTitulo.setText((contador - 2) + "/2 Pareja de Abuelos");
                        }

                        // reiniciar vista
                        onRestart();

                    } else if (contador < 6) {
                        // ------ ABUELOS -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto2);

                        // agregar al array correspondiente
                        abuelos.add(miembro1);
                        abuelos.add(miembro2);

                        // cambiar titulo
                        lblTitulo.setText((contador - 2) + "/2 Pareja de Abuelos");
                        if (contador == 5) {
                            lblTitulo.setText("Padres");
                        }

                        // reiniciar vista
                        onRestart();

                    } else {
                        // ------ PADRES -----
                        // crear objetos
                        miembro1 = new Miembro(Validaciones.devolverTipoMiembro1(contador), txtNombre1.getText().toString(), txtApellido11.getText().toString(), txtApellido12.getText().toString(), fechaN1, fechaD1, urlFoto1);
                        miembro2 = new Miembro(Validaciones.devolverTipoMiembro2(contador), txtNombre2.getText().toString(), txtApellido21.getText().toString(), txtApellido22.getText().toString(), fechaN2, fechaD2, urlFoto2);

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
                /*break;

            case R.id.btnImg1:
                lay_addImagen1();
                break;

            case R.id.btnImg2:
                lay_addImagen2();*/

        }
    }

    /* ------- SUBIR FOTOGRAFIA --------
    public void lay_addImagen1(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage1.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage1 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();

                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img1.setImageBitmap(bitmap);
                        urlFoto1 = Img.getImgString(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    public void lay_addImagen2(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.addFlags(Intent.FLAG_GRANT_PREFIX_URI_PERMISSION);
        pickImage2.launch(intent);
    }

    //Metodo que nos permite coger una imagen de la galería
    private final ActivityResultLauncher<Intent> pickImage2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == RESULT_OK){
                    Uri imageUri = result.getData().getData();

                    try{
                        InputStream inputStream = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        img2.setImageBitmap(bitmap);
                        urlFoto2 = Img.getImgString(bitmap);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
    );*/

}