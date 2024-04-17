package com.example.demo.service;

import com.example.demo.domain.MessageTempLogRepository;
import com.example.demo.domain.MessageTempRepository;
import com.example.demo.dto.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqConsumerService {

    private final MessageTempRepository messageTempRepository;
    private final MessageTempLogRepository messageTempLogRepository;

    @RabbitListener(queues = "test.json")
    public void receiveJson(String msg) { // Message dto 메타정보까지 같이 나옴
        log.error("받음-json");
        log.info(msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "test.dto", durable = "true"),
            exchange = @Exchange(value = "dto.topic", type = ExchangeTypes.TOPIC),
            key = "test")
    ) // 여러가지 옵션 가능
    @SendTo("test.json") // 재전송
    public String receiveDto(MessageDto dto) throws InterruptedException, JsonProcessingException {
        Thread.sleep(10000L);

        log.error("받음-Dto");
        log.info("receive - dto {}", dto.toString());

        messageTempRepository.save(dto.toEntity());

        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(dto);
    }

    // 한 큐에서 다중 메서드 리스너를 구현하고 싶으면
    // https://docs.spring.io/spring-amqp/reference/amqp/receiving-messages/async-annotation-driven/method-selection.html
    public void receiveDtoLog(MessageDto dto) {
        log.error("받음-Dto-log");
        log.info("뭐 받음 {}", dto);
        messageTempLogRepository.save(dto.toLogEntity());
    }
}
