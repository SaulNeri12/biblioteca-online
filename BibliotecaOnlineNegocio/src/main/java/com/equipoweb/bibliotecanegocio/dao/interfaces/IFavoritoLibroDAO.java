/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.FavoritoLibro;
import com.equipoweb.bibliotecanegocio.entidades.Libro; // Importar Libro
import java.util.List;

/**
 * Interfaz para la gestión de libros favoritos en la capa de acceso a datos (DAO).
 * Proporciona métodos para obtener, asignar y eliminar libros marcados como favoritos por un usuario.
 *
 * @author neri (modificado por AI)
 */
public interface IFavoritoLibroDAO {

    /**
     * Obtiene las entidades de relación FavoritoLibro para un usuario específico.
     * (Puede ser útil para obtener metadatos de la relación si los hubiera).
     *
     * @param idUsuario El ID del usuario cuyos favoritos se desean consultar.
     * @return Una lista de objetos FavoritoLibro.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    List<FavoritoLibro> obtenerFavoritos(Long idUsuario) throws DAOException;

    /**
     * Obtiene la lista de Libros marcados como favoritos por un usuario específico.
     * Este es el método que probablemente usará el servlet SvFavoritos.
     *
     * @param idUsuario El ID del usuario cuyos libros favoritos se desean consultar.
     * @return Una lista de objetos Libro.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    List<Libro> obtenerLibrosFavoritosPorUsuario(Long idUsuario) throws DAOException; // NUEVO METODO

    /**
     * Asigna un libro como favorito para un usuario, usando el ISBN del libro.
     *
     * @param idUsuario El ID del usuario que marca el libro como favorito.
     * @param isbn El ISBN del libro que se desea marcar como favorito. // CAMBIADO de idLibro a isbn
     * @throws DAOException Si ocurre un error durante la operación de persistencia,
     * si el usuario o el libro no se encuentran, o si ya es favorito.
     */
    void asignarFavorito(Long idUsuario, String isbn) throws DAOException; // PARAMETRO CAMBIADO

    /**
     * Elimina un libro de la lista de favoritos de un usuario, usando el ISBN del libro.
     *
     * @param idUsuario El ID del usuario.
     * @param isbn El ISBN del libro que se desea eliminar de favoritos. // CAMBIADO de idLibro a isbn
     * @return true si se eliminó correctamente; false si no se encontró el favorito para eliminar.
     * @throws DAOException Si ocurre un error al eliminar el favorito en la base de datos o al buscar el libro por ISBN.
     */
    boolean eliminarFavorito(Long idUsuario, String isbn) throws DAOException; // PARAMETRO CAMBIADO
}