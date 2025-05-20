package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IAutorDAO;
import com.equipoweb.bibliotecanegocio.entidades.Autor;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Clase de acceso a datos para la entidad Autor.
 */
class AutorDAO implements IAutorDAO {

    private static AutorDAO _instancia;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private AutorDAO() {

    }

    /**
     * Obtiene la instancia de la clase (Singleton).
     * @return La instancia de la clase.
     */
    public static AutorDAO getInstance() {
        if (_instancia == null) {
            _instancia = new AutorDAO();
        }

        return _instancia;
    }
    
    /**
     * Obtiene todos los autores de la base de datos.
     * @return Una lista con todos los autores.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public List<Autor> obtenerAutoresTodos() throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            // Consulta para obtener todos los autores
            TypedQuery<Autor> query = em.createQuery("SELECT a FROM Autor a", Autor.class);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerAutoresTodos: " + e.getMessage());
            throw new DAOException("Error al obtener los autores.");
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene los autores que coinciden con el nombre de búsqueda.
     * @param nombreBusqueda El nombre a buscar.
     * @return Una lista con los autores que coinciden con el nombre de búsqueda.
     * @throws DAOException Si ocurre un error al acceder a la base de datos.
     */
    @Override
    public List<Autor> obtenerAutoresPorNombre(String nombreBusqueda) throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();
        try {
            // Consulta para obtener autores por nombre
            String jpql = "SELECT a FROM Autor a WHERE a.nombre LIKE :nombre";
            TypedQuery<Autor> query = em.createQuery(jpql, Autor.class);
            query.setParameter("nombre", "%" + nombreBusqueda + "%");
            return query.getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerAutoresPorNombre: " + e.getMessage());
            throw new DAOException("Error al obtener los autores por nombre.");
        } finally {
            em.close();
        }
    }
}
