# 📚 LiterAlura - Catálogo de Libros

## 🎯 Descripción del Proyecto

**LiterAlura** es una aplicación de consola desarrollada en Java con Spring Boot que permite gestionar un catálogo personal de libros. La aplicación consume la API pública de Gutendex para buscar información de libros y autores, almacenando los datos en una base de datos PostgreSQL para consultas posteriores.

### ✨ Características Principales

- 🔍 **Búsqueda de libros** por título utilizando la API de Gutendx
- 💾 **Persistencia de datos** en base de datos PostgreSQL
- 📊 **Consultas avanzadas** de libros y autores
- 🌍 **Filtrado por idiomas** disponibles
- 👥 **Gestión de autores** con información biográfica
- 📈 **Estadísticas** del catálogo personal

## 🛠️ Tecnologías Utilizadas

- **Java JDK 17+**
- **Spring Boot 3.2.3**
- **Spring Data JPA**
- **PostgreSQL 16+**
- **Jackson** (para manejo de JSON)
- **Maven 4+**
- **IntelliJ IDEA** (IDE recomendado)

## 📋 Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

1. **Java JDK 17 o superior**
   - [Descargar Java](https://www.oracle.com/java/technologies/downloads/)

2. **PostgreSQL 16 o superior**
   - [Descargar PostgreSQL](https://www.postgresql.org/download/)

3. **Maven 4 o superior**
   - Incluido con la mayoría de IDEs modernos

4. **IDE recomendado: IntelliJ IDEA**
   - Cualquier IDE compatible con Spring Boot

## 🚀 Instalación y Configuración

### 1. Clonar el Repositorio
```bash
git clone [URL_DEL_REPOSITORIO]
cd literalura
```

### 2. Configurar Base de Datos

1. **Crear base de datos en PostgreSQL:**
```sql
CREATE DATABASE literalura;
CREATE USER literalura_user WITH PASSWORD 'tu_password';
GRANT ALL PRIVILEGES ON DATABASE literalura TO literalura_user;
```

2. **Configurar application.properties:**
```properties
# Configuración de la base de datos
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=literalura_user
spring.datasource.password=tu_password
spring.datasource.driver-class-name=org.postgresql.Driver

# Configuración de JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### 3. Instalar Dependencias
```bash
mvn clean install
```

### 4. Ejecutar la Aplicación
```bash
mvn spring-boot:run
```

## 📖 Funcionalidades

### Menú Principal
La aplicación presenta un menú interactivo con las siguientes opciones:

1. **Buscar libro por título**
2. **Listar libros registrados**
3. **Listar autores registrados**
4. **Listar autores vivos en determinado año**
5. **Listar libros por idioma**
6. **Estadísticas por idioma**
7. **Salir**

### 🔍 Búsqueda de Libros

- Busca libros por título en la API de Gutendx
- Guarda automáticamente el libro y autor en la base de datos
- Muestra información detallada: título, autor, idiomas, número de descargas

### 📚 Gestión de Autores

- Lista todos los autores registrados
- Filtra autores que estaban vivos en un año específico
- Muestra información biográfica completa

### 🌍 Filtros por Idioma

- Lista libros disponibles en idiomas específicos
- Genera estadísticas de distribución por idioma
- Soporte para múltiples idiomas

## 🏗️ Arquitectura del Proyecto

```
src/
├── main/
│   ├── java/
│   │   └── com/alura/literalura/
│   │       ├── LiteraluraApplication.java
│   │       ├── model/
│   │       │   ├── Libro.java
│   │       │   ├── Autor.java
│   │       │   └── DatosLibro.java
│   │       ├── repository/
│   │       │   ├── LibroRepository.java
│   │       │   └── AutorRepository.java
│   │       ├── service/
│   │       │   ├── ConsumoAPI.java
│   │       │   ├── ConvierteDatos.java
│   │       │   └── LibroService.java
│   │       └── principal/
│   │           └── Principal.java
│   └── resources/
│       └── application.properties
```

## 🔧 API Utilizada

**Gutendx API**: https://gutendx.com/

- API gratuita sin necesidad de autenticación
- Catálogo de más de 70,000 libros del Project Gutenberg
- Respuestas en formato JSON
- Búsqueda por título, autor, idioma, etc.

### Ejemplo de URL de consulta:
```
https://gutendx.com/books/?search=don%20quijote
```

## 📊 Modelo de Datos

### Entidad Libro
- ID (Primary Key)
- Título
- Idioma
- Número de descargas
- Autor (Foreign Key)

### Entidad Autor
- ID (Primary Key)
- Nombre
- Año de nacimiento
- Año de fallecimiento
- Lista de libros (One-to-Many)

## 🧪 Pruebas

Para ejecutar las pruebas:

```bash
mvn test
```

## 📝 Ejemplos de Uso

### Buscar un Libro
```
Seleccione una opción: 1
Ingrese el título del libro: Don Quijote

Resultado:
Título: Don Quijote de la Mancha
Autor: Miguel de Cervantes Saavedra (1547-1616)
Idioma: es
Descargas: 15234
```

### Listar Autores Vivos en un Año
```
Seleccione una opción: 4
Ingrese el año: 1600

Autores vivos en 1600:
- Miguel de Cervantes Saavedra (1547-1616)
- William Shakespeare (1564-1616)
```

## 🚨 Manejo de Errores

La aplicación incluye manejo robusto de errores para:

- Conexión a la API externa
- Errores de base de datos
- Entrada inválida del usuario
- Libros no encontrados
- Problemas de conectividad

## 🤝 Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Agrega nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo `LICENSE` para más detalles.

## 👥 Autor

Desarrollado como parte del desafío de programación de Alura.

## 📞 Soporte

Si tienes alguna pregunta o problema:

1. Revisa la documentación de la API Gutendx
2. Verifica la configuración de la base de datos
3. Asegúrate de que todas las dependencias estén instaladas correctamente

## 🔄 Actualizaciones Futuras

- [ ] Interfaz web con Spring MVC
- [ ] API REST para consultas externas
- [ ] Exportación de catálogo a diferentes formatos
- [ ] Integración con otras APIs de libros
- [ ] Sistema de reseñas y calificaciones
