/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import java.util.List;

/**
 * Interfaz de acceso a datos para realizar operaciones relacionadas con los géneros de libros.
 * Define los métodos que permiten interactuar con la base de datos para consultar géneros disponibles.
 * 
 * @author neri
 */
public interface IGeneroDAO {

    /**
     * Obtiene una lista con todos los géneros registrados en la base de datos.
     *
     * @return una lista de objetos {@link Genero} que representan todos los géneros disponibles.
     */
    List<Genero> obtenerGenerosTodos() throws DAOException;
}
