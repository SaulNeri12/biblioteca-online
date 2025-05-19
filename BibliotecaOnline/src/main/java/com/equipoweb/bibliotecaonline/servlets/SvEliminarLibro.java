/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvEliminarLibro", urlPatterns = {"/EliminarLibro"})
/**
 * Este servlet permite eliminar un libro del sistema.
 */
public class SvEliminarLibro extends HttpServlet {

    private final ILibroDAO libroService = FabricaLibroDAO.getInstance().crearDAO();

    /**
     * Maneja las solicitudes HTTP DELETE para eliminar un libro del sistema.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String isbn = request.getParameter("isbn");

        if (isbn == null || isbn.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ISBN es requerido.");
            return;
        }

        try{
        libroService.eliminarLibro(isbn); //llamamos al metodo de eliminacion del libro
        
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"Libro eliminado con éxito.\"}");
        }
        catch(DAOException ex){
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró un libro con ese ISBN.");
        }
    }

}
