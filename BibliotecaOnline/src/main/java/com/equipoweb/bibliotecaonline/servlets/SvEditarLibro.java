/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvEditarLibro", urlPatterns = {"/EditarLibro"})
public class SvEditarLibro extends HttpServlet {

    private final ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();
    
    /**
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BufferedReader reader = request.getReader();
            ObjectMapper mapper = new ObjectMapper();
            Libro libroActualizado = mapper.readValue(reader, Libro.class);

            // Lógica de edición con el DAO
            libroDAO.actualizarLibro(libroActualizado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            mapper.writeValue(response.getWriter(), libroActualizado);

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al editar el libro: " + e.getMessage());
        }
    }
    

    
}
