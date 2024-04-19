package com.example.demo.domain.pay.dto;


import com.example.demo.domain.rabbit.dto.KcpDto;
import lombok.Getter;

// 결제창에서 결제 요청 시 넘겨주는 데이터
@Getter
public class PayReqDto {
    // 얘가 고객이나 결제창에서 받는 데이터
    private String siteCd;
    private String ordrIdxx;
    private String payMethod;
    private String goodName;
    private int goodMmy;
    private String currency;
    private String shopUserId;
    // 인증 콜백으로 받은 데이터
    private String encData;
    private String encInfo;
    private String tranCd;
    // 결제수단 요청 -> 승인요청 값으로 변경
    private String payType;

    public String getSiteCd() {
        return "T0000";
    }

    public KcpDto.PayReqDto toKcpReq(String certInfo) {
        return KcpDto.PayReqDto.builder()
                .site_cd(this.getSiteCd())
                .order_no(this.ordrIdxx)
                .pay_type(KcpDto.PayReqDto.PayType.valueOf(this.payType))
                .kcp_cert_info(certInfo)
                .enc_data(this.encData)
                .enc_info(this.encInfo)
                .ordr_mony(String.valueOf(this.goodMmy))
                .tran_cd(this.tranCd)
                .build();
    }
}
