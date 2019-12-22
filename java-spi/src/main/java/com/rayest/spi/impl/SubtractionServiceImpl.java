package com.rayest.spi.impl;

import com.rayest.spi.CalculatorService;

public class SubtractionServiceImpl implements CalculatorService {

    public long calculate(long a, long b) {
        return a - b;
    }
}
