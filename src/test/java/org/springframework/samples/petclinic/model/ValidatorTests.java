/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.model;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.owner.PetValidator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michael Isvy
 * Simple test to make sure that Bean Validation is working
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
class ValidatorTests {

    private Validator createValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }

    @Test
    void shouldNotValidateWhenFirstNameEmpty() {

        Owner person = new Owner();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Owner> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    void shouldValidateWhenFirstNameNotEmpty() {

        Owner person = new Owner();
        person.setFirstName("John");
        person.setLastName("smith");

        Validator validator = createValidator();
        Set<ConstraintViolation<Owner>> constraintViolations = validator.validate(person);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

    @Test
    void shouldNotValidateWhenPetNameEmpty() {

        Pet pet = new Pet();
        pet.setName("");

        Validator validator = createValidator();
        Set<ConstraintViolation<Pet>> constraintViolations = validator.validate(pet);

        assertThat(constraintViolations.size()).isEqualTo(1);
        ConstraintViolation<Pet> violation = constraintViolations.iterator().next();
        assertThat(violation.getPropertyPath().toString()).isEqualTo("name");
        assertThat(violation.getMessage()).isEqualTo("must not be empty");
    }

    @Test
    void shouldValidateWhenPetNameNotEmpty() {

        Pet pet = new Pet();
        pet.setName("Max");

        Validator validator = createValidator();
        Set<ConstraintViolation<Pet>> constraintViolations = validator.validate(pet);

        assertThat(constraintViolations.size()).isEqualTo(0);
    }

}
