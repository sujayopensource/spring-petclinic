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
package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.PetTypeFormatter;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTests {

    @Mock
    private PetRepository pets;

    private PetTypeFormatter petTypeFormatter;

    @BeforeEach
    void setup() {
        this.petTypeFormatter = new PetTypeFormatter(pets);
    }

    @Test
    void testPrint() {
        PetType petType = new PetType();
        petType.setName("Hamster");
        String petTypeName = this.petTypeFormatter.print(petType, java.util.Locale.ENGLISH);
        assertEquals("Hamster", petTypeName);
    }

    @Test
    void testParse() throws java.text.ParseException {
        PetType bird = new PetType();
        bird.setName("Bird");
        when(this.pets.findPetTypes()).thenReturn(Arrays.asList(bird));
        
        PetType petType = this.petTypeFormatter.parse("Bird", java.util.Locale.ENGLISH);
        assertEquals("Bird", petType.getName());
    }

    @Test
    void testParseObjectNotFound() {
        PetType bird = new PetType();
        bird.setName("Bird");
        when(this.pets.findPetTypes()).thenReturn(Arrays.asList(bird));
        
        assertThrows(java.text.ParseException.class, () -> {
            this.petTypeFormatter.parse("Fish", java.util.Locale.ENGLISH);
        });
    }

}
