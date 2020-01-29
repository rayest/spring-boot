package com.rayest.redpackage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RedEnvelopeEntity {
    private Integer id;
    private String redPackageNo;
    private String userNo;
    private Integer totalAmount;
    private int totalNumber;
    private int version;
    private int stock;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
