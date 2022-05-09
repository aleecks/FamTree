package com.imf.famtree;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.imf.famtree.beans.Arbol;

public class MostrarArbol extends AppCompatActivity {

    private Arbol arbol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_arbol);

        arbol = (Arbol) getIntent().getSerializableExtra("arbol");
        Toast.makeText(getApplicationContext(), arbol.getBisabuelos().toString(), Toast.LENGTH_LONG).show();

    }
}