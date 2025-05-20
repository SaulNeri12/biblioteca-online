/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import com.equipoweb.bibliotecaonline.servlets.errores.ErrorRespuesta;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Servlet encargado de mostrar la pagina de registro del usuario y procesar el
 * registro del mismo.
 *
 * @author neri
 */
@WebServlet(name = "SvRegistro", urlPatterns = {"/registro"})
/**
 * Este servlet permite a los usuarios registrarse en la aplicación.
 */
public class SvRegistro extends HttpServlet {

    private IUsuariosDAO usuariosDAO = FabricaUsuariosDAO.getInstance().crearDAO();
    private SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * Muestra la página de registro.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
    }

    /**
     * Procesa el registro de un nuevo usuario.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String contrasena = request.getParameter("contrasena");
        
        Date fechaNac = null;
        try {
            fechaNac = this.formatoFecha.parse(request.getParameter("fecha_nac"));
        } catch (ParseException ex) {
            
            response.sendRedirect(request.getContextPath() + "/registro");
            
            /*
            // preparamos la respuesta json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            ErrorRespuesta error = new ErrorRespuesta("El formato de fecha es incorrecto.");
            
            response.getWriter().write(error.toString());*/
            return;
        }
        
        request.setAttribute("nombre", nombre);
        request.setAttribute("email", email);
        request.setAttribute("telefono", telefono);
        request.setAttribute("fechaNac", fechaNac);
        request.setAttribute("contrasena", contrasena); // No recomendado mostrarla normalmente

        Usuario usuario = new Usuario();
        
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setFechaNacimiento(fechaNac);
        usuario.setTelefono(telefono);
        usuario.setContrasena(contrasena);
       
        try {
            this.usuariosDAO.registrarUsuario(usuario);
        } catch (DAOException ex) {
            
            response.sendRedirect(request.getContextPath() + "/registro");
            
            /*
            // preparamos la respuesta json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            
            response.getWriter().write(error.toString());
*/
            return;
        }
        
        // guardamos la sesion
        //usuario.setContrasena(null);
        //HttpSession session = request.getSession();
        //session.setAttribute("usuario", usuario);
        
        // Redireccionar al JSP
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        //response.sendRedirect("usuario");
    }

}
