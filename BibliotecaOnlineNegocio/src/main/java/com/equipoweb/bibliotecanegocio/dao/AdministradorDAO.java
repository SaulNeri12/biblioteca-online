/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAdministradorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Administrador;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

/**
 * Clase de acceso de datos para la entidad de Administrador, define las 
 * funciones de acceso a los datos a su tabla en la base de datos.
 * 
 * @author skevi
 */
class AdministradorDAO implements IAdministradorDAO {

    /**
     * Instancia global privada de la clase.
     */
    private static AdministradorDAO _instancia;

    /**
     * Constructor privado para evitar instacias externas.
     */
    private AdministradorDAO() {

    }

    /**
     * Metodo para la obtencion de la instancia global.
     * @return Instancia global de la clase.
     */
    public static AdministradorDAO getInstance() {
        if (_instancia == null) {
            _instancia = new AdministradorDAO();
        }

        return _instancia;
    }
    
    /**
     * Verifica si existen credenciales válidas dentro de la tabla de 
     * administradores para iniciar sesión con el email y la contraseña 
     * proporcionadas.
     *
     * @param correo el correo electrónico del usuario.
     * @param contrasena la contraseña del usuario.
     * @return true si las credenciales son correctas y se puede iniciar sesión, false en caso contrario.
     * @throws DAOException si ocurre un error al verificar las credenciales.
     */
    @Override
    public Administrador iniciarSesion(String correo, String contrasena) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            TypedQuery<Administrador> query = em.createQuery(
                "SELECT a FROM Administrador a WHERE a.correo = :correo AND a.contrasena = :contrasena",
                Administrador.class
            );
            
            query.setParameter("correo", correo);
            query.setParameter("contrasena", contrasena);

            return query.getSingleResult(); // credenciales correctas
        } catch (NoResultException e) {
            throw new DAOException("Error en correo o contraseña");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DAOException("Error en correo o contraseña");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    
    
    
}
