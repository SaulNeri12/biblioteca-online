/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.conexion.interfaces.IConexion;
import javax.persistence.EntityManager;

/**
 *
 * @author nerix
 */
public class BibliotecaOnlineNegocio {

    public static void main(String[] args) {
        IConexion con = Conexion.getInstance();
        EntityManager em = Conexion.getInstance().crearConexion();

        em.getTransaction().begin();
        // Forzamos algo que obligue a inicializar el esquema
        em.createQuery("SELECT l FROM Libro l").getResultList();
        em.getTransaction().commit();


    }
}
