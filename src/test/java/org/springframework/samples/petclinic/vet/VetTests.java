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
package org.springframework.samples.petclinic.vet;

import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.owner.PetTypeFormatter;

import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author Colin But
 */
class VetTests {

    @Test
    void testGetSetId() {
        Vet vet = new Vet();
        vet.setId(1);
        assertThat(vet.getId()).isEqualTo(1);
    }

    @Test
    void testGetSetFirstName() {
        Vet vet = new Vet();
        vet.setFirstName("John");
        assertThat(vet.getFirstName()).isEqualTo("John");
    }

    @Test
    void testGetSetLastName() {
        Vet vet = new Vet();
        vet.setLastName("Doe");
        assertThat(vet.getLastName()).isEqualTo("Doe");
    }

    @Test
    void testGetSetSpecialties() {
        Vet vet = new Vet();
        Specialty specialty = new Specialty();
        specialty.setName("Surgery");
        vet.addSpecialty(specialty);
        assertThat(vet.getSpecialties()).hasSize(1);
        assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("Surgery");
    }

    @Test
    void testGetNrOfSpecialties() {
        Vet vet = new Vet();
        Specialty specialty1 = new Specialty();
        specialty1.setName("Surgery");
        Specialty specialty2 = new Specialty();
        specialty2.setName("Radiology");
        vet.addSpecialty(specialty1);
        vet.addSpecialty(specialty2);
        assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
    }

}
