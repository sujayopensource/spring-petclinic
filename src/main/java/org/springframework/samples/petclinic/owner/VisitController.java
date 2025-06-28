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

import org.springframework.samples.petclinic.visit.Visit;
import org.springframework.samples.petclinic.visit.VisitRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}")
class VisitController {

    private static final String VIEWS_PETS_CREATE_OR_UPDATE_VISIT_FORM = "pets/createOrUpdateVisitForm";
    private final PetRepository pets;
    private final VisitRepository visits;

    public VisitController(PetRepository pets, VisitRepository visits) {
        this.pets = pets;
        this.visits = visits;
    }

    @ModelAttribute("pet")
    public Pet findPet(@PathVariable("petId") int petId) {
        return this.pets.findById(petId);
    }

    @GetMapping("/visits/new")
    public String initNewVisitForm(@PathVariable("petId") int petId, ModelMap model) {
        Visit visit = new Visit();
        Pet pet = this.pets.findById(petId);
        pet.addVisit(visit);
        visit.setPetId(petId);
        model.put("visit", visit);
        return VIEWS_PETS_CREATE_OR_UPDATE_VISIT_FORM;
    }

    @PostMapping("/visits/new")
    public String processNewVisitForm(@PathVariable("ownerId") int ownerId, @PathVariable("petId") int petId, @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return VIEWS_PETS_CREATE_OR_UPDATE_VISIT_FORM;
        } else {
            Pet pet = this.pets.findById(petId);
            pet.addVisit(visit);
            this.visits.save(visit);
            return "redirect:/owners/{ownerId}";
        }
    }

}
