package com.subhakar.spring.calculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NetPriceCalculator implements PriceCalculator {
    @Override
    public double calculate(double price, int quantity) {
        log.info("Net based price calculation with input parameters [singlePrice = {},quantity= {} ], NO tax applied.", price, quantity);
        double result = Math.round(price * quantity);
        log.info("Calculation result is {}", result);
        return result;
    }
}
