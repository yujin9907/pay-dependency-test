package com.example.demo.domain.rabbit.dto;

import lombok.*;


public class KcpDto {

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PayReqDto {
        private String tran_cd;  // 결제요청타입
        private String site_cd;  // kcp 발급 사이트 코드 (테스트 : T0000)
        private String kcp_cert_info;  // 인증서 정보
        private String enc_data;  // 결제창 인증결과 암호화 데이터
        private String enc_info;  // 결제창 인증결과 암호화 데이터
        private String ordr_mony;  // 금액
        private String order_no; // 주문번호
        private PayType pay_type; // 결제수단

        public enum PayType {
            PACA, PABK, PACC, PAMC,
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PayRespDto {
        private String res_cd;
        private String res_msg;
        private String res_en_msg;
        private String pay_method;
        private String tno;
        private String amount;
    }
}

