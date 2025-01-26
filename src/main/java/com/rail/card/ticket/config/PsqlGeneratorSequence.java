package com.rail.card.ticket.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class PsqlGeneratorSequence implements GeneratorSequence {
    private final JdbcTemplate jdbcTemplate;

    public PsqlGeneratorSequence(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(String sequenceName) {
        this.jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS \"" + sequenceName + "\" INCREMENT 1 START 1");
    }

    public long get(String sequenceName) {
        return (Long)this.jdbcTemplate.queryForObject("select nextval('" + sequenceName + "')", Long.class);
    }
}
