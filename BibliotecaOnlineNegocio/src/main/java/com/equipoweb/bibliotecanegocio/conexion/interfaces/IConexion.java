/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.conexion.interfaces;

import javax.persistence.EntityManager;

/**
 *
 * @author neri
 */
public interface IConexion {
    public EntityManager crearConexion();
}
