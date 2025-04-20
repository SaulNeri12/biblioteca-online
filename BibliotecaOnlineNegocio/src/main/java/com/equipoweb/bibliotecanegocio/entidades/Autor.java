
package com.equipoweb.bibliotecanegocio.entidades;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Saul Neri
 */
@Entity
@Table(name="autor")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;
    
    @Column(name="nombre", nullable=false)
    @JsonProperty("nombre")
    private String nombre;
    
    @Column(name="biografia", nullable=false)
    @JsonProperty("biografica")
    private String biografia;
    
    @OneToMany(mappedBy = "autor")
    @JsonProperty("libros")
    private List<Libro> libros;

    public Autor() {
        
    }
    
    public Autor(Long id, String nombre, String biografia, List<Libro> libros) {
        this.id = id;
        this.nombre = nombre;
        this.biografia = biografia;
        this.libros = libros;
    }

    public Autor(String nombre, String biografia, List<Libro> libros) {
        this.nombre = nombre;
        this.biografia = biografia;
        this.libros = libros;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Autor)) {
            return false;
        }
        Autor other = (Autor) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Autor[id=%d, nombre=%s, biografia=%s, libros=%s]",
                this.id,
                this.nombre,
                this.biografia,
                this.libros
        );
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
     * @return the biografia
     */
    public String getBiografia() {
        return biografia;
    }

    /**
     * @param biografia the biografia to set
     */
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    /**
     * @return the libros
     */
    public List<Libro> getLibros() {
        return libros;
    }

    /**
     * @param libros the libros to set
     */
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

}
