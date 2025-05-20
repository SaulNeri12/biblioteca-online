/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

/**
 *
 * @author nerix
 */
@WebServlet(name = "SvLogOut", urlPatterns = {"/logout"})
/**
 * Este servlet permite a los usuarios cerrar sesión en la aplicación.
 */
public class SvLogOut extends HttpServlet {


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Cierra la sesión del usuario y lo redirige a la página de inicio.
     * @param request Objeto HttpServletRequest que contiene la solicitud del cliente.
     * @param response Objeto HttpServletResponse que contiene la respuesta del servlet.
     * @throws ServletException Si el servlet encuentra un problema al manejar la solicitud.
     * @throws IOException Si ocurre un error de entrada/salida al enviar la respuesta.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        
        boolean usuarioLogeado = Optional.ofNullable((Usuario) session.getAttribute("usuario")).isPresent();
        
        if (usuarioLogeado) {
            // elimina la sesion
            session.setAttribute("usuario", null);
        }
        
        // redirecciona al usuario a la pagina de inicio
        response.sendRedirect("index.jsp");
        
    }
}
