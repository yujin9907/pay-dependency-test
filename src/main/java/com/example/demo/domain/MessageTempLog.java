package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageTempLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long uid;
    private String title;
    private String message;
}
