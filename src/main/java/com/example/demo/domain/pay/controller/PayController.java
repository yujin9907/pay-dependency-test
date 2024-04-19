package com.example.demo.domain.pay.controller;

import com.example.demo.domain.pay.dto.PayReqDto;
import com.example.demo.domain.pay.service.PayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PayController {
    private final PayService payService;

    // 승인 요청
    @PostMapping("pay")
    public String sendPay(@RequestBody PayReqDto dto) {
        payService.sendPay(dto);
        return "ok";
    }
}
