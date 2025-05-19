/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import java.util.List;

/**
 *
 * @author skevi
 */
/**
 * Clase de prueba para la funcionalidad del buscador de libros.
 */
public class PruebasBuscador {

    /**
     * Método principal para la prueba del buscador de libros.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();
        
        // Parámetros de prueba: puedes cambiar para probar diferentes casos
        String nombreLibro = "a"; // Prueba parcial
        String genero = ""; // Nombre exacto del género
        String nombreAutor = ""; // Vacío, no se filtra por autor

        try {
            /**
             * Busca libros por nombre, género y autor.
             */
            List<Libro> resultados = libroDAO.buscarLibro(nombreLibro, genero, nombreAutor);
            
            for (Libro libro : resultados) {
                System.out.println(libro.toString());
            }
            
        }catch(DAOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
