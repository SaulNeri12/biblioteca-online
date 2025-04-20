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
public class ErrorRespuesta {
    
    @JsonProperty("error")
    private String mensaje;
    
    public ErrorRespuesta() {
        
    }
    
    public ErrorRespuesta(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    @Override
    public String toString() {
        return String.format("{\"error\": \"%s\"}", this.mensaje);
    }
}
