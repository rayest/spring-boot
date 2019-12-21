package com.rayest.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class OrderEntity implements Serializable {
    private Integer id;
    private String orderNo;
    private String userNo;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;
}
