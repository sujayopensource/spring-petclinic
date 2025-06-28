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
@Controller("/owners/{ownerId}/pets")
public class PetController {

    private final PetRepository pets;
    private final OwnerRepository owners;
    private final PetTypeRepository petTypes;

    @Inject
    public PetController(PetRepository pets, OwnerRepository owners, PetTypeRepository petTypes) {
        this.pets = pets;
        this.owners = owners;
        this.petTypes = petTypes;
    }

    @Get("/new")
    @View("pets/createOrUpdatePetForm")
    public Map<String, Object> initCreationForm(Integer ownerId) {
        System.out.println("initCreationForm called with ownerId: " + ownerId);
        try {
            Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
                new RuntimeException("Owner not found with id: " + ownerId));
            Pet pet = new Pet();
            owner.addPet(pet);
            var types = this.petTypes.findAll();
            System.out.println("Found " + types.size() + " pet types for form");
            return Map.of("owner", owner, "pet", pet, "types", types);
        } catch (Exception e) {
            System.err.println("Error in initCreationForm: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Post("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("pets/createOrUpdatePetForm")
    public HttpResponse<?> processCreationForm(Integer ownerId, String name, String birthDate, Integer typeId) {
        try {
            Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
                new RuntimeException("Owner not found with id: " + ownerId));
            
            Pet pet = new Pet();
            pet.setName(name);
            if (birthDate != null && !birthDate.trim().isEmpty()) {
                pet.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE));
            }
            if (typeId != null) {
                pet.setType(this.petTypes.findById(typeId).orElse(null));
            }
            
            owner.addPet(pet);
            this.pets.save(pet);
            return HttpResponse.redirect(URI.create("/owners/" + ownerId));
        } catch (Exception e) {
            System.err.println("Error in processCreationForm: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Get("/{petId}/edit")
    @View("pets/createOrUpdatePetForm")
    public Map<String, Object> initUpdateForm(Integer ownerId, Integer petId) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
            new RuntimeException("Owner not found with id: " + ownerId));
        Pet pet = this.pets.findById(petId).orElseThrow(() -> 
            new RuntimeException("Pet not found with id: " + petId));
        return Map.of("owner", owner, "pet", pet, "types", this.petTypes.findAll());
    }

    @Post("/{petId}/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("pets/createOrUpdatePetForm")
    public HttpResponse<?> processUpdateForm(Integer ownerId, Integer petId, String name, String birthDate, Integer typeId) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
            new RuntimeException("Owner not found with id: " + ownerId));
        Pet pet = this.pets.findById(petId).orElseThrow(() -> 
            new RuntimeException("Pet not found with id: " + petId));
        
        pet.setName(name);
        if (birthDate != null && !birthDate.trim().isEmpty()) {
            pet.setBirthDate(LocalDate.parse(birthDate, DateTimeFormatter.ISO_LOCAL_DATE));
        }
        if (typeId != null) {
            pet.setType(this.petTypes.findById(typeId).orElse(null));
        }
        pet.setOwner(owner);
        
        this.pets.save(pet);
        return HttpResponse.redirect(URI.create("/owners/" + ownerId));
    }
}
