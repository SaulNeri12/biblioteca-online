/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaAutorDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Servlet que manejara la visualizacion, busqueda, registro y actualizacion
 * de los autores registrados en el sistema.
 * @author neri
 */
@WebServlet(name = "SvAutor", urlPatterns = {"/autor"})
public class SvAutor extends HttpServlet {

    IAutorDAO autorDAO = FabricaAutorDAO.getInstance().crearDAO();
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            List<Autor> autores = autorDAO.obtenerAutoresTodos();

            // Crear una lista de Mapas con solo el id y nombre de los autores
            List<Map<String, Object>> autoresList = new ArrayList<>();
            for (Autor autor : autores) {
                Map<String, Object> autorMap = new HashMap<>();
                autorMap.put("id", autor.getId());
                autorMap.put("nombre", autor.getNombre());
                autoresList.add(autorMap);
            }

            // Convertimos la lista a JSON usando Jackson
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(autoresList);

            // Enviar la respuesta JSON al cliente
            response.getWriter().write(json);

        } catch (Exception e) {
            // Manejar el error y enviar respuesta adecuada
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }



    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
