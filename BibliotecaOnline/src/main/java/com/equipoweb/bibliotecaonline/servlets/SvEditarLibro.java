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
/**
 * Este servlet permite editar la información de un libro existente.
 */
public class SvEditarLibro extends HttpServlet {

    private final ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();
    
    /**
     * Maneja las solicitudes HTTP PUT para actualizar la información de un libro.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
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
