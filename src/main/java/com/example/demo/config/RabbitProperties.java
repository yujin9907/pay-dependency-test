package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;

@ConfigurationProperties("spring.rabbitmq")
@AllArgsConstructor
@Getter
public class RabbitProperties {
    private String host;
    private int port;
    private String username;
    private String password;
}
