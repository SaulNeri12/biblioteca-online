/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.dao.interfaces.IAdministradorDAO;

/**
 *
 * @author skevi
 */
public class FabricaAdministradorDAO implements IFabricaDAO<IAdministradorDAO>{
    
    /**
     * Instancia global de la clase.
     */
    private static FabricaAdministradorDAO _instancia;
    
    /**
     * Constructor privado para evitar instacias externas.
     */
    private FabricaAdministradorDAO() {
        
    }
    
    /**
     * Metodo para la obtencion de la instancia global.
     * 
     * @return Instancia global de la clase.
     */
    public static FabricaAdministradorDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FabricaAdministradorDAO();
        }
        
        return _instancia;
    }

    /**
     * Funcion que obtiene la clase dao correspondiente a la fabrica
     * @return Intancia de la clase DAO.
     */
    @Override
    public IAdministradorDAO crearDAO() {
       return AdministradorDAO.getInstance();
    }
    
    
}
