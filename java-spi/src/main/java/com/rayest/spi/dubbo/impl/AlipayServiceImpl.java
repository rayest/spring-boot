package com.rayest.spi.dubbo.impl;

import com.rayest.spi.dubbo.PayService;

public class AlipayServiceImpl implements PayService {
    public void pay() {
        System.out.println("Pay with Alipay.");
    }
}
