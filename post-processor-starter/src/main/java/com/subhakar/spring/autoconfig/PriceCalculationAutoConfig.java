package com.subhakar.spring.autoconfig;

import com.subhakar.spring.calculator.GrossPriceCalculator;
import com.subhakar.spring.calculator.NetPriceCalculator;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class PriceCalculationAutoConfig {

    @Bean
    @ConditionalOnProperty(name = "com.subhakar.spring.calculation.mode", havingValue = "GROSS")
    @ConditionalOnMissingBean
    public GrossPriceCalculator getGrossPriceCalculator() {
        return new GrossPriceCalculator();
    }

    @Bean
    @ConditionalOnProperty(name = "com.subhakar.spring.calculation.mode", havingValue = "NET")
    @ConditionalOnMissingBean
    public NetPriceCalculator getNetPriceCalculator() {
        return new NetPriceCalculator();
    }
}
