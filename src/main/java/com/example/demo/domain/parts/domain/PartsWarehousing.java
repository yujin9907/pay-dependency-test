package com.example.demo.domain.parts.domain;

import com.example.demo.domain.AuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(PartsWarehousingId.class)
public class PartsWarehousing extends AuditingEntity {
    @Id
    private String dlrCd;
    @Id
    private String sysWerDt;
    @Id
    private Integer werSeq;
    private String werDt;
    private String ptsInvoiceNo;
    private String ptsCd;
    private String ptsNm;
    private Integer ptsQty;
    private Integer ptsUnitPrice;
    private Integer ptsPrice;
    private String orderNo;
    @Column(name = "uid")
    private Long werUid;
}
