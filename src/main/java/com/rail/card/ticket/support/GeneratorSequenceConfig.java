package com.rail.card.ticket.support;

import com.rail.card.ticket.support.PsqlGeneratorSequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class GeneratorSequenceConfig {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Bean
    public GeneratorSequence generatorSequence() {
        GeneratorSequence generatorSequence = new PsqlGeneratorSequence(jdbcTemplate);
        generatorSequence.create("rail_seq");
        return generatorSequence;
    }
}
