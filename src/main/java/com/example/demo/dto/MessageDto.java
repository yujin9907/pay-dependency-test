package com.example.demo.dto;

import com.example.demo.domain.MessageTemp;
import lombok.*;
import org.springframework.stereotype.Service;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {
    private String title;
    private String message;
    public MessageTemp toEntity() {
        return MessageTemp.builder()
                .title(this.title)
                .message(this.message)
                .build();
    }
}
