/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecaonline.servlets.errores;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 *
 * @author Saul Neri
 */
/**
 * Clase para representar una respuesta de error en formato JSON.
 */
public class ErrorRespuesta {
    
    @JsonProperty("error")
    private String mensaje;
    
    /**
     * Constructor por defecto.
     */
    public ErrorRespuesta() {
        
    }
    
    /**
     * Constructor con un mensaje de error.
     * @param mensaje El mensaje de error.
     */
    public ErrorRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * Obtiene el mensaje de error.
     * @return El mensaje de error.
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * Establece el mensaje de error.
     * @param mensaje El mensaje de error.
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    /**
     * Devuelve una representación en cadena del objeto ErrorRespuesta en formato JSON.
     * @return Una cadena en formato JSON que representa el objeto ErrorRespuesta.
     */
    @Override
    public String toString() {
        return String.format("{\"error\": \"%s\"}", this.mensaje);
    }
}
