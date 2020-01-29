package com.rayest.redpackage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RedEnvelopeVO {
    private String userNo;
    private Integer amount;
    private LocalDateTime createdTime;
}
