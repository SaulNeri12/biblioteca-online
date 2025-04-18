/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaAutorDAO;
import com.equipoweb.bibliotecanegocio.dao.FabricaFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.FabricaGeneroDAO;
import com.equipoweb.bibliotecanegocio.dao.FabricaLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IGeneroDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.equipoweb.bibliotecanegocio.entidades.FavoritoLibro;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author neri
 */
public class BibliotecaOnlineNegocio {

    public static void main(String[] args) {

        FabricaLibroDAO fabricaLibroDAO = FabricaLibroDAO.getInstance();
        ILibroDAO libroDAO = fabricaLibroDAO.crearDAO();

        FabricaGeneroDAO fabricaGeneroDAO = FabricaGeneroDAO.getInstance();
        IGeneroDAO generoDAO = fabricaGeneroDAO.crearDAO();

        FabricaUsuariosDAO fabricaUsuariosDAO = FabricaUsuariosDAO.getInstance();
        IUsuariosDAO usuariosDAO = fabricaUsuariosDAO.crearDAO();

        FabricaAutorDAO fabricaAutorDAO = FabricaAutorDAO.getInstance();
        IAutorDAO autorDAO = fabricaAutorDAO.crearDAO();

        FabricaFavoritoLibroDAO fabricaFavoritoLibroDAO = FabricaFavoritoLibroDAO.getInstance();
        IFavoritoLibroDAO favoritoLibroDAO = fabricaFavoritoLibroDAO.crearDAO();

        List<Autor> autores = null;

        try {
            autores = autorDAO.obtenerAutoresTodos();
            System.out.println(autores);
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("-----------------------------");

        try {

            System.out.println(autorDAO.obtenerAutoresPorNombre("vic"));
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Libro> libros = null;

        try {
            libros = libroDAO.obtenerLibrosTodos();
            System.out.println(libros);
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        Usuario usr = new Usuario();
        usr.setNombre("Saul Neri");
        usr.setEmail("saul.neri22@gmail.com");
        usr.setTelefono("885929134");
        usr.setContrasena("shesh12345");

        try {
            usuariosDAO.registrarUsuario(usr);
            System.out.println("Se registro el usuario: " + usr.getId());
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        usr.setEmail("shesh");

        try {
            usuariosDAO.iniciarSesion(usr.getEmail(), usr.getContrasena());
            System.out.println("inicio sesion con exito");
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        List<Genero> generos = null;

        try {
            generos = generoDAO.obtenerGenerosTodos();
            System.out.println(generos);
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("--------------- obtener usuario ------");

        try {
            System.out.println(usuariosDAO.obtenerUsuario(1l));
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        Libro libro = new Libro();

        libro.setIsbn("3881881891090202");
        libro.setNombre("Parque Jurassico");
        libro.setAutor(autores.get(0));
        libro.setDescripcion("Una historia de ingenieria genetica y dinosaurios");
        libro.setContenidoAdulto(Boolean.FALSE);
        libro.setNumPaginas(378);
        libro.setEditorial("Shesh!!!");
        libro.setGeneros(Arrays.asList(generos.get(0), generos.get(1)));
        libro.setAnio(1990);

        try {
            libroDAO.registrarLibro(libro);
            System.out.println("libro registrado!!!");
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            favoritoLibroDAO.asignarFavorito(1l, libros.get(0).getId());
            System.out.println("agregado a favoritos");
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            List<FavoritoLibro> favLibros = favoritoLibroDAO.obtenerFavoritos(1l);

            for (FavoritoLibro fav : favLibros) {
                System.out.println(
                        String.format(
                                "Libro: %s, Usuario: %s",
                                fav.getLibro(), fav.getUsuario()
                        )
                );
            }
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            favoritoLibroDAO.eliminarFavorito(1l, libros.get(0).getId());
            System.out.println("Eliminado de favoritos");
        } catch (DAOException ex) {
            Logger.getLogger(BibliotecaOnlineNegocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
