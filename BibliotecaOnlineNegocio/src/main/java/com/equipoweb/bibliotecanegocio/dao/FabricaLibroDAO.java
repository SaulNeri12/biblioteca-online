/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;

/**
 *
 * @author Saul Neri
 */
public class FabricaLibroDAO implements IFabricaDAO<ILibroDAO> {

    private static FabricaLibroDAO _instancia;
    
    private FabricaLibroDAO() {
        
    }
    
    public static FabricaLibroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaLibroDAO();
        }
        
        return _instancia;
    }
    
    @Override
    public ILibroDAO crearDAO() {
        return LibroDAO.getInstance();
    }
}
