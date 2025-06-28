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

import io.micronaut.cache.annotation.Cacheable;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.views.View;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Mark Fisher
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 * @author Dave Syer
 */
@Controller("/vets")
public class VetController {

    private final VetRepository vetRepository;

    @Inject
    public VetController(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Get("/")
    @View("vets/vetList")
    // @Cacheable("vets") - temporarily disabled for debugging
    public Map<String, Object> showVetList() {
        System.out.println("showVetList called (main controller)");
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        List<Vet> allVets = this.vetRepository.findAll();
        System.out.println("Found " + allVets.size() + " vets in database");
        for (Vet vet : allVets) {
            System.out.println("Vet: " + vet.getFirstName() + " " + vet.getLastName() + " (ID: " + vet.getId() + ")");
        }
        
        Vets vets = new Vets();
        vets.getVetList().addAll(allVets);
        return Map.of("vets", vets);
    }

    @Get("/vets.html")
    @View("vets/vetList")
    // @Cacheable("vets") - temporarily disabled for debugging
    public Map<String, Object> showResourcesVetList() {
        System.out.println("showResourcesVetList called (main controller)");
        try {
            // Here we are returning an object of type 'Vets' rather than a collection of Vet
            // objects so it is simpler for Object-Xml mapping
            List<Vet> allVets = this.vetRepository.findAll();
            System.out.println("Found " + allVets.size() + " vets in database");
            for (Vet vet : allVets) {
                System.out.println("Vet: " + vet.getFirstName() + " " + vet.getLastName() + " (ID: " + vet.getId() + ")");
                if (vet.getSpecialties() != null) {
                    System.out.println("  Specialties: " + vet.getSpecialties().size());
                    for (var specialty : vet.getSpecialties()) {
                        System.out.println("    - " + specialty.getName());
                    }
                }
            }
            
            Vets vets = new Vets();
            vets.getVetList().addAll(allVets);
            System.out.println("Returning vets object with " + vets.getVetList().size() + " vets");
            System.out.println("Vets object: " + vets);
            System.out.println("VetList: " + vets.getVetList());
            
            Map<String, Object> result = Map.of("vets", vets);
            System.out.println("Returning result map: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("Error in showResourcesVetList: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}

@Controller("/vets.html")
class VetHtmlController {

    private final VetRepository vetRepository;

    @Inject
    public VetHtmlController(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Get("/")
    @View("vets/vetList")
    // @Cacheable("vets") - temporarily disabled for debugging
    public Map<String, Object> showVetList() {
        System.out.println("showVetList called (VetHtmlController)");
        // Here we are returning an object of type 'Vets' rather than a collection of Vet
        // objects so it is simpler for Object-Xml mapping
        List<Vet> allVets = this.vetRepository.findAll();
        System.out.println("Found " + allVets.size() + " vets in database");
        for (Vet vet : allVets) {
            System.out.println("Vet: " + vet.getFirstName() + " " + vet.getLastName() + " (ID: " + vet.getId() + ")");
        }
        
        Vets vets = new Vets();
        vets.getVetList().addAll(allVets);
        return Map.of("vets", vets);
    }
}
