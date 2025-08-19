package com.literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ConvierteDatos implements IConvierteDatos {

    private final ObjectMapper objectMapper;

    public ConvierteDatos() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Convierte un JSON string a un objeto de la clase especificada
     * @param json String JSON a convertir
     * @param clase Clase objetivo para la conversión
     * @return Objeto de la clase especificada
     */
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir JSON a objeto: " + e.getMessage(), e);
        }
    }

    /**
     * Convierte un objeto a JSON string
     * @param objeto Objeto a convertir
     * @return String JSON
     */
    public String convertirAJson(Object objeto) {
        try {
            return objectMapper.writeValueAsString(objeto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al convertir objeto a JSON: " + e.getMessage(), e);
        }
    }
}
