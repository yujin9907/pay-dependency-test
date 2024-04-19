package com.example.demo.domain.rabbit.dto;

import com.example.demo.domain.rabbit.domain.MessageTemp;
import com.example.demo.domain.rabbit.domain.MessageTempLog;
import lombok.*;

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
                .message(this.message)
                .build();
    }
}
