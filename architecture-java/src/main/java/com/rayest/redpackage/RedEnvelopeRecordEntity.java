package com.rayest.redpackage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RedEnvelopeRecordEntity {
    private Integer id;
    private String redEnvelopeNo;
    private Integer amount;
    private String userNo;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
    private String memo;
}
