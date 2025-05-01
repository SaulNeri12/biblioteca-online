/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

// Asegúrate que las importaciones a tus clases DAO sean correctas
import com.equipoweb.bibliotecanegocio.dao.FabricaFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet para agregar un libro a la lista de favoritos de un usuario.
 * Utiliza el ISBN del libro.
 */
@WebServlet(name = "SvAgregarFavoritos", urlPatterns = {"/AgregarFavorito"})
public class SvAgregarFavoritos extends HttpServlet {

    // Usar la fábrica y la interfaz DAO correctas
    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // --- Identificación del Usuario ---
        // TODO: Implementa la lógica real para obtener el ID del usuario autenticado.
        HttpSession session = request.getSession(false);
        Long usuarioId = null;
        if (session != null && session.getAttribute("usuarioId") != null) {
            // Asume que guardas el ID del usuario como Long en la sesión con la key "usuarioId"
            try {
                usuarioId = (Long) session.getAttribute("usuarioId");
            } catch (ClassCastException e) {
                 System.err.println("Error al obtener usuarioId de la sesión: El atributo no es de tipo Long.");
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar la sesión.");
                 return;
            }
        }

        if (usuarioId == null) {
            // Si no hay usuario logueado, enviar error de no autorizado
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\"error\": \"Usuario no autenticado.\"}");
            return;
        }
        // ---------------------------------

        String isbn = request.getParameter("isbn");

        if (isbn == null || isbn.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "{\"error\": \"El ISBN del libro es requerido.\"}");
            return;
        }

        try {
            // Llamar al método DAO que ahora acepta ISBN
            favoritoDAO.asignarFavorito(usuarioId, isbn);

            response.setStatus(HttpServletResponse.SC_OK); // O SC_CREATED (201) si prefieres
            response.getWriter().write("{\"mensaje\": \"Libro agregado a favoritos con éxito.\"}");

        } catch (DAOException ex) {
            System.err.println("Error DAO al agregar favorito (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + ex.getMessage());
             ex.printStackTrace(); // Loguear para depuración
             // Devolver el mensaje de la excepción DAO puede ser útil para el frontend
             // o devolver un mensaje genérico.
             // Si la DAOException fue porque ya existe, podrías enviar un código diferente (ej: 409 Conflict)
             if (ex.getMessage() != null && ex.getMessage().contains("ya está marcado como favorito")) {
                 response.setStatus(HttpServletResponse.SC_CONFLICT); // 409 Conflict es apropiado si ya existe
                 response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
             } else if (ex.getMessage() != null && (ex.getMessage().contains("no encontrado") || ex.getMessage().contains("not found"))) {
                 response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found si el libro o usuario no existe
                 response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
             }
              else {
                  response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                  response.getWriter().write("{\"error\": \"Error al agregar el libro a favoritos: " + ex.getMessage() + "\"}");
             }
        } catch (Exception e) {
            // Captura genérica para otros posibles errores inesperados
            System.err.println("Error inesperado al agregar favorito (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Ocurrió un error inesperado.\"}");
        }
    }
}