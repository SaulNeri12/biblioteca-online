/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;

/**
 *
 * @author Saul Neri
 */
/**
 * Fabrica para la creación de objetos AutorDAO.
 */
public class FabricaAutorDAO implements IFabricaDAO<IAutorDAO> {

    private static FabricaAutorDAO _instancia;
    
    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private FabricaAutorDAO() {
        
    }
    
    /**
     * Obtiene la instancia de la clase (Singleton).
     * @return La instancia de la clase.
     */
    public static FabricaAutorDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaAutorDAO();
        }
        
        return _instancia;
    }
    
    /**
     * Crea una instancia de AutorDAO.
     * @return Una instancia de AutorDAO.
     */
    @Override
    public IAutorDAO crearDAO() {
        return AutorDAO.getInstance();
    }
}
