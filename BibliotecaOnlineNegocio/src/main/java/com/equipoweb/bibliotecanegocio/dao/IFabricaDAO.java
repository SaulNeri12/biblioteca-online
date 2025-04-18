/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.dao;

/**
 * Interfaz genérica para la creación de objetos DAO (Data Access Object).
 * Implementa el patrón de fábrica abstracta, proporcionando un método para instanciar
 * objetos DAO sin acoplarse a una implementación específica.
 *
 * @param <T> el tipo de objeto DAO que será creado.
 *
 * @author neri
 */
public interface IFabricaDAO<T> {

    /**
     * Crea e instancia un objeto DAO del tipo especificado.
     *
     * @return una nueva instancia de un objeto DAO de tipo {@code T}.
     */
    T crearDAO();
}
