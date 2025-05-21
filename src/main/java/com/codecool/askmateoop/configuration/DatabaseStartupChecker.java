package com.codecool.askmateoop.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class DatabaseStartupChecker {

    private final DataSource dataSource;

    public DatabaseStartupChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @PostConstruct
    public void checkConnection() {
        try (Connection conn = dataSource.getConnection()) {
            System.out.println("Successfully connected to the database!");
        } catch (Exception e) {
            System.err.println("Failed to connect to the database:");
            e.printStackTrace();
        }
    }
}