/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.equipoweb.bibliotecanegocio.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Saul Neri
 */
@Entity
@Table(name = "libro")
public class Libro implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @Column(name = "isbn", unique = true, nullable = false)
    @JsonProperty("isbn")
    private String isbn;

    @Column(name = "nombre", nullable = false)
    @JsonProperty("nombre")
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    @JsonProperty("descripcion")
    private String descripcion;

    @Column(name = "contenido_adulto", nullable = false)
    @JsonProperty("contenido_adulto")
    private Boolean contenidoAdulto;

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    @JsonProperty("autor")
    private Autor autor;

    @Column(name = "editorial", nullable = false)
    @JsonProperty("editorial")
    private String editorial;

    @Column(name = "anio_publicacion", nullable = false)
    @JsonProperty("anio_publicacion")
    private Integer anio;

    @Column(name = "num_paginas", nullable = false)
    @JsonProperty("num_paginas")
    private Integer numPaginas;

    @ManyToMany
    @JoinTable(
            name = "libro_genero",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    private List<Genero> generos;

    public Libro() {

    }

    public Libro(String nombre, String descripcion, Autor autor, String editorial, Integer anio, Integer numPaginas) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.numPaginas = numPaginas;
    }

    public Libro(String isbn, String nombre, String descripcion, Autor autor, String editorial, Integer anio, Integer numPaginas, List<Genero> generos) {
        this.isbn = isbn;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.numPaginas = numPaginas;
        this.generos = generos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Libro)) {
            return false;
        }
        Libro other = (Libro) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Libro[id=%d, nombre=%s, descripcion=%s, autor=%s, anio=%d, num_paginas=%d, +18=%s, generos=%s",
                this.id,
                this.nombre,
                this.descripcion,
                this.autor,
                this.anio,
                this.numPaginas,
                this.contenidoAdulto,
                this.generos
        );
    }

    /**
     * @return the isbn
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * @param isbn the isbn to set
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the autor
     */
    public Autor getAutor() {
        return autor;
    }

    /**
     * @param autor the autor to set
     */
    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    /**
     * @return the editorial
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * @param editorial the editorial to set
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * @return the anio
     */
    public Integer getAnio() {
        return anio;
    }

    /**
     * @param anio the anio to set
     */
    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    /**
     * @return the numPaginas
     */
    public Integer getNumPaginas() {
        return numPaginas;
    }

    /**
     * @param numPaginas the numPaginas to set
     */
    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

    /**
     * @return the generos
     */
    public List<Genero> getGeneros() {
        return generos;
    }

    /**
     * @param generos the generos to set
     */
    public void setGeneros(List<Genero> generos) {
        this.generos = generos;
    }

    /**
     * @return the contenidoAdulto
     */
    public Boolean esContenidoAdulto() {
        return contenidoAdulto;
    }

    /**
     * @param contenidoAdulto the contenidoAdulto to set
     */
    public void setContenidoAdulto(Boolean contenidoAdulto) {
        this.contenidoAdulto = contenidoAdulto;
    }

}
