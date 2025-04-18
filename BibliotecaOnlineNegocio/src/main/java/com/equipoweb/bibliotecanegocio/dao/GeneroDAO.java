/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.equipoweb.bibliotecanegocio.dao;

import com.equipoweb.bibliotecanegocio.conexion.Conexion;
import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.dao.interfaces.IGeneroDAO;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import java.util.List;
import javax.persistence.EntityManager;



/**
 * Implementación de la interfaz {@link IGeneroDAO} para gestionar
 * operaciones relacionadas con los géneros de libros en la base de datos.
 * Usa JPA para interactuar con la base de datos.
 * 
 * @author neri
 */
class GeneroDAO implements IGeneroDAO {

    private static GeneroDAO _instancia;

    private GeneroDAO() {

    }

    public static GeneroDAO getInstance() {
        if (_instancia == null) {
            _instancia = new GeneroDAO();
        }

        return _instancia;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Genero> obtenerGenerosTodos() throws DAOException {
        EntityManager em = Conexion.getInstance().crearConexion();

        try {
            return em.createQuery("SELECT g FROM Genero g", Genero.class).getResultList();
        } catch (Exception e) {
            System.out.println("### ERROR DAO obtenerGenerosTodos: " + e.getMessage());
            throw new DAOException("Error al obtener la lista de géneros.");
        } finally {
            em.close();
        }
    }
}

