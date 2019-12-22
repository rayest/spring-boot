package com.rayest.spi.dubbo;

import com.alibaba.dubbo.common.extension.ExtensionLoader;

public class DubboSPITest {
    public static void main(String[] args) {
        ExtensionLoader<PayService> loader = ExtensionLoader.getExtensionLoader(PayService.class);
        PayService alipay = loader.getExtension("alipay");
        alipay.pay();

        PayService wechatPay = loader.getExtension("wechatPay");
        wechatPay.pay();
    }
}
