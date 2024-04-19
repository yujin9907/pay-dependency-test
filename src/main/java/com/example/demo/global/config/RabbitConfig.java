package com.example.demo.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

// https://docs.spring.io/spring-amqp/reference/amqp/broker-configuration.html

@PropertySource(value = "classpath:/application.yml")
@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    private final RabbitProperties rabbitProperties;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Value("${rabbitmq.queue.json-name}")
    private String jsonQueue;
    @Value("${rabbitmq.exchange.json-name}")
    private String jsonExchange;
    @Value("${rabbitmq.queue.dto-name}")
    private String dtoQueue;
    @Value("${rabbitmq.exchange.dto-name}")
    private String dtoExchange;

    @Bean
    public Queue jsonQueue() {
        return new Queue(jsonQueue);
    }
    @Bean
    public TopicExchange jsonExchange() {
        return new TopicExchange(jsonExchange);
    }
    @Bean
    public Queue dtoQueue() {
        return new Queue(dtoQueue);
    }
    @Bean
    public TopicExchange dtoExchange() {
        return new TopicExchange(dtoExchange);
    }
    @Bean
    public Binding jsonBinding() {
        return BindingBuilder.bind(jsonQueue()).to(jsonExchange()).with("*.test");
    }
    @Bean
    public Binding dtoBinding() {
        return BindingBuilder.bind(dtoQueue()).to(dtoExchange()).with("*.test");
    }



    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    // 센더 설정
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
//        rabbitTemplate.setReceiveTimeout(5000L);
        return rabbitTemplate;
    }
    // 리스너 설정
    @Bean
    SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        final SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }
    // 연결설정
    @Bean
    public CachingConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitProperties.getHost());
        connectionFactory.setPort(rabbitProperties.getPort());
        connectionFactory.setUsername(rabbitProperties.getUsername());
        connectionFactory.setPassword(rabbitProperties.getPassword());
        return connectionFactory;
    }
}
