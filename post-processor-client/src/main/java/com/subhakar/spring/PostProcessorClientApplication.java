package com.subhakar.spring;

import com.subhakar.spring.calculator.PriceCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PostProcessorClientApplication {
    private PriceCalculator priceCalculator;

    @Autowired
    public PostProcessorClientApplication(PriceCalculator priceCalculator) {
        this.priceCalculator = priceCalculator;
    }

    public static void main(String[] args) {
        SpringApplication.run(PostProcessorClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner run() {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                priceCalculator.calculate(1, 2);
            }
        };
    }
}
