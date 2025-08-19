package com.literalura.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * Configuración de base de datos PostgreSQL para LiterAlura
 *
 * Esta clase configura:
 * - DataSource para PostgreSQL
 * - EntityManagerFactory con Hibernate
 * - TransactionManager para JPA
 * - Propiedades específicas de Hibernate
 */
@Configuration
@EnableJpaRepositories(basePackages = "com.alura.literalura.repository")
public class DatabaseConfig {

    @Value("${spring.datasource.url:jdbc:postgresql://localhost:5432/literalura}")
    private String databaseUrl;

    @Value("${spring.datasource.username:postgres}")
    private String databaseUsername;

    @Value("${spring.datasource.password:}")
    private String databasePassword;

    @Value("${spring.jpa.hibernate.ddl-auto:update}")
    private String hibernateDdlAuto;

    @Value("${spring.jpa.show-sql:true}")
    private String showSql;

    /**
     * Configura el DataSource para PostgreSQL
     *
     * @return DataSource configurado para PostgreSQL
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(databaseUrl);
        dataSource.setUsername(databaseUsername);
        dataSource.setPassword(databasePassword);

        return dataSource;
    }

    /**
     * Configura el EntityManagerFactory con Hibernate como proveedor JPA
     *
     * @return LocalContainerEntityManagerFactoryBean configurado
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.alura.literalura.model.entity");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());

        return em;
    }

    /**
     * Configura el TransactionManager para JPA
     *
     * @return PlatformTransactionManager configurado
     */
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    /**
     * Propiedades específicas de Hibernate
     *
     * @return Properties con configuración de Hibernate
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();

        // Dialecto de PostgreSQL
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        // Estrategia de DDL (create, update, validate, create-drop)
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateDdlAuto);

        // Mostrar SQL en consola
        properties.setProperty("hibernate.show_sql", showSql);

        // Formatear SQL en consola
        properties.setProperty("hibernate.format_sql", "true");

        // Mostrar comentarios SQL
        properties.setProperty("hibernate.use_sql_comments", "true");

        // Configuración de conexiones
        properties.setProperty("hibernate.connection.autocommit", "false");

        // Configuración del pool de conexiones (básica)
        properties.setProperty("hibernate.c3p0.min_size", "5");
        properties.setProperty("hibernate.c3p0.max_size", "20");
        properties.setProperty("hibernate.c3p0.timeout", "300");
        properties.setProperty("hibernate.c3p0.max_statements", "50");
        properties.setProperty("hibernate.c3p0.idle_test_period", "3000");

        // Configuración de caché de segundo nivel (opcional)
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");

        // Configuración de estadísticas (útil para debugging)
        properties.setProperty("hibernate.generate_statistics", "false");

        return properties;
    }
}