package com.rayest.api.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Order implements Serializable {
    private int id;
    private String orderNo;
    private String userNo;
}
