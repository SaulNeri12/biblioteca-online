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
public class FabricaAutorDAO implements IFabricaDAO<IAutorDAO> {

    private static FabricaAutorDAO _instancia;
    
    private FabricaAutorDAO() {
        
    }
    
    public static FabricaAutorDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaAutorDAO();
        }
        
        return _instancia;
    }
    
    @Override
    public IAutorDAO crearDAO() {
        return AutorDAO.getInstance();
    }
}
