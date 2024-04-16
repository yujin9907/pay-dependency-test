package com.example.demo.dto;

import com.example.demo.domain.MessageTemp;
import com.example.demo.domain.MessageTempLog;
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

    public MessageTempLog toLogEntity() {
        return MessageTempLog.builder()
                .title(this.title)
                .message(this.title)
                .build();
    }
}
