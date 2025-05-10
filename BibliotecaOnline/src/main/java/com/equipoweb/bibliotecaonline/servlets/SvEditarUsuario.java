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

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvEditarUsuario", urlPatterns = {"/EditarUsuario"})
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
