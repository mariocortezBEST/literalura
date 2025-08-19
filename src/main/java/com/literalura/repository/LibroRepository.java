package com.literalura.repository;

import com.literalura.model.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    /**
     * Busca un libro por título exacto (case insensitive)
     */
    Optional<Libro> findByTituloIgnoreCase(String titulo);

    /**
     * Busca libros que contengan el título especificado (case insensitive)
     */
    List<Libro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca libros por idioma
     */
    List<Libro> findByIdioma(String idioma);

    /**
     * Cuenta la cantidad de libros por idioma
     */
    @Query("SELECT COUNT(l) FROM Libro l WHERE l.idioma = :idioma")
    Long contarLibrosPorIdioma(@Param("idioma") String idioma);

    /**
     * Obtiene todos los idiomas disponibles en la base de datos
     */
    @Query("SELECT DISTINCT l.idioma FROM Libro l ORDER BY l.idioma")
    List<String> findDistinctIdiomas();

    /**
     * Busca libros por autor
     */
    @Query("SELECT l FROM Libro l WHERE l.autor.id = :autorId")
    List<Libro> findByAutorId(@Param("autorId") Long autorId);

    /**
     * Busca libros ordenados por número de descargas (descendente)
     */
    List<Libro> findAllByOrderByNumeroDescargasDesc();

    /**
     * Busca los top N libros más descargados
     */
    @Query("SELECT l FROM Libro l ORDER BY l.numeroDescargas DESC LIMIT :limite")
    List<Libro> findTopLibrosMasDescargados(@Param("limite") int limite);

    /**
     * Verifica si ya existe un libro con el mismo título y autor
     */
    @Query("SELECT COUNT(l) > 0 FROM Libro l WHERE l.titulo = :titulo AND l.autor.id = :autorId")
    boolean existsByTituloAndAutorId(@Param("titulo") String titulo, @Param("autorId") Long autorId);

    /**
     * Busca libros con más de X descargas
     */
    List<Libro> findByNumeroDescargasGreaterThan(Integer numeroDescargas);

    /**
     * Obtiene estadísticas de idiomas
     */
    @Query("SELECT l.idioma, COUNT(l) FROM Libro l GROUP BY l.idioma ORDER BY COUNT(l) DESC")
    List<Object[]> obtenerEstadisticasPorIdioma();
}
