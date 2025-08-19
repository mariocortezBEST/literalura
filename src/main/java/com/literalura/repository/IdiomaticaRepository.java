package com.literalura.repository;

import com.literalura.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Repositorio especializado para consultas relacionadas con idiomas
 */
@Repository
public interface IdiomaticaRepository extends JpaRepository<Libro, Long> {

    /**
     * Obtiene un mapa con la cantidad de libros por idioma
     */
    @Query("SELECT l.idioma as idioma, COUNT(l) as cantidad FROM Libro l GROUP BY l.idioma ORDER BY COUNT(l) DESC")
    List<Object[]> obtenerResumenPorIdioma();

    /**
     * Busca libros en español
     */
    @Query("SELECT l FROM Libro l WHERE l.idioma IN ('es', 'spanish', 'español')")
    List<Libro> findLibrosEnEspanol();

    /**
     * Busca libros en inglés
     */
    @Query("SELECT l FROM Libro l WHERE l.idioma IN ('en', 'english', 'inglés')")
    List<Libro> findLibrosEnIngles();

    /**
     * Busca libros en francés
     */
    @Query("SELECT l FROM Libro l WHERE l.idioma IN ('fr', 'french', 'francés')")
    List<Libro> findLibrosEnFrances();

    /**
     * Busca libros en portugués
     */
    @Query("SELECT l FROM Libro l WHERE l.idioma IN ('pt', 'portuguese', 'portugués')")
    List<Libro> findLibrosEnPortugues();

    /**
     * Obtiene los idiomas más populares (con más libros)
     */
    @Query("SELECT l.idioma FROM Libro l GROUP BY l.idioma ORDER BY COUNT(l) DESC LIMIT :limite")
    List<String> findIdiomasPopulares(@Param("limite") int limite);
}
