/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;

/**
 *
 * @author Saul Neri
 */
public class FabricaUsuariosDAO implements IFabricaDAO<IUsuariosDAO> {

    private static FabricaUsuariosDAO _instancia;
    
    private FabricaUsuariosDAO() {
        
    }
    
    public static FabricaUsuariosDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaUsuariosDAO();
        }
        
        return _instancia;
    }
    
    @Override
    public IUsuariosDAO crearDAO() {
        return UsuariosDAO.getInstance();
    }
}
