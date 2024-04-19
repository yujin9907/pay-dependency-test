package com.example.demo.domain.rabbit.api;

import com.example.demo.domain.rabbit.dto.MessageDto;
import com.example.demo.domain.rabbit.service.RabbitMqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Tag(name = "Rabbit MQ", description = "ampq 이것저것 테스트")
public class RabbitController {

    private final RabbitMqService rabbitMqService;

    @GetMapping("/")
    @Operation(summary = "health check", description = "그냥 되는지")
    @ApiResponse(responseCode = "200", description = "go home")
    public String test() {
        return "go home";
    }

    @GetMapping("/send")
    @Operation(summary = "MQ send DTO", description = "dto 그대로 메시지에 쌓기")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = MessageDto.class))}),
            @ApiResponse(responseCode = "500", description = "파싱 에러"),
    })
    public String sendDto(@RequestBody MessageDto messageDto) {
        try {
            rabbitMqService.sendMessageTestDto(messageDto);
            return "ok";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/send-json")
    @Operation(summary = "MQ send json", description = "json (String) 으로 메시지에 쌓기")
    public String sendTest2(@RequestBody MessageDto messageDto) {
        try {
            rabbitMqService.sendMessageTestJson(messageDto);
            return "ok";
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
