package com.example.demo.domain.rabbit.service;

import com.example.demo.domain.rabbit.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Value("${rabbitmq.exchange.json-name}")
    private String jsonExchange;
    @Value("${rabbitmq.exchange.dto-name}")
    private String dtoExchange;
    @Value("${rabbitmq.routing.key}")
    private String key;

    public void sendMessageTestDto(MessageDto messageDto) throws JsonProcessingException {
        log.error("보냄-Dto");
        log.info("보낸내용 {}", messageDto);
        rabbitTemplate.convertAndSend(dtoExchange, "*.test", messageDto);
    }

    public void sendMessageTestJson(MessageDto messageDto) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        String toJson = om.writeValueAsString(messageDto);

        log.error("보냄-json");
        log.error(toJson);
        rabbitTemplate.convertAndSend(jsonExchange, key, toJson);
    }
}
