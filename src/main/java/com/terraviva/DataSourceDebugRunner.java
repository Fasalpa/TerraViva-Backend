package com.terraviva;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class DataSourceDebugRunner implements CommandLineRunner {

    private final Environment env;

    public DataSourceDebugRunner(Environment env) {
        this.env = env;
    }

    @Override
    public void run(String... args) {
        System.out.println("DATASOURCE URL -> " + env.getProperty("spring.datasource.url"));
        System.out.println("DATASOURCE USER -> " + env.getProperty("spring.datasource.username"));
    }
}