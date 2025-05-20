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
 * Servlet para agregar un libro a la lista de favoritos de un usuario.
 * Utiliza el ISBN del libro. Incluye logging de sesión mejorado.
 */
@WebServlet(name = "SvAgregarFavoritos", urlPatterns = {"/AgregarFavorito"})
/**
 * Este servlet permite a los usuarios agregar libros a su lista de favoritos.
 * Procesa las solicitudes POST para agregar un libro a la base de datos de favoritos del usuario.
 */
public class SvAgregarFavoritos extends HttpServlet {

    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();

    /**
     * Procesa las solicitudes POST para agregar un libro a la lista de favoritos del usuario.
     *
     * @param request  Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException      Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // === Logging de Diagnóstico de Sesión ===
        System.out.println("\n--- [SvAgregarFavoritos] - Iniciando doPost ---");
        HttpSession session = request.getSession(false); // No crear sesión nueva si no existe
        Long usuarioId = null;

        if (session == null) {
            System.out.println("[SvAgregarFavoritos] Diagnóstico: HttpSession es NULL. No hay sesión activa.");
        } else {
            System.out.println("[SvAgregarFavoritos] Diagnóstico: Session ID = " + session.getId());
            // Listar todos los atributos para ver qué hay realmente
            System.out.print("[SvAgregarFavoritos] Diagnóstico: Atributos en sesión -> { ");
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

            // Intentar obtener el usuarioId específicamente
            Object usuarioIdObj = session.getAttribute("usuarioId");
             System.out.println("[SvAgregarFavoritos] Diagnóstico: session.getAttribute(\"usuarioId\") = " + usuarioIdObj);

            if (usuarioIdObj != null) {
                if (usuarioIdObj instanceof Long) {
                    try {
                        usuarioId = (Long) usuarioIdObj;
                         System.out.println("[SvAgregarFavoritos] Diagnóstico: Cast a Long exitoso. usuarioId = " + usuarioId);
                    } catch (ClassCastException e) {
                         // Este bloque no debería alcanzarse si instanceof es true, pero por seguridad
                         System.err.println("[SvAgregarFavoritos] ERROR INESPERADO: Falló el cast a Long aunque instanceof era true.");
                         e.printStackTrace(); // Loguear el error completo
                         response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno inesperado de sesión (Tipo)\"}");
                         return;
                    }
                } else {
                     System.err.println("[SvAgregarFavoritos] ERROR: El atributo 'usuarioId' existe pero NO es de tipo Long. Tipo encontrado: " + usuarioIdObj.getClass().getName());
                     response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno de datos de sesión (Tipo incorrecto)\"}");
                     return;
                }
            } else {
                 System.out.println("[SvAgregarFavoritos] Diagnóstico: El atributo 'usuarioId' es NULL en la sesión.");
            }
        }
        // === Fin Logging de Diagnóstico ===


        // --- Verificación de usuarioId (lógica original) ---
        if (usuarioId == null) {
             System.out.println("[SvAgregarFavoritos] usuarioId es NULL después de la verificación. Enviando 401.");
             response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "{\"error\": \"Usuario no autenticado.\"}");
             return;
        }
        // ---------------------------------------------------
         System.out.println("[SvAgregarFavoritos] Autenticación OK. usuarioId = " + usuarioId);

        // --- Procesamiento de la solicitud ---
        String isbn = request.getParameter("isbn");
         System.out.println("[SvAgregarFavoritos] Parámetro isbn recibido: " + isbn);

        if (isbn == null || isbn.isBlank()) {
             System.out.println("[SvAgregarFavoritos] ISBN es inválido. Enviando 400.");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "{\"error\": \"El ISBN del libro es requerido.\"}");
            return;
        }

        try {
             System.out.println("[SvAgregarFavoritos] Llamando a favoritoDAO.asignarFavorito...");
            favoritoDAO.asignarFavorito(usuarioId, isbn);
             System.out.println("[SvAgregarFavoritos] Llamada a DAO exitosa.");

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"mensaje\": \"Libro agregado a favoritos con éxito.\"}");
            System.out.println("[SvAgregarFavoritos] Respuesta 200 OK enviada.");

        } catch (DAOException ex) {
             System.err.println("[SvAgregarFavoritos] Error DAO al agregar favorito (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + ex.getMessage());
             ex.printStackTrace(); // Loguear stack trace completo

             // Mejorar manejo de errores específicos de DAO
             if (ex.getMessage() != null && ex.getMessage().contains("ya está marcado como favorito")) {
                 response.setStatus(HttpServletResponse.SC_CONFLICT); // 409
                 response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
             } else if (ex.getMessage() != null && (ex.getMessage().contains("no encontrado") || ex.getMessage().contains("not found"))) {
                 response.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404
                 response.getWriter().write("{\"error\": \"" + ex.getMessage() + "\"}");
             } else {
                 response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                 response.getWriter().write("{\"error\": \"Error del servidor al agregar favorito: " + ex.getMessage() + "\"}");
             }
             System.out.println("[SvAgregarFavoritos] Respuesta de error DAO enviada (Status: " + response.getStatus() + ")");

        } catch (Exception e) {
             System.err.println("[SvAgregarFavoritos] Error inesperado (Usuario: " + usuarioId + ", ISBN: " + isbn + "): " + e.getMessage());
             e.printStackTrace();
             response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Ocurrió un error inesperado en el servidor.\"}");
             System.out.println("[SvAgregarFavoritos] Respuesta 500 (Error inesperado) enviada.");
        }
         System.out.println("--- [SvAgregarFavoritos] - Finalizando doPost ---");
    }
}
