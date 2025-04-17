/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import java.util.List;

/**
 * Interfaz de acceso a datos para realizar operaciones relacionadas con los autores.
 * Define los métodos que permiten obtener información de los autores desde la base de datos.
 * 
 * @author neri
 */
public interface IAutorDAO {

    /**
     * Obtiene una lista con todos los autores registrados en la base de datos.
     *
     * @return una lista de objetos {@link Autor} que representan todos los autores disponibles.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Autor> obtenerAutoresTodos() throws DAOException;

    /**
     * Busca y obtiene una lista de autores cuyo nombre coincida parcial o totalmente
     * con el texto proporcionado.
     *
     * @param nombreBusqueda el nombre o parte del nombre del autor a buscar.
     * @return una lista de objetos {@link Autor} que coinciden con el criterio de búsqueda.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Autor> obtenerAutoresPorNombre(String nombreBusqueda) throws DAOException;
}
