package com.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fecha {
    public static String FechaBD(){
        Date fecha = new Date();
        SimpleDateFormat formatFecha = new SimpleDateFormat("dd/MM/YYYY");
        
        return formatFecha.format(fecha);
    }
}
