package ru.otus.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;

import java.util.Arrays;

@SpringBootApplication
@IntegrationComponentScan
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(App.class, args);

        ctx.getBean(UpperService.class).upperStrings(
                Arrays.asList("foo", "bar")
        ).forEach(System.out::println);

        ctx.close();
    }

    @Bean
    public IntegrationFlow upperr() {
        return f -> f

                .split()
                .channel(MessageChannels.direct())
                .log()
                .<String, String>transform(String::toUpperCase)
                .aggregate();
    }
}
