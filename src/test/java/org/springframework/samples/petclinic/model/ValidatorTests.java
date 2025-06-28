package org.springframework.samples.petclinic.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michael Isvy
 * Simple test to make sure that Bean Validation is working
 * (useful when upgrading to a new version of Hibernate Validator/ Bean Validation)
 */
@SpringBootTest
class ValidatorTests {

    private Validator createValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.afterPropertiesSet();
        return localValidatorFactoryBean;
    }

    @Test
    void shouldNotValidateWhenFirstNameEmpty() {

        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Owner person = new Owner();
        person.setFirstName("");
        person.setLastName("smith");

        Validator validator = createValidator();
        ConstraintViolation<Owner> constraintViolation = validator.validate(person).iterator().next();

        assertThat(constraintViolation.getPropertyPath().toString()).isEqualTo("firstName");
        assertThat(constraintViolation.getMessage()).isEqualTo("must not be empty");
    }

}
