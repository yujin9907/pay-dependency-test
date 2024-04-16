package com.example.demo.service;

import com.example.demo.domain.MessageTempLogRepository;
import com.example.demo.domain.MessageTempRepository;
import com.example.demo.dto.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RabbitMqConsumerService {

    private final MessageTempRepository messageTempRepository;
    private final MessageTempLogRepository messageTempLogRepository;

    @RabbitListener(queues = "json")
    public void receiveJson(String msg) {
        log.error("받음-json");
        log.info(msg);
    }

//    @RabbitListener(queues = "json")
//    public void receiveJson(Message dto) {
//        log.error("받음-json2");
//        log.info("receive - json {}", dto.toString());
//    }

    @RabbitListener(queues = "dto")
    public void receiveDto(MessageDto dto) throws InterruptedException {
        Thread.sleep(5000L);

        log.error("받음-Dto");
        log.info("receive - dto {}", dto.toString());

        messageTempRepository.save(dto.toEntity());
    }

//    @RabbitListener(queues = "dto")
//    public void receiveDtoLog(MessageDto dto) {
//        log.error("받음-Dto-log");
//        messageTempLogRepository.save(dto.toLogEntity());
//    }
}
