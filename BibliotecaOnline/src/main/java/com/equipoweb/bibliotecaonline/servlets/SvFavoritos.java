/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Libro;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Collections;
import java.util.Enumeration; // Para listar atributos de sesión

/**
 * Servlet para obtener la lista de libros favoritos de un usuario.
 * Incluye logging de sesión mejorado.
 */
@WebServlet(name = "SvFavoritos", urlPatterns = {"/Favoritos"})
public class SvFavoritos extends HttpServlet {

    private final IFavoritoLibroDAO favoritoDAO = FabricaFavoritoLibroDAO.getInstance().crearDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // === Logging de Diagnóstico de Sesión ===
         System.out.println("\n--- [SvFavoritos] - Iniciando doGet ---");
        HttpSession session = request.getSession(false);
        Long usuarioId = null;

        if (session == null) {
            System.out.println("[SvFavoritos] Diagnóstico: HttpSession es NULL.");
        } else {
            System.out.println("[SvFavoritos] Diagnóstico: Session ID = " + session.getId());
             System.out.print("[SvFavoritos] Diagnóstico: Atributos en sesión -> { ");
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
            System.out.println("[SvFavoritos] Diagnóstico: session.getAttribute(\"usuarioId\") = " + usuarioIdObj);

            if (usuarioIdObj != null) {
                if (usuarioIdObj instanceof Long) {
                    try {
                        usuarioId = (Long) usuarioIdObj;
                         System.out.println("[SvFavoritos] Diagnóstico: Cast a Long exitoso. usuarioId = " + usuarioId);
                    } catch (ClassCastException e) {
                         System.err.println("[SvFavoritos] ERROR INESPERADO: Falló el cast a Long.");
                         e.printStackTrace();
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno inesperado de sesión (Tipo)\"}");
                        return;
                    }
                } else {
                     System.err.println("[SvFavoritos] ERROR: Atributo 'usuarioId' NO es Long. Tipo: " + usuarioIdObj.getClass().getName());
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\": \"Error interno de datos de sesión (Tipo incorrecto)\"}");
                    return;
                }
            } else {
                 System.out.println("[SvFavoritos] Diagnóstico: Atributo 'usuarioId' es NULL.");
            }
        }
        // === Fin Logging de Diagnóstico ===


        // --- Verificación y Procesamiento ---
        if (usuarioId == null) {
            // Si no hay usuario, devolver lista vacía es amigable con el frontend.
             System.out.println("[SvFavoritos] usuarioId es NULL. Devolviendo lista vacía.");
            response.getWriter().write(mapper.writeValueAsString(Collections.emptyList()));
            return;
        }
        // ------------------------------------
         System.out.println("[SvFavoritos] Autenticación OK. usuarioId = " + usuarioId);


        try {
             System.out.println("[SvFavoritos] Llamando a favoritoDAO.obtenerLibrosFavoritosPorUsuario...");
            List<Libro> librosFavoritos = favoritoDAO.obtenerLibrosFavoritosPorUsuario(usuarioId);
             System.out.println("[SvFavoritos] Llamada a DAO exitosa. Libros encontrados: " + (librosFavoritos != null ? librosFavoritos.size() : "null"));

            String jsonResponse = mapper.writeValueAsString(librosFavoritos);
            response.getWriter().write(jsonResponse);
             System.out.println("[SvFavoritos] Respuesta JSON enviada con " + (librosFavoritos != null ? librosFavoritos.size() : "0") + " libros.");

        } catch (DAOException ex) {
             System.err.println("[SvFavoritos] Error DAO (Usuario: " + usuarioId + "): " + ex.getMessage());
            ex.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error del servidor al obtener favoritos: " + ex.getMessage() + "\"}");
             System.out.println("[SvFavoritos] Respuesta 500 (Error DAO) enviada.");
        } catch (Exception e) { // Incluye JsonProcessingException de Jackson
             System.err.println("[SvFavoritos] Error inesperado/JSON (Usuario: " + usuarioId + "): " + e.getMessage());
            e.printStackTrace();
            // Evitar sendError si ya se escribió parte de la respuesta (aunque aquí es menos probable)
            if (!response.isCommitted()) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("{\"error\": \"Ocurrió un error inesperado en el servidor al procesar favoritos.\"}");
            }
             System.out.println("[SvFavoritos] Respuesta 500 (Error inesperado/JSON) enviada.");
        }
         System.out.println("--- [SvFavoritos] - Finalizando doGet ---");
    }
}