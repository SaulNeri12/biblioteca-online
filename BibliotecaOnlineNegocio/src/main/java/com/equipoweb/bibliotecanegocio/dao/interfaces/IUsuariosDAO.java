/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao.interfaces;

import com.equipoweb.bibliotecanegocio.dao.excepciones.DAOException;
import com.equipoweb.bibliotecanegocio.entidades.Usuario;
import java.util.List;

/**
 * Interfaz de acceso a datos para realizar operaciones relacionadas con usuarios.
 * Provee métodos para obtener, registrar y autenticar usuarios en la base de datos.
 * 
 * @author neri
 */
public interface IUsuariosDAO {

    /**
     * Obtiene un usuario a partir de su identificador único.
     *
     * @param id el ID del usuario que se desea obtener.
     * @return el usuario correspondiente al ID proporcionado.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    Usuario obtenerUsuario(Long id) throws DAOException;

    /**
     * Busca usuarios cuyo nombre coincida parcial o totalmente con el texto proporcionado.
     *
     * @param nombreBusqueda el nombre o fragmento del nombre a buscar.
     * @return una lista de usuarios que coinciden con el criterio de búsqueda.
     * @throws DAOException si ocurre un error al acceder a los datos.
     */
    List<Usuario> obtenerUsuariosPorNombre(String nombreBusqueda) throws DAOException;

    /**
     * Registra un nuevo usuario en la base de datos.
     *
     * @param usuario el objeto Usuario que se desea registrar.
     * @throws DAOException si ocurre un error al registrar el usuario.
     */
    void registrarUsuario(Usuario usuario) throws DAOException;

    /**
     * Verifica si existen credenciales válidas para iniciar sesión con el email y la contraseña proporcionados.
     *
     * @param email el correo electrónico del usuario.
     * @param contrasena la contraseña del usuario.
     * @return true si las credenciales son correctas y se puede iniciar sesión, false en caso contrario.
     * @throws DAOException si ocurre un error al verificar las credenciales.
     */
    Usuario iniciarSesion(String email, String contrasena) throws DAOException;
    
    /**
     * Regresa una lista con todos los usuarios registrados en el sistema.
     * @return Devuelve la lista de usuarios encontrados
     * @throws DAOException si ocurre un error al buscar los usuarios.
     */
    List<Usuario> obtenerUsuariosTodos() throws DAOException;
}
