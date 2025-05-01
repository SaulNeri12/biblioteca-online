/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

// Asegúrate que las importaciones a tus clases DAO y Entidades sean correctas
import com.equipoweb.bibliotecanegocio.dao.FabricaFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Libro; // Necesario para la lista

import com.fasterxml.jackson.databind.ObjectMapper; // Para convertir lista a JSON
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Collections; // Para lista vacía

/**
 * Servlet para obtener la lista de libros favoritos de un usuario.
 */
@WebServlet(name = "SvFavoritos", urlPatterns = {"/Favoritos"})
public class SvFavoritos extends HttpServlet {

    // Usar la fábrica y la interfaz DAO correctas
    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();
    private final ObjectMapper mapper = new ObjectMapper(); // Instancia para convertir a JSON

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // --- Identificación del Usuario ---
        // TODO: Implementa la lógica real para obtener el ID del usuario autenticado.
        HttpSession session = request.getSession(false);
        Long usuarioId = null;
         if (session != null && session.getAttribute("usuarioId") != null) {
             try {
                usuarioId = (Long) session.getAttribute("usuarioId");
            } catch (ClassCastException e) {
                 System.err.println("Error al obtener usuarioId de la sesión: El atributo no es de tipo Long.");
                 // Devolver lista vacía o error, aquí devolvemos error.
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno al procesar la sesión.\"}");
                 return;
            }
        }

        if (usuarioId == null) {
            // Si no hay usuario, devolver lista vacía es más amigable para el frontend que un error 401.
            response.getWriter().write(mapper.writeValueAsString(Collections.emptyList()));
            return;
        }
        // ---------------------------------

        try {
            // Llamar al método DAO que devuelve List<Libro>
            List<Libro> librosFavoritos = favoritoDAO.obtenerLibrosFavoritosPorUsuario(usuarioId);

            // Convertir la lista de libros a JSON y escribirla en la respuesta
            String jsonResponse = mapper.writeValueAsString(librosFavoritos);
            response.getWriter().write(jsonResponse);

        } catch (DAOException ex) {
            System.err.println("Error DAO al obtener favoritos (Usuario: " + usuarioId + "): " + ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al obtener la lista de favoritos: " + ex.getMessage() + "\"}");
        } catch (Exception e) {
            System.err.println("Error inesperado al obtener favoritos (Usuario: " + usuarioId + "): " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Ocurrió un error inesperado.\"}");
        }
    }
}