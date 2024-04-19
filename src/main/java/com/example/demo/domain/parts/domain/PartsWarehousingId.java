package com.example.demo.domain.parts.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PartsWarehousingId implements Serializable {
    private String dlrCd;
    private String sysWerDt;
    private Integer werSeq;
}
