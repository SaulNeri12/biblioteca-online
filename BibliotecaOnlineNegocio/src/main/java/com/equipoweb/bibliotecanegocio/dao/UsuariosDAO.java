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

    public List<Usuario> obtenerUsuariosTodos() throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u",
                    Usuario.class
            );

            return query.getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerUsuariosTodos: " + e.getMessage());
            throw new DAOException("Error al buscar los usuarios registrados.");
        } finally {
            em.close();
        }
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

            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                throw new DAOException("El nombre es obligatorio.");
            }

            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
                throw new DAOException("El correo electrónico es obligatorio.");
            }

            if (!usuario.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                throw new DAOException("El formato del correo electrónico no es válido.");
            }

            if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
                throw new DAOException("El teléfono es obligatorio.");
            }

            if (!usuario.getTelefono().matches("^\\d{10}$")) {
                throw new DAOException("El teléfono debe tener 10 dígitos.");
            }

            if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
                throw new DAOException("La contraseña es obligatoria.");
            }

            if (usuario.getContrasena().length() < 6) {
                throw new DAOException("La contraseña debe tener al menos 6 caracteres.");
            }

            if (usuario.getFechaNacimiento() == null) {
                throw new DAOException("La fecha de nacimiento es obligatoria.");
            }

            // Validar si ya existe un usuario con el mismo email
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email OR u.telefono = :telefono", Usuario.class);
            query.setParameter("email", usuario.getEmail());
            query.setParameter("telefono", usuario.getTelefono());

            if (!query.getResultList().isEmpty()) {
                throw new DAOException("El correo o teléfono ya está en uso.");
            }

            em.getTransaction().begin();
            em.persist(usuario);
            em.getTransaction().commit();
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DAOException(e.getMessage());
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
    public Usuario iniciarSesion(String email, String contrasena) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.email = :email AND u.contrasena = :contrasena",
                    Usuario.class
            );
            query.setParameter("email", email);
            query.setParameter("contrasena", contrasena);

            Usuario usuario = query.getSingleResult();
            return usuario;
        } catch (NoResultException e) {
            System.out.println(e.getMessage());
            throw new DAOException("El correo o la contraseña son incorrectos.");
        } catch (Exception e) {
            System.out.println("### ERROR DAO iniciarSesion: " + e.getMessage());
            throw new DAOException("Error al iniciar sesión.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean actualizarUsuario(Usuario usuario) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            Usuario usuarioExistente = em.find(Usuario.class, usuario.getId());

            if (usuarioExistente == null) {
                throw new DAOException("El usuario no existe.");
            }

            // Validaciones
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
                throw new DAOException("El nombre es obligatorio.");
            }

            if (usuario.getTelefono() == null || usuario.getTelefono().trim().isEmpty()) {
                throw new DAOException("El teléfono es obligatorio.");
            }

            if (!usuario.getTelefono().matches("^\\d{10}$")) {
                throw new DAOException("El teléfono debe tener 10 dígitos.");
            }

            if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
                throw new DAOException("La contraseña es obligatoria.");
            }

            if (usuario.getContrasena().length() < 6) {
                throw new DAOException("La contraseña debe tener al menos 6 caracteres.");
            }

            // Comprobar si el nuevo teléfono ya lo tiene otro usuario
            TypedQuery<Usuario> query = em.createQuery(
                    "SELECT u FROM Usuario u WHERE u.telefono = :telefono AND u.id <> :id",
                    Usuario.class
            );
            query.setParameter("telefono", usuario.getTelefono());
            query.setParameter("id", usuario.getId());

            if (!query.getResultList().isEmpty()) {
                throw new DAOException("El teléfono ya está en uso por otro usuario.");
            }

            // Solo se modifican los campos permitidos
            em.getTransaction().begin();
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setContrasena(usuario.getContrasena());
            em.getTransaction().commit();

            return true;
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("### ERROR DAO actualizarUsuario: " + e.getMessage());
            throw new DAOException("Error al actualizar el usuario.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminarUsuario(Long id) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            Usuario usuario = em.find(Usuario.class, id);

            if (usuario == null) {
                throw new DAOException("El usuario no existe.");
            }

            em.getTransaction().begin();
            em.remove(usuario);
            em.getTransaction().commit();

            return true;
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("### ERROR DAO eliminarUsuario: " + e.getMessage());
            throw new DAOException("Error al eliminar el usuario.");
        } finally {
            em.close();
        }
    }
}
