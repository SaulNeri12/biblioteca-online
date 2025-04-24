/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaAutorDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import java.util.List;

/**
 *
 * @author skevi
 */
public class PruebaAutores {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IAutorDAO autorDAO = FabricaAutorDAO.getInstance().crearDAO();
        
        try{
            List<Autor> autores = autorDAO.obtenerAutoresTodos();
            
            for (Autor autor : autores) {
                System.out.println(autor.toString());
            }
        }
        catch(DAOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
