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
/**
 * Clase DAO para la entidad Administrador.
 * Implementa la interfaz IAdministradorDAO y proporciona métodos para acceder y manipular los datos de los administradores en la base de datos.
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
    /**
     * Inicia la sesión de un administrador.
     * @param correo Correo electrónico del administrador.
     * @param contrasena Contraseña del administrador.
     * @return El objeto Administrador si las credenciales son correctas, null si no lo son.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
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
            throw new DAOException("El correo o la contraseña son incorrectos, por favor ingrese las credenciales correctas.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new DAOException("Ha ocurrido un error, por favor intente mas tarde.");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }


    
    
    
}
