package com.literalura.repository;

import com.literalura.model.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Busca un autor por nombre exacto (case insensitive)
     */
    Optional<Autor> findByNombreIgnoreCase(String nombre);

    /**
     * Busca autores que contengan el nombre especificado (case insensitive)
     */
    List<Autor> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Busca autores vivos en un año determinado
     * Un autor está vivo si:
     * - Su año de nacimiento <= año especificado
     * - Su año de fallecimiento es null O > año especificado
     */
    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento <= :ano AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento > :ano)")
    List<Autor> findAutoresVivosEnAno(@Param("ano") Integer ano);

    /**
     * Busca autores nacidos en un año específico
     */
    List<Autor> findByAnoNacimiento(Integer anoNacimiento);

    /**
     * Busca autores fallecidos en un año específico
     */
    List<Autor> findByAnoFallecimiento(Integer anoFallecimiento);

    /**
     * Busca autores nacidos entre dos años
     */
    List<Autor> findByAnoNacimientoBetween(Integer anoInicio, Integer anoFin);

    /**
     * Busca autores que aún están vivos (año de fallecimiento es null)
     */
    List<Autor> findByAnoFallecimientoIsNull();

    /**
     * Busca autores que ya fallecieron (año de fallecimiento no es null)
     */
    List<Autor> findByAnoFallecimientoIsNotNull();

    /**
     * Cuenta autores vivos en un año determinado
     */
    @Query("SELECT COUNT(a) FROM Autor a WHERE a.anoNacimiento <= :ano AND (a.anoFallecimiento IS NULL OR a.anoFallecimiento > :ano)")
    Long contarAutoresVivosEnAno(@Param("ano") Integer ano);

    /**
     * Obtiene autores ordenados por año de nacimiento
     */
    List<Autor> findAllByOrderByAnoNacimientoAsc();

    /**
     * Busca autores por siglo de nacimiento
     */
    @Query("SELECT a FROM Autor a WHERE a.anoNacimiento BETWEEN :anoInicio AND :anoFin")
    List<Autor> findAutoresPorSiglo(@Param("anoInicio") Integer anoInicio, @Param("anoFin") Integer anoFin);

    /**
     * Obtiene estadísticas de autores por siglo
     */
    @Query("SELECT FLOOR(a.anoNacimiento/100)*100 as siglo, COUNT(a) FROM Autor a WHERE a.anoNacimiento IS NOT NULL GROUP BY FLOOR(a.anoNacimiento/100) ORDER BY siglo")
    List<Object[]> obtenerEstadisticasPorSiglo();

    /**
     * Busca autores con al menos un libro en la base de datos
     */
    @Query("SELECT DISTINCT a FROM Autor a JOIN a.libros l")
    List<Autor> findAutoresConLibros();

    /**
     * Verifica si existe un autor con el mismo nombre y años de vida
     */
    @Query("SELECT COUNT(a) > 0 FROM Autor a WHERE a.nombre = :nombre AND a.anoNacimiento = :anoNacimiento AND (a.anoFallecimiento = :anoFallecimiento OR (a.anoFallecimiento IS NULL AND :anoFallecimiento IS NULL))")
    boolean existsByNombreAndAnosVida(@Param("nombre") String nombre, @Param("anoNacimiento") Integer anoNacimiento, @Param("anoFallecimiento") Integer anoFallecimiento);
}