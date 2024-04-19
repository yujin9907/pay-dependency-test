package com.example.demo.domain.parts.domain;

import com.example.demo.domain.AuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@IdClass(RepairListId.class)
public class RepairList extends AuditingEntity {
    @Id
    private String dlrCd;
    @Id
    private String roNo;
    @NotNull
    private Integer seq;
    private Integer sortSeq;
    private Integer custSeq;
    private Integer jobNo;
    private String jobCd;
    // labor
    private String lbCd;
    private String lbNm;
    private Integer lbTime;
    private Integer lbQty;
    private Integer lbPrice;
    // pts
    private String ptsCd;
    private String ptsNm;
    private Integer ptsQty;
    private Integer ptsUnitPrice;
    private Integer ptsPrice;
    private String ptsOutStatus;
}
