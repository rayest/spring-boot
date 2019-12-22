package com.rayest.spi.dubbo.impl;

import com.rayest.spi.dubbo.PayService;

public class WechatPayServiceImpl implements PayService {
    public void pay() {
        System.out.println("Pay with Wechat.");
    }
}
