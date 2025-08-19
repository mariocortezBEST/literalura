package com.literalura.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

/**
 * Configuración para el cliente HTTP y la API externa
 *
 * Esta clase configura:
 * - HttpClient para realizar solicitudes a la API Gutendx
 * - Timeouts y configuraciones de red
 * - Headers por defecto
 */
@Configuration
public class ApiConfig {

    @Value("${literalura.api.gutendx.base-url:https://gutendx.com/books/}")
    private String baseUrl;

    @Value("${literalura.api.gutendx.timeout:30000}")
    private long timeoutMillis;

    @Value("${literalura.api.user-agent:LiterAlura/1.0}")
    private String userAgent;

    /**
     * Configura HttpClient para realizar solicitudes HTTP
     *
     * @return HttpClient configurado con timeouts apropiados
     */
    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofMillis(timeoutMillis))
                .build();
    }

    /**
     * Proporciona la URL base de la API Gutendx
     *
     * @return URL base configurada
     */
    @Bean("gutendxBaseUrl")
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * Proporciona el User-Agent para las solicitudes
     *
     * @return User-Agent configurado
     */
    @Bean("apiUserAgent")
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * Proporciona el timeout para las solicitudes
     *
     * @return Timeout en milisegundos
     */
    @Bean("apiTimeout")
    public Duration getTimeout() {
        return Duration.ofMillis(timeoutMillis);
    }
}