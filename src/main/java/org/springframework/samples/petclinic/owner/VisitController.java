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

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Consumes;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.views.View;
import jakarta.inject.Inject;
import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author Colin But
 */
@Controller("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final VisitRepository visits;
    private final PetRepository pets;

    @Inject
    public VisitController(VisitRepository visits, PetRepository pets) {
        this.visits = visits;
        this.pets = pets;
    }

    @Get("/new")
    @View("pets/createOrUpdateVisitForm")
    public Map<String, Object> initNewVisitForm(Integer petId) {
        Pet pet = this.pets.findById(petId).orElseThrow(() -> 
            new RuntimeException("Pet not found with id: " + petId));
        Visit visit = new Visit();
        pet.addVisit(visit);
        return Map.of("pet", pet, "visit", visit);
    }

    @Post("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("pets/createOrUpdateVisitForm")
    public HttpResponse<?> processNewVisitForm(Integer ownerId, Integer petId, String date, String description) {
        Pet pet = this.pets.findById(petId).orElseThrow(() -> 
            new RuntimeException("Pet not found with id: " + petId));
        
        Visit visit = new Visit();
        if (date != null && !date.trim().isEmpty()) {
            visit.setDate(LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE));
        }
        visit.setDescription(description);
        
        pet.addVisit(visit);
        this.visits.save(visit);
        return HttpResponse.redirect(URI.create("/owners/" + ownerId));
    }

    @Get("/")
    @View("visitList")
    public Map<String, Object> showVisits(Integer petId) {
        Pet pet = this.pets.findById(petId).orElseThrow(() -> 
            new RuntimeException("Pet not found with id: " + petId));
        return Map.of("pet", pet, "visits", this.visits.findByPetId(petId));
    }
}
