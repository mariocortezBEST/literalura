package com.literalura.service;

import com.literalura.dto.DatosLibros;
import com.literalura.dto.DatosLibro;
import com.literalura.dto.DatosAutor;
import com.literalura.model.entity.Libro;
import com.literalura.model.entity.Autor;
import com.literalura.model.Idioma;
import com.literalura.repository.LibroRepository;
import com.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LibroService {

    private static final String URL_BASE = "https://gutendx.com/books/";

    @Autowired
    private ConsumoApi consumoApi;

    @Autowired
    private ConvierteDatos conversor;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorRepository autorRepository;

    /**
     * Busca un libro por título en la API y lo guarda en la base de datos
     * @param titulo Título del libro a buscar
     * @return Libro encontrado y guardado
     */
    public Optional<Libro> buscarLibroPorTitulo(String titulo) {
        try {
            // Verificar si ya existe en la base de datos
            Optional<Libro> libroExistente = libroRepository
                    .findByTituloContainsIgnoreCase(titulo);

            if (libroExistente.isPresent()) {
                System.out.println("El libro ya existe en la base de datos:");
                return libroExistente;
            }

            // Buscar en la API
            String url = URL_BASE + "?search=" + titulo.replace(" ", "%20");
            String json = consumoApi.obtenerDatos(url);
            DatosLibros datosLibros = conversor.obtenerDatos(json, DatosLibros.class);

            if (datosLibros.resultados().isEmpty()) {
                System.out.println("No se encontraron libros con ese título.");
                return Optional.empty();
            }

            // Tomar el primer resultado
            DatosLibro datosLibro = datosLibros.resultados().get(0);
            Libro libro = crearLibroDesdeApi(datosLibro);

            Libro libroGuardado = libroRepository.save(libro);
            System.out.println("Libro guardado exitosamente:");
            return Optional.of(libroGuardado);

        } catch (Exception e) {
            System.err.println("Error al buscar libro: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Obtiene todos los libros guardados en la base de datos
     * @return Lista de todos los libros
     */
    public List<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    /**
     * Obtiene libros por idioma
     * @param idioma Idioma a filtrar
     * @return Lista de libros en el idioma especificado
     */
    public List<Libro> obtenerLibrosPorIdioma(Idioma idioma) {
        return libroRepository.findByIdioma(idioma);
    }

    /**
     * Obtiene estadísticas de libros por idioma
     * @param idioma Idioma a consultar
     * @return Cantidad de libros en el idioma especificado
     */
    public long contarLibrosPorIdioma(Idioma idioma) {
        return libroRepository.countByIdioma(idioma);
    }

    /**
     * Obtiene los top 10 libros más descargados
     * @return Lista de los 10 libros más descargados
     */
    public List<Libro> obtenerTop10LibrosMasDescargados() {
        return libroRepository.findTop10ByOrderByNumeroDescargasDesc();
    }

    /**
     * Busca libros por palabra clave en el título
     * @param palabraClave Palabra clave a buscar
     * @return Lista de libros que contienen la palabra clave
     */
    public List<Libro> buscarLibrosPorPalabraClave(String palabraClave) {
        return libroRepository.findByTituloContainsIgnoreCase(palabraClave)
                .map(List::of)
                .orElse(List.of());
    }

    /**
     * Crea un objeto Libro a partir de los datos de la API
     * @param datosLibro Datos del libro desde la API
     * @return Objeto Libro creado
     */
    private Libro crearLibroDesdeApi(DatosLibro datosLibro) {
        // Crear o buscar autor
        Autor autor = null;
        if (!datosLibro.autores().isEmpty()) {
            DatosAutor datosAutor = datosLibro.autores().get(0);

            // Buscar si el autor ya existe
            Optional<Autor> autorExistente = autorRepository
                    .findByNombreIgnoreCase(datosAutor.nombre());

            if (autorExistente.isPresent()) {
                autor = autorExistente.get();
            } else {
                // Crear nuevo autor
                autor = new Autor();
                autor.setNombre(datosAutor.nombre());
                autor.setAnioNacimiento(datosAutor.anioNacimiento());
                autor.setAnioFallecimiento(datosAutor.anioFallecimiento());
                autor = autorRepository.save(autor);
            }
        }

        // Crear libro
        Libro libro = new Libro();
        libro.setTitulo(datosLibro.titulo());
        libro.setAutor(autor);

        // Procesar idioma (tomar el primero)
        if (!datosLibro.idiomas().isEmpty()) {
            String codigoIdioma = datosLibro.idiomas().get(0);
            try {
                Idioma idioma = Idioma.fromCodigo(codigoIdioma);
                libro.setIdioma(idioma);
            } catch (IllegalArgumentException e) {
                libro.setIdioma(Idioma.OTROS);
            }
        } else {
            libro.setIdioma(Idioma.OTROS);
        }

        libro.setNumeroDescargas(datosLibro.numeroDescargas());

        return libro;
    }
}
