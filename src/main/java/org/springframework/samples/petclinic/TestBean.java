package org.springframework.samples.petclinic;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;

@Context
public class TestBean {

    public TestBean() {
        System.out.println("TestBean constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("TestBean @PostConstruct called");
    }
} 