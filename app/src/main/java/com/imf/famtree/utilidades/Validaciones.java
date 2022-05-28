package com.imf.famtree.utilidades;

import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

    // ---------------- FUNCIONALIDADES ----------------
    public static String devolverTipoMiembro1(int contador) {
        String devolver = "";
        String concatenar;

        try {
            concatenar = Integer.toString(contador);
            devolver = concatenar.concat(".1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return devolver;
        }
    }

    public static String devolverTipoMiembro2(int contador) {
        String devolver = "";
        String concatenar;

        try {
            concatenar = Integer.toString(contador);
            devolver = concatenar.concat(".2");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return devolver;
        }
    }

    // ---------------- VALIDACIONES ----------------
    public static boolean validarNombre(String nombre) {
        boolean comprobador = true;

        try {
            if (nombre.isEmpty() || nombre.length() < 3 || nombre.length() > 20) {
                comprobador = false;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            return comprobador;
        }
    }


    public static boolean validarEditText(ArrayList<EditText> array) {
        boolean comprobador = true;
        int i = 0;

        try {
            while (comprobador && (i < array.size())) {
                if (array.get(i).getText().toString().length() < 1) {
                    comprobador = false;
                }
                i++;
            }

        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            return comprobador;
        }
    }

    public static boolean validarCorreo(String email) {
        boolean comprobador = true;

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher mather = pattern.matcher(email);

            comprobador = mather.find();

        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        } finally {
            return comprobador;
        }
    }

    public static boolean validarPass(TextView contrasena1, TextView contrasena2) {
        boolean comprobador = true;
        String c1, c2;

        try {
            c1 = contrasena1.getText().toString();
            c2 = contrasena2.getText().toString();

            // comprobamos si estan vacios
            if (c1.length() < 6 || c2.length() < 6) {
                comprobador = false;

                // comprobamos si coinciden
            } else if (!c1.equals(c2)) {
                comprobador = false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            comprobador = false;

        } finally {
            return comprobador;
        }
    }

}
