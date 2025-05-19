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
/**
 * Interface para la conexión a la base de datos.
 */
public interface IConexion {
    /**
     * Crea una nueva conexión con la base de datos.
     * @return El EntityManager para la conexión.
     */
    public EntityManager crearConexion();
}
