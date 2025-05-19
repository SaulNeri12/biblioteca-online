/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.conexion;

import com.equipoweb.bibliotecanegocio.conexion.interfaces.IConexion;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Saul Neri
 */
/**
 * Clase para manejar la conexión a la base de datos.
 */
public class Conexion implements IConexion {

    private static Conexion _instancia;
    private EntityManagerFactory emf;
    
    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private Conexion() {
        this.emf = Persistence.createEntityManagerFactory("BibliotecaNegocio_PU");
    }
    
    /**
     * Obtiene la instancia de la conexión (Singleton).
     * @return La instancia de la conexión.
     */
    public static Conexion getInstance() {
        if (_instancia == null) {
            _instancia = new Conexion();
        }
        
        return _instancia;
    }
    
    /**
     * Crea una nueva conexión con la base de datos.
     * @return El EntityManager para la conexión.
     */
    @Override
    public EntityManager crearConexion() {
        return emf.createEntityManager();
    }
    
}
