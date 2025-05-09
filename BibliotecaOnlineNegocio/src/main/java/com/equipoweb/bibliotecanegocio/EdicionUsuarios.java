/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saul Neri
 */
public class EdicionUsuarios {
    public static void main(String[] args) {
        IUsuariosDAO usuarios = FabricaUsuariosDAO.getInstance().crearDAO();
        
        Usuario u1 = null;
        
        try {
            u1 = usuarios.obtenerUsuariosTodos().getFirst();
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }
        
        u1.setContrasena("543210");
        
        try {
            usuarios.actualizarUsuario(u1);
            
            System.out.println("usuario modificado!!! [contrasena]");
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }
        
        u1.setNombre("sheshnain13");
        
        try {
            usuarios.actualizarUsuario(u1);
            
            System.out.println("usuario modificado!!! [nombre]");
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }
        
        u1.setTelefono("9997775554");
        
        try {
            usuarios.actualizarUsuario(u1);
            
            System.out.println("usuario modificado!!! [telefono]");
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }
        
        try {
            usuarios.eliminarUsuario(u1.getId());
            
            System.out.println("usuario eliminado!!!");
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }
    }
}
