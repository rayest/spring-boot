package com.rayest.spi.java;

import com.sun.tools.javac.util.ServiceLoader;

public class JavaSPITest {
    public static void main(String[] args) {
        ServiceLoader<CalculatorService> serviceLoader = ServiceLoader.load(CalculatorService.class);
        for (CalculatorService calculatorService : serviceLoader) {
            long result = calculatorService.calculate(1, 2);
            System.out.println(result);
        }
    }
}
