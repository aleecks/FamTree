package com.imf.famtree;

import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validaciones {

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
            if (c1.length() < 6 || c2.length() < 6 ) {
                comprobador = false;

                // comprobamos si coinciden
            } else if (!c1.equals(c2)) {
                comprobador = false;
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
            comprobador = false;

        } catch (Exception e) {
            e.printStackTrace();
            comprobador = false;

        } finally {
            return comprobador;
        }
    }

}
