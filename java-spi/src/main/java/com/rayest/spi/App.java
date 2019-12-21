package com.rayest.spi;

import com.sun.tools.javac.util.ServiceLoader;

public class App {
    public static void main(String[] args) {
        ServiceLoader<PrintService> serviceLoader = ServiceLoader.load(PrintService.class);
        for (PrintService printService : serviceLoader) {
            printService.printInfo();
        }
    }
}
