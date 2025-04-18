/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;

/**
 *
 * @author Saul Neri
 */
public class FabricaFavoritoLibroDAO implements IFabricaDAO<IFavoritoLibroDAO> {

    private static FabricaFavoritoLibroDAO _instancia;
    
    private FabricaFavoritoLibroDAO() {
        
    }
    
    public static FabricaFavoritoLibroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaFavoritoLibroDAO();
        }
        
        return _instancia;
    }

    @Override
    public FavoritoLibroDAO crearDAO() {
        return FavoritoLibroDAO.getInstance();
    }  
}
