package com.cip.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
    @Configuration
    @PropertySource({"classpath:application.properties"})
    @EnableJpaRepositories(
            basePackages = "com.cip.dao.cip",
            entityManagerFactoryRef = "cipEntityManager",
            transactionManagerRef = "cipTransactionManager")
    public class PersistenceCipAutoConfiguration {

        @Bean
        @ConfigurationProperties(prefix="spring.second-datasource")
        public DataSource productDataSource() {
            return DataSourceBuilder.create().build();
        }

        // productEntityManager bean

        // productTransactionManager bean
    }
