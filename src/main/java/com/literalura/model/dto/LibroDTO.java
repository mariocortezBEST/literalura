package com.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * DTO que mapea un libro individual de la respuesta de la API Gutendx
 * Representa la estructura JSON de cada libro en el array "results"
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibroDTO {

    @JsonAlias("id")
    private Long id;

    @JsonAlias("title")
    private String titulo;

    @JsonAlias("authors")
    private List<AutorDTO> autores;

    @JsonAlias("languages")
    private List<String> idiomas;

    @JsonAlias("download_count")
    private Long numeroDescargas;

    @JsonAlias("subjects")
    private List<String> materias;

    @JsonAlias("formats")
    private Object formatos; // Puede ser complejo, por ahora Object

    // Constructor por defecto
    public LibroDTO() {}

    // Constructor con parámetros principales
    public LibroDTO(Long id, String titulo, List<AutorDTO> autores,
                    List<String> idiomas, Long numeroDescargas) {
        this.id = id;
        this.titulo = titulo;
        this.autores = autores;
        this.idiomas = idiomas;
        this.numeroDescargas = numeroDescargas;
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

    public List<AutorDTO> getAutores() {
        return autores;
    }

    public void setAutores(List<AutorDTO> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Long getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Long numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public List<String> getMaterias() {
        return materias;
    }

    public void setMaterias(List<String> materias) {
        this.materias = materias;
    }

    public Object getFormatos() {
        return formatos;
    }

    public void setFormatos(Object formatos) {
        this.formatos = formatos;
    }

    // Métodos de utilidad según los requerimientos del proyecto

    /**
     * Obtiene el primer autor del libro (según requerimiento del proyecto)
     * @return Primer AutorDTO o null si no hay autores
     */
    public AutorDTO getPrimerAutor() {
        return (autores != null && !autores.isEmpty()) ? autores.get(0) : null;
    }

    /**
     * Obtiene el primer idioma del libro (según requerimiento del proyecto)
     * @return Código del primer idioma o null si no hay idiomas
     */
    public String getPrimerIdioma() {
        return (idiomas != null && !idiomas.isEmpty()) ? idiomas.get(0) : null;
    }

    /**
     * Verifica si el libro tiene información básica completa
     * @return true si tiene título, al menos un autor y un idioma
     */
    public boolean esValido() {
        return titulo != null && !titulo.trim().isEmpty() &&
                autores != null && !autores.isEmpty() &&
                idiomas != null && !idiomas.isEmpty();
    }

    /**
     * Obtiene una representación limpia del título
     * @return Título sin espacios extra y capitalizado apropiadamente
     */
    public String getTituloLimpio() {
        if (titulo == null) return null;
        return titulo.trim().replaceAll("\\s+", " ");
    }

    /**
     * Obtiene el número de descargas o 0 si es null
     * @return Número de descargas seguro
     */
    public Long getDescargasSeguro() {
        return numeroDescargas != null ? numeroDescargas : 0L;
    }

    /**
     * Información resumida para logging/debug
     * @return String con información básica del libro
     */
    public String getInfoBasica() {
        AutorDTO autor = getPrimerAutor();
        return String.format("'%s' por %s (%s) - %d descargas",
                getTituloLimpio(),
                autor != null ? autor.getNombre() : "Autor desconocido",
                getPrimerIdioma(),
                getDescargasSeguro());
    }

    /**
     * Verifica si el libro contiene una palabra en el título (búsqueda insensible a mayúsculas)
     * @param palabra Palabra a buscar
     * @return true si el título contiene la palabra
     */
    public boolean contienePalabraEnTitulo(String palabra) {
        if (titulo == null || palabra == null) return false;
        return titulo.toLowerCase().contains(palabra.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("LibroDTO{id=%d, titulo='%s', autores=%d, idiomas=%s, descargas=%d}",
                id,
                getTituloLimpio(),
                autores != null ? autores.size() : 0,
                getPrimerIdioma(),
                getDescargasSeguro());
    }
}