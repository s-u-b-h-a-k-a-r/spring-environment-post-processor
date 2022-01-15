package com.subhakar.spring.processor;

import static org.springframework.core.env.StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class PriceCalculationEnvironmentProcessor implements EnvironmentPostProcessor {
    private static final String PREFIX = "com.subhakar.spring.";
    private static final String CALCULATION_MODE = "calculation_mode";
    private static final String GROSS_CALCULATION_TAX_RATE = "gross_calculation_tax_rate";
    private static final String CALCULATION_MODE_DEFAULT_VALUE = "NET";
    private static final double GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE = 0;
    private List<String> names = Arrays.asList(CALCULATION_MODE, GROSS_CALCULATION_TAX_RATE);
    private static Map<String, Object> defaults = new LinkedHashMap<>();

    static {
        defaults.put(CALCULATION_MODE, CALCULATION_MODE_DEFAULT_VALUE);
        defaults.put(GROSS_CALCULATION_TAX_RATE, GROSS_CALCULATION_TAX_RATE_DEFAULT_VALUE);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        PropertySource<?> system = environment.getPropertySources()
                .get(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME);
        Map<String, Object> prefixed = new LinkedHashMap<>();
        if (!containsPriceProperties(system)) {
            prefixed = names.stream().collect(Collectors.toMap(this::rename, this::getDefaultValue));
            environment.getPropertySources()
                    .addAfter(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, new MapPropertySource("prefixer", prefixed));
            environment.getPropertySources().stream().forEach(System.out::println);
            return;
        }
        prefixed = names.stream()
                .collect(Collectors.toMap(this::rename, system::getProperty));
        environment.getPropertySources()
                .addAfter(SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, new MapPropertySource("prefixer", prefixed));
    }

    private boolean containsPriceProperties(PropertySource<?> system) {
        return system.containsProperty(CALCULATION_MODE) && system.containsProperty(GROSS_CALCULATION_TAX_RATE);
    }

    private Object getDefaultValue(String key) {
        return defaults.get(key);
    }

    private String rename(String key) {
        return PREFIX + key.replaceAll("\\_", ".");
    }
}
