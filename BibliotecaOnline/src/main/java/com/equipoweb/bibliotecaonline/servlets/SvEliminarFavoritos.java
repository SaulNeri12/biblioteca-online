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
 * Servlet para eliminar un libro de la lista de favoritos de un usuario.
 * Utiliza el ISBN del libro.
 */
@WebServlet(name = "SvEliminarFavoritos", urlPatterns = {"/EliminarFavorito"})
public class SvEliminarFavoritos extends HttpServlet {

    // Usar la fábrica y la interfaz DAO correctas
    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
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
                 response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar la sesión.");
                 return;
            }
        }

        if (usuarioId == null) {
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
            boolean eliminado = favoritoDAO.eliminarFavorito(usuarioId, isbn);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK); // 200 OK es común para DELETE exitoso con contenido
                // O podrías usar SC_NO_CONTENT (204) si no devuelves mensaje:
                // response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                 response.getWriter().write("{\"mensaje\": \"Libro eliminado de favoritos con éxito.\"}");
            } else {
                // Si el DAO devuelve false, significa que el favorito no existía
                response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 Not Found es apropiado
                response.getWriter().write("{\"mensaje\": \"El libro no estaba marcado como favorito o no existe.\"}");
            }

        } catch (DAOException ex) {
            System.err.println("Error DAO al eliminar favorito (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error al eliminar el libro de favoritos: " + ex.getMessage() + "\"}");
        } catch (Exception e) {
            System.err.println("Error inesperado al eliminar favorito (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Ocurrió un error inesperado.\"}");
        }
    }
}