package com.rayest.disruptor;

import com.rayest.common.Note;
import lombok.Data;
import lombok.experimental.Accessors;

@Note("定义消息类，在Disruptor里称为Event，也即系统里生产消费的业务对象")
@Data
@Accessors(chain = true)
public class Product {
    private int id;
    private String name;
    private double weight;
}
