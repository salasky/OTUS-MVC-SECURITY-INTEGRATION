package ru.otus.spring.microservice.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
public class HealthIndicator implements org.springframework.boot.actuate.health.HealthIndicator {
    //рандомный healthindicator
    @Override
    public Health health() {
       while (true) {
           try {
               Thread.sleep(5000);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           int i= new Random().nextInt(10);
           if(i%2==0) {
               return Health.down().build();
           }
           else
               return Health.up().build();
       }
    }
}
