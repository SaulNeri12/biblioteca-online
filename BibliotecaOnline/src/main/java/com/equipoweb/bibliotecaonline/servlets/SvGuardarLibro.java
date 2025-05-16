/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.equipoweb.bibliotecaonline.servlets.errores.ErrorRespuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet encargado de manejar las operaciones CRUD con los libros de la aplicacion.
 * @author neri
 */
@WebServlet(name = "SvLibro", urlPatterns = {"/AgregarLibro"})
public class SvGuardarLibro extends HttpServlet {

    private final ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();
    
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

        request.setCharacterEncoding("UTF-8");

        // Leer el cuerpo de la solicitud como un string
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        // Loguear lo que llega del cliente
        System.out.println("Recibido: " + sb.toString());

        // Deserializar el JSON a un objeto Libro
        String jsonString = sb.toString();
        ObjectMapper mapper = new ObjectMapper();  // Usando Jackson para deserializar

        try {
            Libro libro = mapper.readValue(jsonString, Libro.class);
            System.out.println("Libro deserializado: " + libro);

            // Guardar libro
            libroDAO.registrarLibro(libro);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"Libro guardado con éxito.\"}");

        } catch (DAOException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(error.toString());
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(error.toString());
        } catch (IOException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            response.setContentType("application/json");
            response.getWriter().write(error.toString());
        }
    }
}



