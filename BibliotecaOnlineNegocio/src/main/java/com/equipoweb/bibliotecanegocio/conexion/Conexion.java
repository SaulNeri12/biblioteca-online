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
public class Conexion implements IConexion {

    private static Conexion _instancia;
    private EntityManagerFactory emf;
    
    private Conexion() {
        this.emf = Persistence.createEntityManagerFactory("BibliotecaNegocio_PU");
    }
    
    public static Conexion getInstance() {
        if (_instancia == null) {
            _instancia = new Conexion();
        }
        
        return _instancia;
    }
    
    @Override
    public EntityManager crearConexion() {
        return emf.createEntityManager();
    }
    
}
