package com.example.demo.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageDto {
    private String title;
    private String message;
}
