/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvBuscarLibros", urlPatterns = {"/BuscarLibros"})
/**
 * Este servlet permite buscar libros por título y género.
 */
public class SvBuscarLibros extends HttpServlet {
    
    ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    /**
     * Maneja las solicitudes HTTP GET para buscar libros por título y género.
     *
     * @param request  Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException      Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        String libro = request.getParameter("libro");
        String genero = request.getParameter("genero");
        
        try {
            List<Libro> libros = libroDAO.buscarLibro(libro, genero, "");

            // Convertimos la lista a JSON usando Jackson
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(libros);

            response.getWriter().write(json);

        } catch (DAOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
