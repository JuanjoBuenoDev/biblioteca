package com.biblioteca.service;

public class Formato {

    public String formatoNacionalidad(String nacionalidad) {
        if (nacionalidad == null || nacionalidad.isBlank()) {
            return nacionalidad;
        }
        // trim() quita espacios al inicio/final y pasamos todo a minúscula antes de capitalizar
        String texto = nacionalidad.trim().toLowerCase();
        return texto.substring(0, 1).toUpperCase() + texto.substring(1);
    }
}
