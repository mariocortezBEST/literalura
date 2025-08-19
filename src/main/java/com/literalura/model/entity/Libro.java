package com.literalura.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import java.util.Objects;

/**
 * Entidad JPA que representa un Libro en la base de datos
 * Mapea la tabla 'libros' en PostgreSQL
 */
@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título no puede estar vacío")
    @Size(max = 500, message = "El título no puede exceder 500 caracteres")
    @Column(name = "titulo", nullable = false, length = 500)
    private String titulo;

    @NotBlank(message = "El idioma no puede estar vacío")
    @Size(max = 10, message = "El código de idioma no puede exceder 10 caracteres")
    @Column(name = "idioma", nullable = false, length = 10)
    private String idioma;

    @Min(value = 0, message = "El número de descargas no puede ser negativo")
    @Column(name = "numero_descargas")
    private Long numeroDescargas;

    // Relación muchos a uno con Autor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    @NotNull(message = "Un libro debe tener un autor")
    private Autor autor;

    // ID único del libro en la API (para evitar duplicados)
    @Column(name = "gutendx_id", unique = true)
    private Long gutendxId;

    // Constructor por defecto (requerido por JPA)
    public Libro() {}

    // Constructor con parámetros principales
    public Libro(String titulo, String idioma, Long numeroDescargas, Autor autor) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
    }

    // Constructor completo
    public Libro(String titulo, String idioma, Long numeroDescargas, Autor autor, Long gutendxId) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.numeroDescargas = numeroDescargas;
        this.autor = autor;
        this.gutendxId = gutendxId;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Long getGutendxId() {
        return gutendxId;
    }

    public void setGutendxId(Long gutendxId) {
        this.gutendxId = gutendxId;
    }

    // Métodos de utilidad

    /**
     * Obtiene el nombre completo del idioma basado en el código
     * @return Nombre del idioma en español
     */
    public String getNombreIdioma() {
        return switch (idioma.toLowerCase()) {
            case "es" -> "Español";
            case "en" -> "Inglés";
            case "fr" -> "Francés";
            case "de" -> "Alemán";
            case "pt" -> "Portugués";
            case "it" -> "Italiano";
            case "la" -> "Latín";
            default -> idioma.toUpperCase();
        };
    }

    /**
     * Formatea el número de descargas para mostrar
     * @return String formateado con el número de descargas
     */
    public String getDescargasFormateadas() {
        if (numeroDescargas == null) {
            return "No disponible";
        }

        if (numeroDescargas >= 1_000_000) {
            return String.format("%.1fM", numeroDescargas / 1_000_000.0);
        } else if (numeroDescargas >= 1_000) {
            return String.format("%.1fK", numeroDescargas / 1_000.0);
        } else {
            return numeroDescargas.toString();
        }
    }

    /**
     * Obtiene información resumida del libro para mostrar en listas
     * @return String con información resumida
     */
    public String getInfoResumida() {
        return String.format("'%s' por %s (%s) - %s descargas",
                titulo,
                autor != null ? autor.getNombre() : "Autor desconocido",
                getNombreIdioma(),
                getDescargasFormateadas());
    }

    // equals, hashCode y toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(id, libro.id) &&
                Objects.equals(gutendxId, libro.gutendxId) &&
                Objects.equals(titulo, libro.titulo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, gutendxId, titulo);
    }

    @Override
    public String toString() {
        return String.format("Libro{id=%d, titulo='%s', autor='%s', idioma='%s', descargas=%s}",
                id, titulo,
                autor != null ? autor.getNombre() : "N/A",
                getNombreIdioma(),
                getDescargasFormateadas());
    }
}