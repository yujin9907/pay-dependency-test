package com.example.demo.domain.parts.dto;

import lombok.Getter;
import lombok.Setter;

public class PartsOutDto {
    @Getter
    @Setter
    public static class CheckReqDto {
        private String roNo;
        private Long werUid;
        private Long qrUid;
    }
}
