package ru.otus.spring.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;

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

                .log()
                .split()
                .log()
                //.channel("chanelDir")
                .channel(MessageChannels.queue())
                .aggregate();
    }


    @Bean(name=PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defPoller(){
        return Pollers.fixedDelay(3000).maxMessagesPerPoll(1).get();
    }

    @Bean
    public DirectChannel channelDir(){
        return MessageChannels.direct().get();
    }

}
