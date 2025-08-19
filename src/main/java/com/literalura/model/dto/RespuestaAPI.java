package com.literalura.model.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * DTO que mapea la respuesta completa de la API Gutendx
 * Representa la estructura JSON devuelta por https://gutendx.com/books/
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespuestaAPI {

    @JsonAlias("count")
    private Integer totalResultados;

    @JsonAlias("next")
    private String siguientePagina;

    @JsonAlias("previous")
    private String paginaAnterior;

    @JsonAlias("results")
    private List<LibroDTO> resultados;

    // Constructor por defecto
    public RespuestaAPI() {}

    // Constructor con parámetros
    public RespuestaAPI(Integer totalResultados, String siguientePagina,
                        String paginaAnterior, List<LibroDTO> resultados) {
        this.totalResultados = totalResultados;
        this.siguientePagina = siguientePagina;
        this.paginaAnterior = paginaAnterior;
        this.resultados = resultados;
    }

    // Getters y Setters
    public Integer getTotalResultados() {
        return totalResultados;
    }

    public void setTotalResultados(Integer totalResultados) {
        this.totalResultados = totalResultados;
    }

    public String getSiguientePagina() {
        return siguientePagina;
    }

    public void setSiguientePagina(String siguientePagina) {
        this.siguientePagina = siguientePagina;
    }

    public String getPaginaAnterior() {
        return paginaAnterior;
    }

    public void setPaginaAnterior(String paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    public List<LibroDTO> getResultados() {
        return resultados;
    }

    public void setResultados(List<LibroDTO> resultados) {
        this.resultados = resultados;
    }

    // Métodos de utilidad

    /**
     * Verifica si hay resultados en la respuesta
     * @return true si hay al menos un resultado
     */
    public boolean tieneResultados() {
        return resultados != null && !resultados.isEmpty();
    }

    /**
     * Obtiene el primer libro de los resultados (útil para búsquedas específicas)
     * @return Primer LibroDTO o null si no hay resultados
     */
    public LibroDTO getPrimerLibro() {
        return tieneResultados() ? resultados.get(0) : null;
    }

    /**
     * Obtiene el número de resultados en esta página
     * @return Cantidad de libros en los resultados
     */
    public int getCantidadResultados() {
        return resultados != null ? resultados.size() : 0;
    }

    /**
     * Verifica si hay más páginas disponibles
     * @return true si existe una siguiente página
     */
    public boolean tieneOtrasPaginas() {
        return siguientePagina != null && !siguientePagina.isEmpty();
    }

    /**
     * Información resumida de la respuesta para logging
     * @return String con información de la respuesta
     */
    public String getInfoRespuesta() {
        return String.format("Respuesta API: %d resultados totales, %d en esta página, %s páginas adicionales",
                totalResultados != null ? totalResultados : 0,
                getCantidadResultados(),
                tieneOtrasPaginas() ? "con" : "sin");
    }

    @Override
    public String toString() {
        return String.format("RespuestaAPI{totalResultados=%d, resultados=%d, siguientePagina='%s'}",
                totalResultados != null ? totalResultados : 0,
                getCantidadResultados(),
                siguientePagina != null ? "Sí" : "No");
    }
}