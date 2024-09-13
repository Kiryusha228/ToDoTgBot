package org.example.tgbot;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TgBotMicroserviceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TgBotMicroserviceApplication.class, args);
    }
}
//123