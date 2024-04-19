package com.example.demo.domain.parts.domain;

import com.example.demo.domain.AuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PartsQrScan extends AuditingEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long qrUid;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wer_uid")
    private PartsWarehousing partsWarehousing;
    @NotNull
    private String dlrCd;
    private String ptsCd;
    private String outRoNo;
    private String outSeq;
    private String outUser;
    private LocalDateTime outDate;
    private String claimRptNo;
    private String claimRegDt;
    private Integer claimSeq;
    private LocalDateTime claimDate;
    private String claimUser;
}
