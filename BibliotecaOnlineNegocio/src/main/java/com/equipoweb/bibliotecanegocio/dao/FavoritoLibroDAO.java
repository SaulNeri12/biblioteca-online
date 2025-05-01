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
import javax.persistence.EntityManager;
import javax.persistence.NoResultException; // Para manejar libro no encontrado por ISBN
import javax.persistence.TypedQuery;

// Nota: Se asume que la entidad Libro tiene un campo 'isbn' que es único.
// y la entidad FavoritoLibro tiene campos 'usuario' (tipo Usuario) y 'libro' (tipo Libro)

class FavoritoLibroDAO implements IFavoritoLibroDAO {

    private static FavoritoLibroDAO _instancia;

    private FavoritoLibroDAO() { }

    public static FavoritoLibroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new FavoritoLibroDAO();
        }
        return _instancia;
    }

    /**
     * Busca un libro por su ISBN. Método auxiliar.
     * @param em EntityManager activo.
     * @param isbn ISBN a buscar.
     * @return El objeto Libro encontrado.
     * @throws NoResultException si no se encuentra el libro.
     */
    private Libro encontrarLibroPorIsbn(EntityManager em, String isbn) throws NoResultException {
         TypedQuery<Libro> query = em.createQuery(
                 "SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class);
         query.setParameter("isbn", isbn);
         // Se asume que el ISBN es único, getSingleResult es apropiado.
         return query.getSingleResult();
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
            System.err.println("### ERROR DAO obtenerFavoritos: " + e.getMessage());
            // Loguear stack trace para depuración interna
             e.printStackTrace();
            throw new DAOException("Error al obtener las relaciones de favoritos del usuario.");
        } finally {
            if (em != null && em.isOpen()) {
                 em.close();
            }
        }
    }

    // NUEVO METODO IMPLEMENTADO
    @Override
    public List<Libro> obtenerLibrosFavoritosPorUsuario(Long idUsuario) throws DAOException {
         if (idUsuario == null || idUsuario <= 0) {
            throw new DAOException("El ID de usuario no es válido.");
        }
        EntityManager em = Conexion.getInstance().crearConexion();
         try {
             TypedQuery<Libro> query = em.createQuery(
                 // Selecciona el objeto Libro (f.libro) desde FavoritoLibro (f)
                 // donde el id del usuario asociado (f.usuario.id) coincida.
                 "SELECT f.libro FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario",
                 Libro.class
             );
             query.setParameter("idUsuario", idUsuario);
             return query.getResultList();
         } catch (Exception e) {
             System.err.println("### ERROR DAO obtenerLibrosFavoritosPorUsuario: " + e.getMessage());
             e.printStackTrace();
             throw new DAOException("Error al obtener los libros favoritos del usuario.");
         } finally {
             if (em != null && em.isOpen()) {
                 em.close();
             }
         }
    }


    @Override
    public void asignarFavorito(Long idUsuario, String isbn) throws DAOException { // PARAMETRO CAMBIADO
        if (idUsuario == null || idUsuario <= 0 || isbn == null || isbn.isBlank()) {
            throw new DAOException("ID de usuario o ISBN inválido.");
        }

        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            em.getTransaction().begin();

            Usuario usuario = em.find(Usuario.class, idUsuario);
            if (usuario == null) {
                throw new DAOException("Usuario con ID " + idUsuario + " no encontrado.");
            }

            // Buscar libro por ISBN en lugar de ID
            Libro libro;
            try {
                 libro = encontrarLibroPorIsbn(em, isbn);
            } catch (NoResultException e) {
                 throw new DAOException("Libro con ISBN " + isbn + " no encontrado.");
            }

            // Usar el ID del libro encontrado para la verificación y creación
            Long idLibro = libro.getId();

            // Verificar si ya existe un registro con este usuario y este libro
            TypedQuery<Long> queryExistencia = em.createQuery(
                    "SELECT COUNT(f) FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario AND f.libro.id = :idLibro",
                    Long.class
            );
            queryExistencia.setParameter("idUsuario", idUsuario);
            queryExistencia.setParameter("idLibro", idLibro); // Usar el ID obtenido

            boolean yaExiste = queryExistencia.getSingleResult() > 0;
            if (yaExiste) {
                // Considerar no lanzar excepción, simplemente no hacer nada si ya existe.
                // O mantener la excepción si se quiere notificar explícitamente.
                 em.getTransaction().rollback(); // Importante hacer rollback antes de lanzar excepción
                throw new DAOException("El libro ya está marcado como favorito por este usuario.");
            }

            // Crear nuevo favorito
            FavoritoLibro favorito = new FavoritoLibro();
            favorito.setUsuario(usuario);
            favorito.setLibro(libro); // Usar el objeto Libro encontrado

            em.persist(favorito);
            em.getTransaction().commit();
        } catch (DAOException e) { // Re-lanzar DAOException específicas
             if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
         catch (Exception e) { // Capturar otras excepciones (JPA, etc.)
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("### ERROR DAO asignarFavorito: " + e.getMessage());
             e.printStackTrace();
            // Envolver en DAOException para mantener consistencia en la capa superior
            throw new DAOException("Error al asignar libro favorito: " + e.getMessage());
        } finally {
             if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean eliminarFavorito(Long idUsuario, String isbn) throws DAOException { // PARAMETRO CAMBIADO
        if (idUsuario == null || idUsuario <= 0 || isbn == null || isbn.isBlank()) {
            throw new DAOException("ID de usuario o ISBN inválido.");
        }

        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            em.getTransaction().begin();

            // Buscar libro por ISBN para obtener su ID interno
            Libro libro;
             try {
                 libro = encontrarLibroPorIsbn(em, isbn);
            } catch (NoResultException e) {
                // Si el libro no existe, el favorito tampoco puede existir.
                em.getTransaction().rollback();
                 return false; // Indicar que no se eliminó porque el libro base no existe.
            }

            Long idLibro = libro.getId();

            // Buscar la entidad FavoritoLibro específica para eliminarla
            TypedQuery<FavoritoLibro> queryBusqueda = em.createQuery(
                    "SELECT f FROM FavoritoLibro f WHERE f.usuario.id = :idUsuario AND f.libro.id = :idLibro",
                    FavoritoLibro.class
            );
            queryBusqueda.setParameter("idUsuario", idUsuario);
            queryBusqueda.setParameter("idLibro", idLibro); // Usar ID obtenido

            FavoritoLibro favorito;
            try {
                favorito = queryBusqueda.getSingleResult();
            } catch (NoResultException e) {
                // El favorito no existe para este usuario y libro
                em.getTransaction().rollback();
                return false; // No se encontró nada para eliminar
            }

            // Si se encontró, eliminarlo
            em.remove(favorito);
            em.getTransaction().commit();
            return true; // Eliminación exitosa

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("### ERROR DAO eliminarFavorito: " + e.getMessage());
            e.printStackTrace();
            throw new DAOException("Error al eliminar el libro favorito: " + e.getMessage());
        } finally {
             if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}