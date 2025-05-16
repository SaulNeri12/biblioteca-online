/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.ILibroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import com.equipoweb.bibliotecanegocio.entidades.Libro;
import java.time.LocalDate;
import java.util.ArrayList;
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
     * Busca libros por nombre, genero y nombre del autor, si alguno de los
     * parametros esta vacio no se considera para la busqueda.
     *
     * @param nombre Nombre del libro a buscar.
     * @param genero Genero del libro a buscar.
     * @param nombreAutor Nombre del autor del libro a buscar.
     * @return Lista de libros que coincidan en la busqueda.
     * @throws DAOException En caso de error en la busqueda.
     */
    @Override
    public List<Libro> buscarLibro(String nombre, String genero, String nombreAutor) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            StringBuilder jpql = new StringBuilder("SELECT DISTINCT l FROM Libro l ");
            jpql.append("JOIN l.autor a ");
            jpql.append("LEFT JOIN l.generos g ");
            jpql.append("WHERE 1=1 ");

            if (nombre != null && !nombre.trim().isEmpty()) {
                jpql.append("AND LOWER(l.nombre) LIKE LOWER(:nombre) ");
            }

            if (genero != null && !genero.trim().isEmpty()) {
                jpql.append("AND LOWER(g.nombre) = LOWER(:genero) ");
            }

            if (nombreAutor != null && !nombreAutor.trim().isEmpty()) {
                jpql.append("AND LOWER(a.nombre) LIKE LOWER(:nombreAutor) ");
            }

            TypedQuery<Libro> query = em.createQuery(jpql.toString(), Libro.class);

            if (nombre != null && !nombre.trim().isEmpty()) {
                query.setParameter("nombre", "%" + nombre + "%");
            }

            if (genero != null && !genero.trim().isEmpty()) {
                query.setParameter("genero", genero);
            }

            if (nombreAutor != null && !nombreAutor.trim().isEmpty()) {
                query.setParameter("nombreAutor", "%" + nombreAutor + "%");
            }

            query.setMaxResults(20);

            return query.getResultList();

        } catch (Exception e) {
            throw new DAOException("Error al buscar libros");
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
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

        // Validación de objeto libro
        if (libro == null) {
            throw new DAOException("El objeto Libro es nulo.");
        }

        // Validación de nombre (título)
        if (libro.getNombre() == null || libro.getNombre().isBlank()) {
            throw new DAOException("El título del libro es nulo o está vacío.");
        }

        // Validación de descripción
        if (libro.getDescripcion() == null || libro.getDescripcion().isBlank()) {
            throw new DAOException("La descripción del libro es nula o está vacía.");
        }

        // Validación de autor
        if (libro.getAutor() == null || libro.getAutor().getId() == null) {
            throw new DAOException("El autor del libro es nulo o inválido.");
        }

        if (libro.esContenidoAdulto() == null) {
            throw new DAOException("No se indico si el contenido es de tipo adulto o no.");
        }

        // Validación del año del libro
        Integer anioLibro = libro.getAnio();
        if (anioLibro == null) {
            throw new DAOException("El año del libro no puede ser nulo.");
        }
        if (anioLibro < 0) {
            throw new DAOException("El año del libro no puede ser negativo: " + anioLibro);
        }
        int anioActual = LocalDate.now().getYear();
        if (anioLibro > anioActual) {
            throw new DAOException("El año del libro no puede ser posterior al año actual (" + anioActual + "): " + anioLibro);
        }

        // Validación de fecha de publicación (si existe campo LocalDate fechaPublicacion)
        if (libro.getAnio() != null) {
            if (libro.getAnio() > LocalDate.now().getYear()) {
                throw new DAOException("El año de publicación no puede ser posterior al año actual.");
            }
        }

        // Validación del número de páginas
        Integer numPaginas = libro.getNumPaginas();
        if (numPaginas == null) {
            throw new DAOException("El número de páginas no puede ser nulo.");
        }

        // (según requisito: no superar el año actual)
        if (numPaginas < 1) {
            throw new DAOException("El número de páginas del libro no puede ser menor a 1");
        }

        List<Genero> generos = libro.getGeneros();

        if (generos == null || generos.isEmpty()) {
            throw new DAOException("La lista de generos de esta vacia.");
        }

        int numGeneros = generos.size();

        if (numGeneros == 2) {
            boolean generosIguales = generos.get(0).getId().equals(generos.get(1).getId());
            if (generosIguales) {
                throw new DAOException("Los generos del libro no pueden ser iguales.");
            }
        }

        EntityManager entityManager = Conexion.getInstance().crearConexion();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            // verificamos que existan los generos
            List<Genero> generosGestionados = new ArrayList<>();
            for (Genero genero : libro.getGeneros()) {
                Genero g = entityManager.find(Genero.class, genero.getId());
                if (g == null) {
                    throw new DAOException("El género con ID " + genero.getId() + " no existe.");
                }
                generosGestionados.add(g);
            }
            
            libro.setGeneros(generosGestionados);

            Long idAutor = libro.getAutor().getId();
                    
            boolean autorExiste = entityManager.createQuery("SELECT COUNT(1) FROM Autor a WHERE a.id = :idAutor", Long.class)
                    .setParameter("idAutor", idAutor)
                    .getSingleResult() > 0;

            if (!autorExiste) {
                throw new DAOException("El autor del libro no existe.");
            }

            // checar si ya existe un libro con ese isbn
            String isbn = libro.getIsbn();
                    
            boolean isbnExiste = entityManager.createQuery("SELECT COUNT(1) FROM Libro l WHERE l.isbn = :isbn", Long.class)
                    .setParameter("isbn", isbn)
                    .getSingleResult() > 0;

            if (isbnExiste) {
                throw new DAOException(String.format("Ya existe un libro con el ISBN especificado: %s", isbn));
            }
            
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
            throw new DAOException("Error al registrar el libro, por favor intente más tarde.");
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
