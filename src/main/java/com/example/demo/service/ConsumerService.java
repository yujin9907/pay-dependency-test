package com.example.demo.service;

import com.example.demo.domain.MessageTemp;
import com.example.demo.domain.MessageTempRepository;
import com.example.demo.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final MessageTempRepository messageTempRepository;

    @RabbitListener(queues = "test")
    public void receiveJson(String msg) {
        log.error("받음-json");
        log.info(msg);
    }

    @RabbitListener(queues = "test")
    public void receiveJson(Message dto) {
        log.error("받음-json2");
        log.info("receive - json {}", dto.toString());
    }

    @RabbitListener(queues = "test")
    public void receiveDto(Message dto) {
        log.error("받음-Dto");
        log.info("receive - dto {}", dto.toString());
    }
}
