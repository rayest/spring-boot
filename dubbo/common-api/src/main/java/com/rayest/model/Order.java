package com.rayest.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Order {
    private int id;
    private String orderNo;
    private String userNo;
}
