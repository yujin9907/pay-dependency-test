package com.example.demo.domain.rabbit.repository;

import com.example.demo.domain.rabbit.domain.MessageTempLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTempLogRepository extends JpaRepository<MessageTempLog, Long> {
}
