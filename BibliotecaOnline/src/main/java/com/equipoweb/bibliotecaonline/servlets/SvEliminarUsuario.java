/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecaonline.servlets.errores.ErrorRespuesta;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;

/**
 * Servlet encargado de manejar las operaciones CRUD de un usuario, asi como
 * mostrar la informacion de un usuario.
 *
 * @author neri
 */
@WebServlet(name = "SvUsuario", urlPatterns = {"/EliminarUsuario"})
/**
 * Este servlet permite eliminar un usuario del sistema.
 */
public class SvEliminarUsuario extends HttpServlet {

    private IUsuariosDAO usuarios = FabricaUsuariosDAO.getInstance().crearDAO();


    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    /**
     * Maneja las solicitudes HTTP DELETE para eliminar un usuario del sistema.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idUsuarioParametro = request.getParameter("id_usuario");
        
        System.out.println("Query String: " + request.getQueryString()); // Ver qué datos llegan

        if (idUsuarioParametro == null) {
            this.sendErrorJSON(response, "No se envió el ID del usuario.");
            return;
        }

        Long idUsuario = null;

        try {
            idUsuario = Optional.of(Long.valueOf(idUsuarioParametro))
                    .orElseThrow(() -> new IllegalArgumentException("El ID especificado no cumple con el formato correcto."));
        } catch (IllegalArgumentException ex) {
            this.sendErrorJSON(response, ex.getMessage());
            return;
        }

        try {
            this.usuarios.eliminarUsuario(idUsuario);
        } catch (DAOException ex) {
            this.sendErrorJSON(response, ex.getMessage());
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("{\"mensaje\": \"Usuario eliminado con exito.\"}");
    }

    private void sendErrorJSON(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ErrorRespuesta error = new ErrorRespuesta(message);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(error);  // ✅ convierte correctamente
        response.getWriter().write(json);
    }

}
