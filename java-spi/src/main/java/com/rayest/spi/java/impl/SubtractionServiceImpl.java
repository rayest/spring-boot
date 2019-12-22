package com.rayest.spi.java.impl;

import com.rayest.spi.java.CalculatorService;

public class SubtractionServiceImpl implements CalculatorService {

    public long calculate(long a, long b) {
        return a - b;
    }
}
