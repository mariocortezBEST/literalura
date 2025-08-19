package com.literalura.service;
import com.literalura.model.entity.Autor;
import com.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AutorService {

    @Autowired
    private AutorRepository autorRepository;

    /**
     * Obtiene todos los autores registrados
     * @return Lista de todos los autores
     */
    public List<Autor> obtenerTodosLosAutores() {
        return autorRepository.findAll();
    }

    /**
     * Busca autores que estaban vivos en un año determinado
     * @param anio Año a consultar
     * @return Lista de autores vivos en el año especificado
     */
    public List<Autor> buscarAutoresVivosEnAnio(Integer anio) {
        if (anio == null || anio < 0) {
            throw new IllegalArgumentException("El año debe ser un número positivo");
        }

        return autorRepository.findAutoresVivosEnAnio(anio);
    }

    /**
     * Busca autores por nombre
     * @param nombre Nombre del autor a buscar
     * @return Lista de autores que coinciden con el nombre
     */
    public List<Autor> buscarAutoresPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of();
        }

        return autorRepository.findByNombreContainsIgnoreCase(nombre.trim());
    }

    /**
     * Obtiene autores nacidos en un rango de años
     * @param anioInicio Año de inicio del rango
     * @param anioFin Año de fin del rango
     * @return Lista de autores nacidos en el rango especificado
     */
    public List<Autor> buscarAutoresPorRangoNacimiento(Integer anioInicio, Integer anioFin) {
        if (anioInicio == null || anioFin == null) {
            throw new IllegalArgumentException("Los años no pueden ser nulos");
        }

        if (anioInicio > anioFin) {
            throw new IllegalArgumentException("El año de inicio debe ser menor o igual al año de fin");
        }

        return autorRepository.findByAnioNacimientoBetween(anioInicio, anioFin);
    }

    /**
     * Cuenta el número total de autores registrados
     * @return Número total de autores
     */
    public long contarAutores() {
        return autorRepository.count();
    }

    /**
     * Verifica si un autor está vivo en un año específico
     * @param autor Autor a verificar
     * @param anio Año a consultar
     * @return true si el autor estaba vivo en ese año, false en caso contrario
     */
    public boolean estaVivoEnAnio(Autor autor, Integer anio) {
        if (autor == null || anio == null) {
            return false;
        }

        // Si no tiene año de nacimiento, no podemos determinar
        if (autor.getAnioNacimiento() == null) {
            return false;
        }

        // Debe haber nacido antes o en el año consultado
        if (autor.getAnioNacimiento() > anio) {
            return false;
        }

        // Si no tiene año de fallecimiento, se asume que sigue vivo
        if (autor.getAnioFallecimiento() == null) {
            return true;
        }

        // Debe haber fallecido después del año consultado
        return autor.getAnioFallecimiento() > anio;
    }
}