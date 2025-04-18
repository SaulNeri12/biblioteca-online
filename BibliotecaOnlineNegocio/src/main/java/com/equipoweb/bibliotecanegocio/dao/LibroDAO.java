/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.FavoritoLibro;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import javax.persistence.*;
import java.util.List;

import javax.persistence.*;
import java.util.List;

import javax.persistence.*;
import java.util.List;

/**
 * Implementación del DAO para gestionar las operaciones CRUD de la entidad
 * Libro en la base de datos. Utiliza la clase Conexion para gestionar las
 * conexiones a la base de datos.
 *
 * @author Saul Neri
 */
class LibroDAO implements ILibroDAO {

    private static LibroDAO _instancia;

    private LibroDAO() {

    }

    public static LibroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new LibroDAO();
        }

        return _instancia;
    }

    /**
     * Obtiene todos los libros almacenados en la base de datos.
     *
     * @return una lista con todos los libros.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    @Override
    public List<Libro> obtenerLibrosTodos() throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();

        try {

            TypedQuery<Libro> query = entityManager.createQuery("SELECT l FROM Libro l", Libro.class);
            List<Libro> libros = query.getResultList();

            return libros;

        } catch (Exception e) {
            System.out.println("### ERROR DAO OBTENER TODOS LOS LIBROS: " + e.getMessage());
            throw new DAOException("Error al obtener todos los libros.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Obtiene una lista de libros que pertenecen a los géneros especificados.
     *
     * @param generos una lista de nombres de géneros por los que se desea
     * filtrar.
     * @return una lista de libros que coinciden con los géneros dados.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    @Override
    public List<Libro> obtenerLibrosPorGeneros(List<String> generos) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();

        try {

            TypedQuery<Libro> query = entityManager.createQuery(
                    "SELECT l FROM Libro l JOIN l.generos g WHERE g.nombre IN :generos", Libro.class);
            query.setParameter("generos", generos);
            List<Libro> libros = query.getResultList();

            return libros;

        } catch (Exception e) {
            System.out.println("### ERROR DAO OBTENER LIBROS POR GENEROS: " + e.getMessage());
            throw new DAOException("Error al obtener libros por géneros.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Busca libros cuyo nombre contenga el texto proporcionado (búsqueda
     * parcial o completa).
     *
     * @param nombreBusqueda el texto a buscar en los nombres de los libros.
     * @return una lista de libros cuyos nombres coincidan parcial o totalmente
     * con el texto dado.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    @Override
    public List<Libro> obtenerLibrosPorNombre(String nombreBusqueda) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();

        try {

            TypedQuery<Libro> query = entityManager.createQuery(
                    "SELECT l FROM Libro l WHERE l.nombre LIKE :nombreBusqueda", Libro.class);
            query.setParameter("nombreBusqueda", "%" + nombreBusqueda + "%");
            List<Libro> libros = query.getResultList();

            return libros;

        } catch (Exception e) {
            System.out.println("### ERROR DAO OBTENER LIBROS POR NOMBRE: " + e.getMessage());
            throw new DAOException("Error al obtener libros por nombre.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Registra un nuevo libro en la base de datos.
     *
     * @param libro el libro a registrar.
     * @throws DAOException si ocurre un error al registrar el libro.
     */
    @Override
    public void registrarLibro(Libro libro) throws DAOException {
        if (libro == null || libro.getNombre() == null || libro.getNombre().isBlank() || libro.getAutor() == null) {
            throw new DAOException("El libro o sus datos obligatorios son nulos o inválidos.");
        }

        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            boolean yaExiste = entityManager.createQuery(
                    "SELECT COUNT(l) FROM Libro l WHERE LOWER(l.nombre) = :nombre AND l.autor.id = :idAutor", Long.class)
                    .setParameter("nombre", libro.getNombre().toLowerCase())
                    .setParameter("idAutor", libro.getAutor().getId())
                    .getSingleResult() > 0;

            if (yaExiste) {
                throw new DAOException("Ya existe un libro con el mismo título y autor.");
            }

            entityManager.persist(libro);
            entityManager.flush();

            transaction.commit();
        } catch (DAOException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Propaga el DAOException tal cual
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("### ERROR DAO REGISTRAR LIBRO: " + e.getMessage());
            throw new DAOException("Error al registrar el libro.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Actualiza la información de un libro existente en la base de datos.
     *
     * @param libro el libro con los datos actualizados.
     * @throws DAOException si ocurre un error al actualizar el libro.
     */
    @Override
    public void actualizarLibro(Libro libro) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            entityManager.merge(libro);
            entityManager.flush();  // Asegura que los cambios se persistan inmediatamente

            transaction.commit();

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("### ERROR DAO ACTUALIZAR LIBRO: " + e.getMessage());
            throw new DAOException("Error al actualizar el libro.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un libro de la base de datos a partir de su identificador único
     * (ID).
     *
     * @param id el ID del libro a eliminar.
     * @return true si el libro fue eliminado correctamente, false en caso
     * contrario.
     * @throws DAOException si ocurre un error al eliminar el libro.
     */
    @Override
    public boolean eliminarLibro(Long id) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Libro libro = entityManager.find(Libro.class, id);
            if (libro != null) {
                entityManager.remove(libro);
                entityManager.flush();  // Asegura que los cambios se persistan inmediatamente
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("### ERROR DAO ELIMINAR LIBRO POR ID: " + e.getMessage());
            throw new DAOException("Error al eliminar el libro por ID.");
        } finally {
            entityManager.close();
        }
    }

    /**
     * Elimina un libro de la base de datos a partir de su código ISBN.
     *
     * @param isbn el ISBN del libro a eliminar.
     * @return true si el libro fue eliminado correctamente, false en caso
     * contrario.
     * @throws DAOException si ocurre un error al eliminar el libro.
     */
    @Override
    public boolean eliminarLibro(String isbn) throws DAOException {
        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            TypedQuery<Libro> query = entityManager.createQuery("SELECT l FROM Libro l WHERE l.isbn = :isbn", Libro.class);
            query.setParameter("isbn", isbn);
            List<Libro> libros = query.getResultList();
            if (!libros.isEmpty()) {
                entityManager.remove(libros.get(0));
                entityManager.flush();  // Asegura que los cambios se persistan inmediatamente
                transaction.commit();
                return true;
            }
            transaction.rollback();
            return false;

        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("### ERROR DAO ELIMINAR LIBRO POR ISBN: " + e.getMessage());
            throw new DAOException("Error al eliminar el libro por ISBN.");
        } finally {
            entityManager.close();
        }
    }
}
