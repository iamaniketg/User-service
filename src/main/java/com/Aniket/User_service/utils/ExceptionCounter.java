package com.Aniket.User_service.utils;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class ExceptionCounter {

    private final Counter exceptionCounter;

    public ExceptionCounter(MeterRegistry meterRegistry) {
        this.exceptionCounter = Counter.builder("app_exceptions_total")
                .description("Total number of exceptions in the application")
                .tag("type", "runtime")   // you can add labels like type, service, etc.
                .register(meterRegistry);
    }

    public void increment() {
        this.exceptionCounter.increment();
    }
}
