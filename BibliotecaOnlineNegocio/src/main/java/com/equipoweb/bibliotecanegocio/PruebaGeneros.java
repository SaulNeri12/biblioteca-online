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
public class PruebaGeneros {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IGeneroDAO generoDAO = FabricaGeneroDAO.getInstance().crearDAO();
        
        try{
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
