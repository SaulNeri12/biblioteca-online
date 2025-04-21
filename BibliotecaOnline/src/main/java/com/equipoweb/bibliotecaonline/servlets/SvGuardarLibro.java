/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.equipoweb.bibliotecaonline.servlets;

import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet encargado de manejar las operaciones CRUD con los libros de la aplicacion.
 * @author neri
 */
@WebServlet(name = "SvLibro", urlPatterns = {"/libro"})
public class SvGuardarLibro extends HttpServlet {

    private final ILibroDAO libroDAO = FabricaLibroDAO.getInstance().crearDAO();
    
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

        request.setCharacterEncoding("UTF-8");

        String isbn = request.getParameter("isbn");
        String nombre = request.getParameter("nombre");
        String descripcion = request.getParameter("descripcion");
        String contenidoAdulto = request.getParameter("contenidoAdulto");
        String idAutorStr = request.getParameter("idAutor");
        String editorial = request.getParameter("editorial");
        String anioStr = request.getParameter("anio");
        String numPaginasStr = request.getParameter("numPaginas");
        String[] idsGeneros = request.getParameterValues("idGeneros[]");

        if (isbn == null || isbn.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "El ISBN es obligatorio.");
            return;
        }

        try {
            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setNombre(nombre);
            libro.setDescripcion(descripcion);
            libro.setContenidoAdulto(Boolean.valueOf(contenidoAdulto));
            libro.setEditorial(editorial);
            libro.setAnio(Integer.valueOf(anioStr));
            libro.setNumPaginas(Integer.valueOf(numPaginasStr));

            // Crear el autor con solo su ID
            Autor autor = new Autor();
            autor.setId(Long.valueOf(idAutorStr));
            libro.setAutor(autor);

            // Crear la lista de géneros a partir de los IDs
            if (idsGeneros != null) {
                List<Genero> generos = Arrays.stream(idsGeneros)
                        .map(id -> {
                            Genero genero = new Genero();
                            genero.setId(Long.valueOf(id));
                            return genero;
                        })
                        .collect(Collectors.toList());

                libro.setGeneros(generos);
            }

            // Guardar libro
            libroDAO.registrarLibro(libro);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.setContentType("application/json");
            response.getWriter().write("{\"mensaje\": \"Libro guardado con éxito.\"}");

        } catch (DAOException ex) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al guardar el libro.");
            ex.printStackTrace();
        } catch (NumberFormatException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Año o número de páginas inválido.");
        }
    }

}
