/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IUsuariosDAO;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementación de la interfaz IUsuariosDAO utilizando JPA y EntityManager. Se
 * encarga de las operaciones CRUD y de autenticación para la entidad Usuario.
 * Usa una conexión singleton proporcionada por la clase Conexion.
 *
 * @author neri
 */
class UsuariosDAO implements IUsuariosDAO {

    private static UsuariosDAO _instancia;

    private UsuariosDAO() {

    }

    public static UsuariosDAO getInstance() {
        if (_instancia == null) {
            _instancia = new UsuariosDAO();
        }

        return _instancia;
    }

    @Override
    public Usuario obtenerUsuario(Long id) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            return em.find(Usuario.class, id);
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerUsuario: " + e.getMessage());
            throw new DAOException("Error al obtener usuario por ID.");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Usuario> obtenerUsuariosPorNombre(String nombreBusqueda) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))",
                    Usuario.class
            );
            query.setParameter("nombre", nombreBusqueda);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerUsuariosPorNombre: " + e.getMessage());
            throw new DAOException("Error al buscar usuarios por nombre.");
        } finally {
            em.close();
        }
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {

            // Validar si ya existe un usuario con el mismo email
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
            query.setParameter("email", usuario.getEmail());

            if (!query.getResultList().isEmpty()) {
                throw new DAOException("Ya existe un usuario registrado con ese email.");
            }

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("### ERROR DAO registrarUsuario: " + e.getMessage());
            throw new DAOException("Error al registrar usuario.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean iniciarSesion(String email, String contrasena) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email AND u.contrasena = :contrasena",
                    Usuario.class
            );
            query.setParameter("email", email);
            query.setParameter("contrasena", contrasena);

            Usuario usuario = query.getSingleResult();
            return usuario != null;
        } catch (NoResultException e) {
            throw new DAOException("El correo o la contraseña son incorrectos.");
        } catch (Exception e) {
            System.out.println("### ERROR DAO iniciarSesion: " + e.getMessage());
            throw new DAOException("Error al iniciar sesión.");
        } finally {
            em.close();
        }
    }
}
