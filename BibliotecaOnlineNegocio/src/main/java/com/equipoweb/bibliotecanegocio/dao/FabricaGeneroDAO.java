/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.IGeneroDAO;

/**
 *
 * @author Saul Neri
 */
public class FabricaGeneroDAO implements IFabricaDAO<IGeneroDAO> {

    private static FabricaGeneroDAO _instancia;
    
    private FabricaGeneroDAO() {
        
    }
    
    public static FabricaGeneroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaGeneroDAO();
        }
        
        return _instancia;
    }
    
    @Override
    public IGeneroDAO crearDAO() {
        return GeneroDAO.getInstance();
    }
}
