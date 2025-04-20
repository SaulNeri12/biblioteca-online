/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.equipoweb.bibliotecanegocio;

import com.equipoweb.bibliotecanegocio.dao.FabricaAdministradorDAO;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAdministradorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Administrador;

/**
 *
 * @author skevi
 */
public class PruebaInicioSesion {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        IAdministradorDAO adminDAO = FabricaAdministradorDAO.getInstance().crearDAO();

        // Prueba con credenciales válidas
        String correo = "admin@example";
        String contrasena = "123456"; // Asegúrate de que este usuario exista en la base de datos

        try {
            Administrador admin = adminDAO.iniciarSesion(correo, contrasena);

            if (admin != null) {
                System.out.println("Inicio de sesión exitoso.");
                System.out.println("ID: " + admin.getId());
                System.out.println("Correo: " + admin.getCorreo());
            } else {
                System.out.println("Credenciales inválidas. No se pudo iniciar sesión.");
            }

        } catch (DAOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
}
