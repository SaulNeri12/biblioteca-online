/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaUsuariosDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import java.util.Date;

/**
 *
 * @author Saul Neri
 */
/**
 * Clase para la edición de usuarios.
 */
public class EdicionUsuarios {
    /**
     * Método principal para la edición de usuarios.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        IUsuariosDAO usuarios = FabricaUsuariosDAO.getInstance().crearDAO();

        Usuario u1 = null;

        try {
            u1 = usuarios.obtenerUsuariosTodos().get(0);
            System.out.println("ANTES:");
            System.out.println("Nombre: " + u1.getNombre());
            System.out.println("Email: " + u1.getEmail());
            System.out.println("Fecha de nacimiento: " + u1.getFechaNacimiento());
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }

        u1.setNombre("sheshnain14");
        u1.setEmail("noman2@example.com");
        u1.setFechaNacimiento(new Date());
        u1.setTelefono("1234567890");
        
        System.out.println(u1.getFechaNacimiento());

        try {
            usuarios.actualizarUsuario(u1);
            System.out.println("usuario modificado!!! [nombre/email/fecha]");
        } catch (DAOException ex) {
            System.out.println("### " + ex.getMessage());
        }

        // Volver a obtener el usuario para verificar si los cambios se reflejan
        try {
            Usuario verificado = usuarios.obtenerUsuariosTodos().get(0);
            System.out.println("DESPUÉS:");
            System.out.println("Nombre: " + verificado.getNombre());
            System.out.println("Email: " + verificado.getEmail());
            System.out.println("Fecha de nacimiento: " + verificado.getFechaNacimiento());
        } catch (DAOException ex) {
            System.out.println("### Error al verificar: " + ex.getMessage());
        }
    }
}
