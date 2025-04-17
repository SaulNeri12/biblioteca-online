/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.conexion.interfaces.IConexion;
import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author nerix
 */
public class BibliotecaOnlineNegocio {

    public static void main(String[] args) {
        
        /*
        IConexion con = Conexion.getInstance();
        EntityManager em = Conexion.getInstance().crearConexion();

        em.getTransaction().begin();
        // Forzamos algo que obligue a inicializar el esquema
        em.createQuery("SELECT l FROM Libro l").getResultList();
        em.getTransaction().commit();
*/
        
        FabricaLibroDAO fabrica = FabricaLibroDAO.getInstance();
        ILibroDAO libroDAO = fabrica.crearDAO();
        
        try {
            System.out.println(libroDAO.obtenerLibrosTodos());
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }
}
