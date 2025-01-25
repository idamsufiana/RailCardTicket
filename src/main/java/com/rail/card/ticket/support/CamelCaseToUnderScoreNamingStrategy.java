package com.rail.card.ticket.support;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CamelCaseToUnderScoreNamingStrategy extends PhysicalNamingStrategyStandardImpl {
    public static final CamelCaseToUnderScoreNamingStrategy INSTANCE = new CamelCaseToUnderScoreNamingStrategy();
    public static final String CAMEL_CASE_REGEX = "([a-z]+)([A-Z]+)";
    public static final String SNAKE_CASE_PATTERN = "$1\\_$2";


    public Identifier toPhysicalCatalogName(Identifier name, JdbcEnvironment context) {
        return this.formatIdentifier(super.toPhysicalCatalogName(name, context));
    }

    public Identifier toPhysicalSchemaName(Identifier name, JdbcEnvironment context) {
        return this.formatIdentifier(super.toPhysicalSchemaName(name, context));
    }

    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return this.formatIdentifier(super.toPhysicalTableName(name, context));
    }

    public Identifier toPhysicalSequenceName(Identifier name, JdbcEnvironment context) {
        return this.formatIdentifier(super.toPhysicalSequenceName(name, context));
    }

    public Identifier toPhysicalColumnName(Identifier name, JdbcEnvironment context) {
        return this.formatIdentifier(super.toPhysicalColumnName(name, context));
    }

    private Identifier formatIdentifier(Identifier identifier) {
        if (identifier != null) {
            String name = identifier.getText();
            String formattedName = name.replaceAll("([a-z]+)([A-Z]+)", "$1\\_$2").toLowerCase();
            return !formattedName.equals(name) ? Identifier.toIdentifier(formattedName, identifier.isQuoted()) : identifier;
        } else {
            return null;
        }
    }
}