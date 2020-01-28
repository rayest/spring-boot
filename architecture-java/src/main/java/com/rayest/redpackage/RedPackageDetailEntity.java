package com.rayest.redpackage;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class RedPackageDetailEntity {
    private Integer id;
    private String redPackageNo;
    private Integer amount;
    private String userNo;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
