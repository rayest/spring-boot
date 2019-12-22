package com.rayest.spi.impl;

import com.rayest.spi.CalculatorService;

public class AddServiceImpl implements CalculatorService {

    public long calculate(long a, long b) {
        return a + b;
    }
}
