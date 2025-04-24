/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import java.util.List;

/**
 * Interfaz de acceso a datos para realizar operaciones CRUD sobre entidades de tipo Libro.
 * @author neri
 */
public interface ILibroDAO {
    /**
     * Obtiene todos los libros almacenados en la base de datos.
     *
     * @return una lista con todos los libros.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Libro> obtenerLibrosTodos() throws DAOException;

    /**
     * Busca libros por nombre, genero y nombre del autor, si alguno de los
     * parametros esta vacio no se considera para la busqueda.
     * 
     * @param nombre Nombre del libro a buscar.
     * @param genero Genero del libro a buscar.
     * @param nombreAutor Nombre del autor del libro a buscar.
     * @return Lista de libros que coincidan en la busqueda.
     * @throws DAOException En caso de error en la busqueda.
     */
    List<Libro> buscarLibro(String nombre, String genero, String nombreAutor) throws DAOException;

    /**
     * Registra un nuevo libro en la base de datos.
     *
     * @param libro el libro a registrar.
     * @throws DAOException si ocurre un error al registrar el libro.
     */
    void registrarLibro(Libro libro) throws DAOException;

    /**
     * Actualiza la información de un libro existente en la base de datos.
     *
     * @param libro el libro con los datos actualizados.
     * @throws DAOException si ocurre un error al actualizar el libro.
     */
    void actualizarLibro(Libro libro) throws DAOException;

    /**
     * Elimina un libro de la base de datos a partir de su código ISBN.
     *
     * @param isbn el ISBN del libro a eliminar.
     * @return true si el libro fue eliminado correctamente, false en caso contrario.
     * @throws DAOException si ocurre un error al eliminar el libro.
     */
    boolean eliminarLibro(String isbn) throws DAOException;
}
