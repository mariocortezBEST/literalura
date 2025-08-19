package com.literalura.service;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Service
public class ConsumoApi {

    private final HttpClient client;

    public ConsumoApi() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    /**
     * Realiza una petición GET a la URL especificada
     * @param url URL a consultar
     * @return Respuesta en formato JSON como String
     */
    public String obtenerDatos(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(30))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();
            } else {
                throw new RuntimeException("Error en la petición HTTP. Código: " +
                        response.statusCode());
            }

        } catch (IOException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Petición interrumpida: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Error inesperado: " + e.getMessage(), e);
        }
    }
}