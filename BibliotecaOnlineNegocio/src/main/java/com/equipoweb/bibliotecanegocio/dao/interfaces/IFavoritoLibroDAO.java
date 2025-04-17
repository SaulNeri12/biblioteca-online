/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.FavoritoLibro;

/** 
 * Interfaz para la gestión de libros favoritos en la capa de acceso a datos (DAO).
 * Proporciona métodos para obtener, asignar y eliminar libros marcados como favoritos por un usuario.
 * 
 * @author neri
 */
public interface IFavoritoLibroDAO {

    /**
     * Obtiene los libros marcados como favoritos por un usuario específico.
     *
     * @param idUsuario El ID del usuario cuyos favoritos se desean consultar.
     * @return Un objeto FavoritoLibro que contiene los libros favoritos del usuario.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    FavoritoLibro obtenerFavoritos(Long idUsuario) throws DAOException;

    /**
     * Asigna un libro como favorito para un usuario.
     *
     * @param idUsuario El ID del usuario que marca el libro como favorito.
     * @param idLibro El ID del libro que se desea marcar como favorito.
     * @throws DAOException Si ocurre un error durante la operación de persistencia.
     */
    void asignarFavorito(Long idUsuario, Long idLibro) throws DAOException;

    /**
     * Elimina un libro de la lista de favoritos de un usuario.
     *
     * @param idUsuario El ID del usuario.
     * @param idLibro El ID del libro que se desea eliminar de favoritos.
     * @return true si se eliminó correctamente; false si no se encontró o no se eliminó.
     * @throws DAOException Si ocurre un error al eliminar el favorito en la base de datos.
     */
    boolean eliminarFavorito(Long idUsuario, Long idLibro) throws DAOException;
}
