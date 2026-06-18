package com.example.mini_devops.beans;

import com.example.mini_devops.repository.ProductRepository;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.binder.MeterBinder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class MetricsConfig {

    private final ProductRepository repository;

    @Bean
    public MeterBinder productMetrics() {

        return registry -> {

            Gauge.builder(
                    "products.count",
                    repository,
                    repo -> (double) repo.count()
            ).register(registry);

        };
    }
}