package com.example.demo.service;

import com.example.demo.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RabbitMqService {

    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.queue.name}")
    private String queueName;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;
    @Value("${rabbitmq.routing.key}")
    private String key;

    public void sendMessageTestDto(MessageDto messageDto) throws JsonProcessingException {
        log.error("보냄-Dto");
        rabbitTemplate.convertAndSend(exchangeName, key, messageDto);
    }

    public void sendMessageTestJson(MessageDto messageDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String toJson = om.writeValueAsString(messageDto);

        log.error("보냄-json");
        log.error(toJson);
        rabbitTemplate.convertAndSend(exchangeName, key, toJson);
    }
}
