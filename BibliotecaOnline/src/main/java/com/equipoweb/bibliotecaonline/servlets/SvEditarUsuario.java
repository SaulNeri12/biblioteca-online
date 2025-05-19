/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvEditarUsuario", urlPatterns = {"/EditarUsuario"})
/**
 * Este servlet permite editar la información de un usuario existente.
 */
public class SvEditarUsuario extends HttpServlet {

    private IUsuariosDAO usuarios = FabricaUsuariosDAO.getInstance().crearDAO();
    
    /**
     * Endpoint que recibe un json con la nueva informacion de un usuario y lo
     * manda actualizar en la base de datos.
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    /**
     * Maneja las solicitudes HTTP PUT para actualizar la información de un usuario.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            BufferedReader reader = request.getReader();
            ObjectMapper mapper = new ObjectMapper();
            Usuario usuarioActualizado = mapper.readValue(reader, Usuario.class);

            // Lógica de edición con el DAO
            usuarios.actualizarUsuario(usuarioActualizado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            mapper.writeValue(response.getWriter(), usuarioActualizado);

        } catch (DAOException | IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("Error al editar al usuario: " + e.getMessage());
        }
    }


}
