package com.example.demo.service;

import com.example.demo.dto.PayReqDto;
import com.example.demo.dto.KcpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayService {
    private final RestTemplate restTemplate;

    public void sendPay(PayReqDto dto) {
        String target_URL = "https://stg-spl.kcp.co.kr/gw/enc/v1/payment";  // 승인요청 개발서버

        KcpDto.PayReqDto kcpJson = dto.toKcpReq("인증");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept-Charset", "UTF-8");

        HttpEntity<KcpDto.PayReqDto> request = new HttpEntity<>(kcpJson, headers);

        ResponseEntity<String> kcpResp = restTemplate.exchange(target_URL, HttpMethod.POST, request, String.class);

        log.info("맞나 {}", kcpResp.toString());
    }

    public void sendPayOrigin(PayReqDto dto) throws IOException {
        String target_URL = "https://stg-spl.kcp.co.kr/gw/enc/v1/payment";  // 승인요청 개발서버

        JSONObject json = new JSONObject();

        json.put("tran_cd", dto.getTranCd()); // 결제요청타입
        json.put("site_cd", dto.getSiteCd()); // kcp 발급 사이트 코드 (테스트 : T0000)
        json.put("kcp_cert_info", "kcp_cert_info"); // 인증서 정보
        json.put("enc_data", dto.getEncData()); // 결제창 인증결과 암호화 데이터
        json.put("enc_info", dto.getEncInfo()); // 결제창 인증결과 암호화 데이터
        json.put("ordr_mony", dto.getGoodMmy()); // 금액
        json.put("ordr_no", dto.getOrdrIdxx()); // 주문번호
        json.put("pay_type", dto.getPayType().toString()); // 결제수단

        String temp_req_data = json.toString();
        String req_data = temp_req_data.replace(",",",\r\n");

        String inputLine = null;
        StringBuffer outResult = new StringBuffer();

        // API REQ
        URL url = new URL(target_URL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept-Charset", "UTF-8");

        OutputStream os = conn.getOutputStream();
        os.write(req_data.getBytes("UTF-8"));
        os.flush();

        // API RES
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        while ((inputLine = in.readLine()) != null)
        {
            outResult.append(inputLine);
        }
        conn.disconnect();

        String temp_result = outResult.toString();
        String res_data = temp_result.replace(",",",\r\n");
    }
}
