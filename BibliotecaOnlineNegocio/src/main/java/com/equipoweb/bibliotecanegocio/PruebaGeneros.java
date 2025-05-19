/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaGeneroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IGeneroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import java.util.List;

/**
 *
 * @author skevi
 */
/**
 * Clase de prueba para la funcionalidad de géneros.
 */
public class PruebaGeneros {

    /**
     * Método principal para la prueba de géneros.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGeneroDAO generoDAO = FabricaGeneroDAO.getInstance().crearDAO();
        
        try{
            /**
             * Obtiene la lista de todos los géneros.
             */
            List<Genero> generos = generoDAO.obtenerGenerosTodos();
            
            for (Genero genero : generos) {
                System.out.println(genero.toString());
            }
        }
        catch(DAOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
