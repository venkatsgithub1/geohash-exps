package org.example;

import jakarta.annotation.PostConstruct;
import org.example.service.db.DatabaseLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private final DatabaseLoader databaseLoader;

    public Application(DatabaseLoader databaseLoader) {
        this.databaseLoader = databaseLoader;
    }

    @PostConstruct
    public void init() {
        databaseLoader.loadDataIfNeeded();
    }


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}