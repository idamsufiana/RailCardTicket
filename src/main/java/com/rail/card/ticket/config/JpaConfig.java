package com.rail.card.ticket.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "appEntityManagerFactory",
        transactionManagerRef = "appTransactionManager",
        basePackages = {"com.rail.card.ticket"})
public class JpaConfig {

    @Autowired
    private Environment env;

    @Bean(name = "appEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean appEntityManagerFactory(final EntityManagerFactoryBuilder builder,
                                                                          final @Qualifier("app-datasource") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();

        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", env.getProperty("spring.jpa.hibernate.naming-strategy"));

        return builder
                .dataSource(dataSource)
                .packages("com.rail.card.ticket")
                .persistenceUnit("app")
                .properties(properties).build();
    }

    @Bean(name = "appTransactionManager")
    public PlatformTransactionManager appTransactionManager(
            @Qualifier("appEntityManagerFactory") EntityManagerFactory appEntityManagerFactory) {
        return new JpaTransactionManager(appEntityManagerFactory);
    }
}
