package com.example.demo.controller;

import com.example.demo.dto.MessageDto;
import com.example.demo.service.RabbitMqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "test", description = "test doc")
public class MainController {

    private final RabbitMqService rabbitMqService;

    @GetMapping("/")
    public String test() {
        return "go home";
    }

    @GetMapping("/send")
    public String sendTest(@ModelAttribute MessageDto messageDto) {
        try {
            rabbitMqService.sendMessageTest(messageDto);
            return "ok";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
