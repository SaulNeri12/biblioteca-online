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
 */
@WebServlet(name = "SvLogin", urlPatterns = {"/login"})
public class SvLogin extends HttpServlet {
    
    private IUsuariosDAO usuariosDAO = FabricaUsuariosDAO.getInstance().crearDAO();


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/jsp/login.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String contrasena = request.getParameter("contrasena");
        
        Usuario usuario = null;
        
        try {
            usuario = this.usuariosDAO.iniciarSesion(email, contrasena);
        } catch (DAOException ex) {
           // preparamos la respuesta json
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            
            ErrorRespuesta error = new ErrorRespuesta(ex.getMessage());
            
            response.getWriter().write(error.toString());
            return;
        }
        
        // guardamos la sesion
        HttpSession session = request.getSession();
        session.setAttribute("usuario", usuario);
        
        response.sendRedirect("index.jsp");
    }

}
