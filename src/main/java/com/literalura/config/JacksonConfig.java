package com.literalura.config;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * Configuración de Jackson para el mapeo JSON
 *
 * Esta clase configura ObjectMapper para:
 * - Manejar propiedades desconocidas sin fallar
 * - Configurar naming strategy snake_case
 * - Optimizar el rendimiento del parsing JSON
 */
@Configuration
public class JacksonConfig {

    /**
     * Configura ObjectMapper personalizado para la aplicación
     *
     * @return ObjectMapper configurado para manejar la API Gutendx
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // No fallar cuando hay propiedades desconocidas en el JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // No fallar cuando hay propiedades null
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

        // Permitir valores vacíos para objetos
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        // Configurar naming strategy para snake_case (download_count -> downloadCount)
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

        return mapper;
    }

    /**
     * Bean auxiliar para conversión de datos
     * Puede ser usado por el servicio ConvierteDatos
     *
     * @return ObjectMapper específico para conversión de datos
     */
    @Bean("dataConverter")
    public ObjectMapper dataConverterMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Configuración más estricta para conversión de datos internos
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, true);

        return mapper;
    }
}