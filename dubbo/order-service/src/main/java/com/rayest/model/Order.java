package com.rayest.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Order implements Serializable {
    private int id;
    private String userId;
    private UserAddress address;
}
