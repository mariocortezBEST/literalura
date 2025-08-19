package com.literalura.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

/**
 * Entidad JPA que representa un Autor en la base de datos
 * Mapea la tabla 'autores' en PostgreSQL
 */
@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del autor no puede estar vacío")
    @Size(max = 255, message = "El nombre no puede exceder 255 caracteres")
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "ano_nacimiento")
    private Integer anoNacimiento;

    @Column(name = "ano_fallecimiento")
    private Integer anoFallecimiento;

    // Relación uno a muchos con Libro
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Libro> libros;

    // Constructor por defecto (requerido por JPA)
    public Autor() {}

    // Constructor con parámetros
    public Autor(String nombre, Integer anoNacimiento, Integer anoFallecimiento) {
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
        this.anoFallecimiento = anoFallecimiento;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(Integer anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Métodos de utilidad

    /**
     * Verifica si el autor estaba vivo en un año específico
     * @param ano Año a verificar
     * @return true si estaba vivo, false si no
     */
    public boolean estabaVivoEn(int ano) {
        // Si no hay año de nacimiento, no podemos determinar
        if (anoNacimiento == null) {
            return false;
        }

        // Si nació después del año consultado, no estaba vivo
        if (anoNacimiento > ano) {
            return false;
        }

        // Si no hay año de fallecimiento, se asume que está vivo
        if (anoFallecimiento == null) {
            return true;
        }

        // Estaba vivo si el año está entre nacimiento y fallecimiento
        return anoFallecimiento >= ano;
    }

    /**
     * Obtiene la información de vida del autor como string
     * @return String con años de vida
     */
    public String getInfoVida() {
        if (anoNacimiento == null && anoFallecimiento == null) {
            return "Fechas desconocidas";
        }

        String nacimiento = anoNacimiento != null ? anoNacimiento.toString() : "?";
        String fallecimiento = anoFallecimiento != null ? anoFallecimiento.toString() : "Presente";

        return String.format("(%s - %s)", nacimiento, fallecimiento);
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(id, autor.id) &&
                Objects.equals(nombre, autor.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre);
    }

    @Override
    public String toString() {
        return String.format("Autor{id=%d, nombre='%s', años=%s, libros=%d}",
                id, nombre, getInfoVida(),
                libros != null ? libros.size() : 0);
    }
}