package com.equipoweb.bibliotecanegocio.entidades;

import com.equipoweb.bibliotecanegocio.entidades.Libro;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2025-04-19T22:15:02", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Genero.class)
public class Genero_ { 

    public static volatile ListAttribute<Genero, Libro> libros;
    public static volatile SingularAttribute<Genero, Long> id;
    public static volatile SingularAttribute<Genero, String> nombre;

}