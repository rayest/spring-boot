package com.rayest.spi.dubbo;

import com.alibaba.dubbo.common.extension.SPI;

@SPI("impl")
public interface PayService {
    void pay();
}
