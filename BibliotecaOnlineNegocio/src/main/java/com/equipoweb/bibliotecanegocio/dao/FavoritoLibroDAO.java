/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IFavoritoLibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.FavoritoLibro;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;

class FavoritoLibroDAO implements IFavoritoLibroDAO {

    private static FavoritoLibroDAO _instancia;

    private FavoritoLibroDAO() {

    }

    public static FavoritoLibroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FavoritoLibroDAO();
        }

        return _instancia;
    }
    
    @Override
    public List<FavoritoLibro> obtenerFavoritos(Long idUsuario) throws DAOException {
        if (idUsuario == null || idUsuario <= 0) {
            throw new DAOException("El ID de usuario no es válido.");
        }

        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            TypedQuery<FavoritoLibro> query = em.createQuery(
                    "SELECT f FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario",
                    FavoritoLibro.class
            );
            query.setParameter("idUsuario", idUsuario);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerFavoritos: " + e.getMessage());
            throw new DAOException("Error al obtener los libros favoritos del usuario.");
        } finally {
            em.close();
        }
    }

    @Override
    public void asignarFavorito(Long idUsuario, Long idLibro) throws DAOException {
        if (idUsuario == null || idUsuario <= 0 || idLibro == null || idLibro <= 0) {
            throw new DAOException("ID de usuario o libro inválido.");
        }

        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, idUsuario);
            Libro libro = em.find(Libro.class, idLibro);

            if (usuario == null || libro == null) {
                throw new DAOException("Usuario o libro no encontrado.");
            }

            // Verificar si ya existe un registro con este usuario y este libro
            TypedQuery<FavoritoLibro> query = em.createQuery(
                    "SELECT f FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario AND f.libro.id = :idLibro",
                    FavoritoLibro.class
            );
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idLibro", idLibro);

            boolean yaExiste = !query.getResultList().isEmpty();
            if (yaExiste) {
                throw new DAOException("El libro ya está marcado como favorito por este usuario.");
            }

            // Crear nuevo favorito
            FavoritoLibro favorito = new FavoritoLibro();
            favorito.setUsuario(usuario);
            favorito.setLibro(libro);

            em.persist(favorito);
            em.getTransaction().commit();
        } catch (DAOException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("### ERROR DAO asignarFavorito: " + e.getMessage());
            throw new DAOException("Error al asignar libro favorito.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean eliminarFavorito(Long idUsuario, Long idLibro) throws DAOException {
        if (idUsuario == null || idUsuario <= 0 || idLibro == null || idLibro <= 0) {
            throw new DAOException("ID de usuario o libro inválido.");
        }

        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            em.getTransaction().begin();

            TypedQuery<FavoritoLibro> query = em.createQuery(
                    "SELECT f FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario AND f.libro.id = :idLibro",
                    FavoritoLibro.class
            );
            query.setParameter("idUsuario", idUsuario);
            query.setParameter("idLibro", idLibro);

            FavoritoLibro favorito = query.getResultStream().findFirst().orElse(null);

            if (favorito == null) {
                em.getTransaction().rollback();
                return false;
            }

            em.remove(favorito);
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.out.println("### ERROR DAO eliminarFavorito: " + e.getMessage());
            throw new DAOException("Error al eliminar el libro favorito.");
        } finally {
            em.close();
        }
    }

}
