package com.example.demo.domain.parts.api;

import com.example.demo.domain.parts.dto.PartsOutDto;
import com.example.demo.domain.parts.dto.PartsQrDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/parts/out")
public class OutController {
    @GetMapping("/check-out")
    public void checkScanOutParts(@ModelAttribute PartsOutDto.CheckReqDto dto) {

    }

    @GetMapping("/check-cancel")
    public void checkScanCancelParts() {

    }
}
