package com.example.demo.domain.rabbit.repository;

import com.example.demo.domain.rabbit.domain.MessageTemp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageTempRepository extends JpaRepository<MessageTemp, Long> {
}
