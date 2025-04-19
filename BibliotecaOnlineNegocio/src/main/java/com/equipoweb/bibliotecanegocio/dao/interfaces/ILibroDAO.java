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
     * Obtiene una lista de libros que pertenecen a los géneros especificados.
     *
     * @param generos una lista de nombres de géneros por los que se desea filtrar.
     * @return una lista de libros que coinciden con los géneros dados.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Libro> obtenerLibrosPorGeneros(List<String> generos) throws DAOException;

    /**
     * Busca libros cuyo nombre contenga el texto proporcionado (búsqueda parcial o completa).
     *
     * @param nombreBusqueda el texto a buscar en los nombres de los libros.
     * @return una lista de libros cuyos nombres coincidan parcial o totalmente con el texto dado.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Libro> obtenerLibrosPorNombre(String nombreBusqueda) throws DAOException;

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
