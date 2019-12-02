package com.rayest.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Data
@Accessors(chain = true)
public class Order {
    private Long orderId;
    private String desc;

    @Override
    public String toString() {
        return "Order{" + "orderId='" + orderId + '\'' + ", desc='" + desc + '\'' + '}';
    }

    public static List<Order> buildOrders() {

        List<Order> orderList = new ArrayList<Order>();
        orderList.add(new Order().setOrderId(1039L).setDesc("创建"));
        orderList.add(new Order().setOrderId(1039L).setDesc("付款"));
        orderList.add(new Order().setOrderId(1039L).setDesc("推送"));
        orderList.add(new Order().setOrderId(1039L).setDesc("完成"));

        orderList.add(new Order().setOrderId(1065L).setDesc("创建"));
        orderList.add(new Order().setOrderId(1065L).setDesc("付款"));

        orderList.add(new Order().setOrderId(7235L).setDesc("创建"));
        orderList.add(new Order().setOrderId(7235L).setDesc("付款"));
        return orderList;
    }
}
