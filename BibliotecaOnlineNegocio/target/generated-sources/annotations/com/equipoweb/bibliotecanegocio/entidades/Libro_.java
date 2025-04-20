package com.equipoweb.bibliotecanegocio.entidades;

import com.equipoweb.bibliotecanegocio.entidades.Autor;
import com.equipoweb.bibliotecanegocio.entidades.Genero;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-04-19T18:12:04", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Libro.class)
public class Libro_ { 

    public static volatile SingularAttribute<Libro, String> descripcion;
    public static volatile SingularAttribute<Libro, String> editorial;
    public static volatile SingularAttribute<Libro, String> isbn;
    public static volatile ListAttribute<Libro, Genero> generos;
    public static volatile SingularAttribute<Libro, Boolean> contenidoAdulto;
    public static volatile SingularAttribute<Libro, Long> id;
    public static volatile SingularAttribute<Libro, String> nombre;
    public static volatile SingularAttribute<Libro, Integer> numPaginas;
    public static volatile SingularAttribute<Libro, Autor> autor;
    public static volatile SingularAttribute<Libro, Integer> anio;

}