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
import java.util.List;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 * @author Colin But
 */
@Controller("/owners")
public class OwnerController {

    private final OwnerRepository owners;
    private final PetRepository pets;

    @Inject
    public OwnerController(OwnerRepository clinicService, PetRepository pets) {
        this.owners = clinicService;
        this.pets = pets;
    }

    @Get("/find")
    @View("owners/findOwners")
    public Map<String, Object> initFindForm() {
        return Map.of("owner", new Owner());
    }

    @Get("/")
    @View("owners/ownersList")
    public Map<String, Object> processFindForm(String lastName) {
        // find owners by last name
        List<Owner> results;
        if (lastName == null || lastName.trim().isEmpty()) {
            // If no lastName provided, return empty results
            results = List.of();
        } else {
            results = this.owners.findByLastName(lastName.trim());
        }
        
        if (results.isEmpty()) {
            // no owners found
            return Map.of("owner", new Owner(), "selections", results);
        } else if (results.size() == 1) {
            // 1 owner found
            Owner owner = results.get(0);
            return Map.of("owner", owner);
        } else {
            // multiple owners found
            return Map.of("selections", results);
        }
    }

    @Get("/{ownerId}")
    @View("owners/ownerDetails")
    public Map<String, Object> showOwner(Integer ownerId) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
            new RuntimeException("Owner not found with id: " + ownerId));
        return Map.of("owner", owner);
    }

    @Get("/new")
    @View("owners/createOrUpdateOwnerForm")
    public Map<String, Object> initCreationForm() {
        return Map.of("owner", new Owner());
    }

    @Post("/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("owners/createOrUpdateOwnerForm")
    public HttpResponse<?> processCreationForm(String firstName, String lastName, String address, String city, String telephone) {
        Owner owner = new Owner();
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        
        this.owners.save(owner);
        return HttpResponse.redirect(URI.create("/owners/" + owner.getId()));
    }

    @Get("/{ownerId}/edit")
    @View("owners/createOrUpdateOwnerForm")
    public Map<String, Object> initUpdateOwnerForm(Integer ownerId) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
            new RuntimeException("Owner not found with id: " + ownerId));
        return Map.of("owner", owner);
    }

    @Post("/{ownerId}/edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @View("owners/createOrUpdateOwnerForm")
    public HttpResponse<?> processUpdateOwnerForm(Integer ownerId, String firstName, String lastName, String address, String city, String telephone) {
        Owner owner = this.owners.findById(ownerId).orElseThrow(() -> 
            new RuntimeException("Owner not found with id: " + ownerId));
        
        owner.setFirstName(firstName);
        owner.setLastName(lastName);
        owner.setAddress(address);
        owner.setCity(city);
        owner.setTelephone(telephone);
        
        this.owners.save(owner);
        return HttpResponse.redirect(URI.create("/owners/" + ownerId));
    }
}
