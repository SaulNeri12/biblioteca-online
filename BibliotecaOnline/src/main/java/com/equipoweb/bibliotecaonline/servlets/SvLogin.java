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
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet encargado de mostrar la pantalla de inicio de sesion y procesar el
 * inicio de sesion.
 * @author neri
 * modificado by caarl :D
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/login"})
/**
 * Este servlet permite a los usuarios iniciar sesión en la aplicación.
 */
public class SvLogin extends HttpServlet {
    
    private IUsuariosDAO usuariosDAO = FabricaUsuariosDAO.getInstance().crearDAO();


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Muestra la pantalla de inicio de sesión.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * Procesa el inicio de sesión del usuario.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");

        Usuario usuario = null;

        try {
            // Asumiendo que iniciarSesion devuelve el objeto Usuario completo si es exitoso
            // y que maneja la verificación de contraseña (idealmente con hash)
            usuario = this.usuariosDAO.iniciarSesion(email, contrasena);

            // Si iniciarSesion devuelve null en lugar de lanzar excepción para login inválido
            if (usuario == null) {
                 // Manejar login inválido (p.ej., redirigir con mensaje de error)
                 System.out.println("[SvLogin] Login fallido: Usuario no encontrado o contraseña incorrecta.");
                 response.sendRedirect("login?error=" + java.net.URLEncoder.encode("Email o contraseña incorrectos", "UTF-8"));
                 return;
            }

        } catch (DAOException ex) {
            // Manejar errores específicos del DAO (ej: problema de conexión)
            System.err.println("[SvLogin] DAOException durante inicio de sesión: " + ex.getMessage());
            ex.printStackTrace();
            // Podrías redirigir a una página de error o al login con un mensaje genérico
             response.sendRedirect("login?error=" + java.net.URLEncoder.encode("Error al intentar iniciar sesión. Intente más tarde.", "UTF-8"));
             return; // Importante salir
         } catch (Exception e) {
             // Capturar otras excepciones inesperadas
             System.err.println("[SvLogin] Excepción inesperada durante inicio de sesión: " + e.getMessage());
             e.printStackTrace();
             response.sendRedirect("login?error=" + java.net.URLEncoder.encode("Ocurrió un error inesperado.", "UTF-8"));
            return; // Importante salir
         }


        // guardamos la sesion
        HttpSession session = request.getSession(); // Obtener o crear sesión

        // 1. Guardar el objeto Usuario completo (ya lo haces)
        session.setAttribute("usuario", usuario);
        System.out.println("[SvLogin] Atributo 'usuario' establecido en sesión: " + usuario); // Log

        // 2. *** AÑADIR ESTA LÍNEA PARA GUARDAR EL ID ***
        //    Asegúrate que usuario.getId() devuelve Long o long
        session.setAttribute("usuarioId", usuario.getId());
        System.out.println("[SvLogin] Atributo 'usuarioId' establecido en sesión: " + usuario.getId()); // Log de confirmación

        // Redirigir a la página principal
        System.out.println("[SvLogin] Login exitoso. Redirigiendo a index.jsp");
        response.sendRedirect("index.jsp");
    }

}
