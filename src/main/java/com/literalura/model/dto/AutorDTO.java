package com.literalura.model.dto;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * DTO que mapea un autor individual de la respuesta de la API Gutendx
 * Representa la estructura JSON de cada autor en el array "authors" de un libro
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AutorDTO {

    @JsonAlias("name")
    private String nombre;

    @JsonAlias("birth_year")
    private Integer anoNacimiento;

    @JsonAlias("death_year")
    private Integer anoFallecimiento;

    // Constructor por defecto
    public AutorDTO() {}

    // Constructor con parámetros
    public AutorDTO(String nombre, Integer anoNacimiento, Integer anoFallecimiento) {
        this.nombre = nombre;
        this.anoNacimiento = anoNacimiento;
        this.anoFallecimiento = anoFallecimiento;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnoNacimiento() {
        return anoNacimiento;
    }

    public void setAnoNacimiento(Integer anoNacimiento) {
        this.anoNacimiento = anoNacimiento;
    }

    public Integer getAnoFallecimiento() {
        return anoFallecimiento;
    }

    public void setAnoFallecimiento(Integer anoFallecimiento) {
        this.anoFallecimiento = anoFallecimiento;
    }

    // Métodos de utilidad

    /**
     * Obtiene una representación limpia del nombre
     * @return Nombre sin espacios extra
     */
    public String getNombreLimpio() {
        if (nombre == null) return null;
        return nombre.trim().replaceAll("\\s+", " ");
    }

    /**
     * Verifica si el autor tiene información básica válida
     * @return true si tiene al menos el nombre
     */
    public boolean esValido() {
        return nombre != null && !nombre.trim().isEmpty();
    }

    /**
     * Verifica si el autor estaba vivo en un año específico
     * @param ano Año a verificar
     * @return true si estaba vivo, false si no o si no se puede determinar
     */
    public boolean estabaVivoEn(int ano) {
        // Si no hay año de nacimiento, no podemos determinar
        if (anoNacimiento == null) {
            return false;
        }

        // Si nació después del año consultado, no estaba vivo
        if (anoNacimiento > ano) {
            return false;
        }

        // Si no hay año de fallecimiento, se asume que está vivo
        if (anoFallecimiento == null) {
            return true;
        }

        // Estaba vivo si el año está entre nacimiento y fallecimiento (inclusive)
        return anoFallecimiento >= ano;
    }

    /**
     * Obtiene la información de vida del autor como string
     * @return String con años de vida
     */
    public String getInfoVida() {
        if (anoNacimiento == null && anoFallecimiento == null) {
            return "Fechas desconocidas";
        }

        String nacimiento = anoNacimiento != null ? anoNacimiento.toString() : "?";
        String fallecimiento = anoFallecimiento != null ? anoFallecimiento.toString() : "Presente";

        return String.format("(%s - %s)", nacimiento, fallecimiento);
    }

    /**
     * Verifica si el autor está presumiblemente vivo
     * @return true si no tiene año de fallecimiento y tiene año de nacimiento
     */
    public boolean estaVivo() {
        return anoNacimiento != null && anoFallecimiento == null;
    }

    /**
     * Calcula la edad aproximada del autor en un año dado
     * @param ano Año para calcular la edad
     * @return Edad aproximada o -1 si no se puede calcular
     */
    public int calcularEdadEn(int ano) {
        if (anoNacimiento == null) {
            return -1;
        }

        // Si murió antes del año consultado, usar año de muerte
        if (anoFallecimiento != null && anoFallecimiento < ano) {
            return anoFallecimiento - anoNacimiento;
        }

        // Si nació después del año consultado, edad negativa (no había nacido)
        if (anoNacimiento > ano) {
            return -1;
        }

        return ano - anoNacimiento;
    }

    /**
     * Información completa del autor para mostrar
     * @return String con nombre y años de vida
     */
    public String getInfoCompleta() {
        return String.format("%s %s", getNombreLimpio(), getInfoVida());
    }

    /**
     * Verifica si el nombre del autor contiene una palabra específica
     * @param palabra Palabra a buscar (insensible a mayúsculas)
     * @return true si el nombre contiene la palabra
     */
    public boolean contieneEnNombre(String palabra) {
        if (nombre == null || palabra == null) return false;
        return nombre.toLowerCase().contains(palabra.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("AutorDTO{nombre='%s', nacimiento=%s, fallecimiento=%s}",
                getNombreLimpio(),
                anoNacimiento != null ? anoNacimiento : "?",
                anoFallecimiento != null ? anoFallecimiento : "Vivo");
    }
}