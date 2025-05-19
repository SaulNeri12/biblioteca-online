/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;
/**
 *
 * @author caarl
 */
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
import java.util.Enumeration; // Para listar atributos de sesión

/**
 * Servlet para eliminar un libro de la lista de favoritos de un usuario.
 * Utiliza el ISBN del libro. Incluye logging de sesión mejorado.
 */
@WebServlet(name = "SvEliminarFavoritos", urlPatterns = {"/EliminarFavorito"})
/**
 * Este servlet permite a los usuarios eliminar libros de su lista de favoritos.
 */
public class SvEliminarFavoritos extends HttpServlet {

    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();

    /**
     * Maneja las solicitudes HTTP DELETE para eliminar un libro de la lista de favoritos del usuario.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // === Logging de Diagnóstico de Sesión ===
        System.out.println("\n--- [SvEliminarFavoritos] - Iniciando doDelete ---");
        HttpSession session = request.getSession(false);
        Long usuarioId = null;

        if (session == null) {
            System.out.println("[SvEliminarFavoritos] Diagnóstico: HttpSession es NULL.");
        } else {
            System.out.println("[SvEliminarFavoritos] Diagnóstico: Session ID = " + session.getId());
             System.out.print("[SvEliminarFavoritos] Diagnóstico: Atributos en sesión -> { ");
            Enumeration<String> attributeNames = session.getAttributeNames();
            boolean first = true;
            while (attributeNames.hasMoreElements()) {
                String attrName = attributeNames.nextElement();
                Object attrValue = session.getAttribute(attrName);
                 if (!first) System.out.print(", ");
                 System.out.print(attrName + ": (" + (attrValue == null ? "NULL" : attrValue.getClass().getName()) + ") " + attrValue);
                 first = false;
            }
             System.out.println(" }");

            Object usuarioIdObj = session.getAttribute("usuarioId");
             System.out.println("[SvEliminarFavoritos] Diagnóstico: session.getAttribute(\"usuarioId\") = " + usuarioIdObj);

            if (usuarioIdObj != null) {
                if (usuarioIdObj instanceof Long) {
                    try {
                        usuarioId = (Long) usuarioIdObj;
                        System.out.println("[SvEliminarFavoritos] Diagnóstico: Cast a Long exitoso. usuarioId = " + usuarioId);
                    } catch (ClassCastException e) {
                        System.err.println("[SvEliminarFavoritos] ERROR INESPERADO: Falló el cast a Long.");
                        e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno inesperado de sesión (Tipo)\"}");
                        return;
                    }
                } else {
                    System.err.println("[SvEliminarFavoritos] ERROR: Atributo 'usuarioId' NO es Long. Tipo: " + usuarioIdObj.getClass().getName());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno de datos de sesión (Tipo incorrecto)\"}");
                    return;
                }
            } else {
                System.out.println("[SvEliminarFavoritos] Diagnóstico: Atributo 'usuarioId' es NULL.");
            }
        }
        // === Fin Logging de Diagnóstico ===


        // --- Verificación de usuarioId ---
        if (usuarioId == null) {
            System.out.println("[SvEliminarFavoritos] usuarioId es NULL. Enviando 401.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\"error\": \"Usuario no autenticado.\"}");
            return;
        }
        // ---------------------------------
        System.out.println("[SvEliminarFavoritos] Autenticación OK. usuarioId = " + usuarioId);

        // --- Procesamiento ---
        String isbn = request.getParameter("isbn");
         System.out.println("[SvEliminarFavoritos] Parámetro isbn recibido: " + isbn);

        if (isbn == null || isbn.isBlank()) {
             System.out.println("[SvEliminarFavoritos] ISBN inválido. Enviando 400.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "{\"error\": \"El ISBN del libro es requerido.\"}");
            return;
        }

        try {
            System.out.println("[SvEliminarFavoritos] Llamando a favoritoDAO.eliminarFavorito...");
            boolean eliminado = favoritoDAO.eliminarFavorito(usuarioId, isbn);
            System.out.println("[SvEliminarFavoritos] Resultado de eliminarFavorito: " + eliminado);

            if (eliminado) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write("{\"mensaje\": \"Libro eliminado de favoritos con éxito.\"}");
                 System.out.println("[SvEliminarFavoritos] Respuesta 200 OK enviada.");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"mensaje\": \"El libro no estaba marcado como favorito o no existe.\"}");
                 System.out.println("[SvEliminarFavoritos] Respuesta 404 Not Found enviada.");
            }

        } catch (DAOException ex) {
            System.err.println("[SvEliminarFavoritos] Error DAO (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error del servidor al eliminar favorito: " + ex.getMessage() + "\"}");
            System.out.println("[SvEliminarFavoritos] Respuesta 500 (Error DAO) enviada.");

        } catch (Exception e) {
            System.err.println("[SvEliminarFavoritos] Error inesperado (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + e.getMessage());
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Ocurrió un error inesperado en el servidor.\"}");
            System.out.println("[SvEliminarFavoritos] Respuesta 500 (Error inesperado) enviada.");
        }
        System.out.println("--- [SvEliminarFavoritos] - Finalizando doDelete ---");
    }
}
