package org.springframework.samples.petclinic.system;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetTypeRepository;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.VetRepository;

import java.util.HashMap;
import java.util.Map;

@Controller("/test")
public class TestController {

    @Inject
    private VetRepository vetRepository;

    @Inject
    private OwnerRepository ownerRepository;

    @Inject
    private PetTypeRepository petTypeRepository;

    @Get("/")
    public Map<String, Object> test() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Check vet data
            long vetCount = vetRepository.count();
            result.put("vetCount", vetCount);
            
            var allVets = vetRepository.findAll();
            result.put("vets", allVets);
            
            // Check owner data
            long ownerCount = ownerRepository.count();
            result.put("ownerCount", ownerCount);
            
            var allOwners = ownerRepository.findAll();
            result.put("owners", allOwners);
            
            // Check pet type data
            long petTypeCount = petTypeRepository.count();
            result.put("petTypeCount", petTypeCount);
            
            var allPetTypes = petTypeRepository.findAll();
            result.put("petTypes", allPetTypes);
            
            // Add detailed vet information
            Map<String, Object> vetDetails = new HashMap<>();
            for (var vet : allVets) {
                Map<String, Object> vetInfo = new HashMap<>();
                vetInfo.put("id", vet.getId());
                vetInfo.put("firstName", vet.getFirstName());
                vetInfo.put("lastName", vet.getLastName());
                vetInfo.put("specialties", vet.getSpecialties());
                vetDetails.put("vet_" + vet.getId(), vetInfo);
            }
            result.put("vetDetails", vetDetails);
            
            result.put("success", true);
            result.put("message", "Database state retrieved successfully");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            e.printStackTrace();
        }
        
        return result;
    }

    @Get("/init")
    public Map<String, Object> initDatabase() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // Import the DatabaseInitializer and call its method
            org.springframework.samples.petclinic.DatabaseInitializer initializer = 
                new org.springframework.samples.petclinic.DatabaseInitializer();
            
            // Call the initialization method
            initializer.initializeDatabase();
            
            result.put("success", true);
            result.put("message", "Database initialization completed");
            
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("errorType", e.getClass().getSimpleName());
            e.printStackTrace();
        }
        
        return result;
    }
} 