package com.subhakar.spring.calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class GrossPriceCalculator implements PriceCalculator {
    @Value("${com.subhakar.spring.gross.calculation.tax.rate}")
    double taxRate;

    @Override
    public double calculate(double price, int quantity) {
        log.info("Gross based price calculation with input parameters [singlePrice = {},quantity= {} ], {} percent tax applied.", price, quantity, taxRate * 100);
        double netPrice = price * quantity;
        double result = Math.round(netPrice * (1 + taxRate));
        log.info("Calculation result is {}", result);
        return result;
    }
}
