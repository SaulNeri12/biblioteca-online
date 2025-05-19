/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaAdministradorDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAdministradorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Administrador;
import com.equipoweb.bibliotecaonline.servlets.errores.ErrorRespuesta;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

/**
 *
 * @author skevi
 */
@WebServlet(name = "SvLoginAdmin", urlPatterns = {"/loginAdmin"})
/**
 * Este servlet permite a los administradores iniciar sesión en la aplicación.
 */
public class SvLoginAdmin extends HttpServlet {

    private IAdministradorDAO administradorDAO = FabricaAdministradorDAO.getInstance().crearDAO();
    

    /**
     * Procesa el inicio de sesión del administrador.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //obtenemos el valor para el usuario en el jsp
        String email = request.getParameter("email");
        //obtenemos el valor de la contraseña del jsp
        String contrasena = request.getParameter("contrasena");
        
        try{
            //consultamos al administrador
            Administrador administrador = administradorDAO.iniciarSesion(email, contrasena);
            
            // guardamos la sesion
            HttpSession session = request.getSession();
            session.setAttribute("usuarioAdmin", administrador);
        
            //nos redirigimos a la pagina de inicio
            //response.sendRedirect("/jsp/indexAdministrador.jsp");
            request.getRequestDispatcher("/jsp/indexAdministrador.jsp").forward(request, response);
        
         } catch (DAOException ex) {
           // preparamos la respuesta json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            
            response.getWriter().write(error.toString());
            return;
        }
    }

}
