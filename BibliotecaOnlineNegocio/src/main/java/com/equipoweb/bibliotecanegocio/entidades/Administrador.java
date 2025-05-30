/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidad para el administrador
 * 
 * @author skevi
 */
@Entity
@Table(name="administrador")
public class Administrador implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    
    
    @Column(name = "correo", nullable = false)
    @JsonProperty("correo")
    private String correo;
    
    @Column(name = "contrasena", nullable = false)
    @JsonProperty("contrasena")
    private String contrasena;

    public Administrador() {
        
    }

    public Administrador(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Administrador{" + "id=" + id + ", correo=" + correo + 
                ", contrasena=" + contrasena + '}';
    }
    
}
