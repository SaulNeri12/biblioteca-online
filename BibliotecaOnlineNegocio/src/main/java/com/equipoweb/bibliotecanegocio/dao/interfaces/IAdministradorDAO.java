/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.Administrador;

/**
 *
 * @author skevi
 */
public interface IAdministradorDAO {
    
    /**
     * Verifica si existen credenciales válidas dentro de la tabla de 
     * administradores para iniciar sesión con el email y la contraseña 
     * proporcionadas.
     *
     * @param email el correo electrónico del usuario.
     * @param contrasena la contraseña del usuario.
     * @return true si las credenciales son correctas y se puede iniciar sesión, false en caso contrario.
     * @throws DAOException si ocurre un error al verificar las credenciales.
     */
    Administrador iniciarSesion(String email, String contrasena) throws DAOException;
}
