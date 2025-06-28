package org.springframework.samples.petclinic;

import io.micronaut.context.annotation.Context;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.owner.PetType;
import org.springframework.samples.petclinic.vet.Specialty;
import org.springframework.samples.petclinic.vet.Vet;
import org.springframework.samples.petclinic.vet.VetRepository;

@Singleton
public class DatabaseInitializer {

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private VetRepository vetRepository;

    @Inject
    private OwnerRepository ownerRepository;

    public DatabaseInitializer() {
        System.out.println("DatabaseInitializer constructor called");
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("DatabaseInitializer @PostConstruct called");
        // Start initialization in a separate thread to avoid blocking startup
        new Thread(() -> {
            try {
                // Wait for Hibernate to create tables
                Thread.sleep(5000);
                initializeDatabase();
            } catch (Exception e) {
                System.err.println("Error in postConstruct: " + e.getMessage());
                e.printStackTrace();
            }
        }).start();
    }

    @Transactional
    public void initializeDatabase() {
        System.out.println("Starting database initialization...");
        System.out.println("EntityManager is null: " + (entityManager == null));
        System.out.println("VetRepository is null: " + (vetRepository == null));
        
        try {
            // Check if data already exists
            long vetCount = vetRepository.count();
            System.out.println("Found " + vetCount + " existing vets in database");
            
            if (vetCount > 0) {
                System.out.println("Database already has data, skipping initialization");
                return;
            }
            
            System.out.println("Creating programmatic test data...");
            createProgrammaticData();
            
            // Verify data was inserted
            vetCount = vetRepository.count();
            System.out.println("After initialization: Found " + vetCount + " vets in database");
            
            // List all vets to verify
            var allVets = vetRepository.findAll();
            for (var vet : allVets) {
                System.out.println("Vet: " + vet.getFirstName() + " " + vet.getLastName() + " (ID: " + vet.getId() + ")");
            }
            
        } catch (Exception e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public void createProgrammaticData() {
        System.out.println("Creating programmatic test data...");
        
        try {
            // Create specialties
            Specialty radiology = new Specialty();
            radiology.setName("radiology");
            entityManager.persist(radiology);
            System.out.println("Created specialty: radiology");
            
            Specialty surgery = new Specialty();
            surgery.setName("surgery");
            entityManager.persist(surgery);
            System.out.println("Created specialty: surgery");
            
            Specialty dentistry = new Specialty();
            dentistry.setName("dentistry");
            entityManager.persist(dentistry);
            System.out.println("Created specialty: dentistry");
            
            // Create pet types
            PetType cat = new PetType();
            cat.setName("cat");
            entityManager.persist(cat);
            System.out.println("Created pet type: cat");
            
            PetType dog = new PetType();
            dog.setName("dog");
            entityManager.persist(dog);
            System.out.println("Created pet type: dog");
            
            PetType lizard = new PetType();
            lizard.setName("lizard");
            entityManager.persist(lizard);
            System.out.println("Created pet type: lizard");
            
            PetType snake = new PetType();
            snake.setName("snake");
            entityManager.persist(snake);
            System.out.println("Created pet type: snake");
            
            PetType bird = new PetType();
            bird.setName("bird");
            entityManager.persist(bird);
            System.out.println("Created pet type: bird");
            
            PetType hamster = new PetType();
            hamster.setName("hamster");
            entityManager.persist(hamster);
            System.out.println("Created pet type: hamster");
            
            // Create vets
            Vet vet1 = new Vet();
            vet1.setFirstName("James");
            vet1.setLastName("Carter");
            entityManager.persist(vet1);
            System.out.println("Created vet: James Carter");
            
            Vet vet2 = new Vet();
            vet2.setFirstName("Helen");
            vet2.setLastName("Leary");
            vet2.addSpecialty(radiology);
            entityManager.persist(vet2);
            System.out.println("Created vet: Helen Leary with radiology");
            
            Vet vet3 = new Vet();
            vet3.setFirstName("Linda");
            vet3.setLastName("Douglas");
            vet3.addSpecialty(surgery);
            vet3.addSpecialty(dentistry);
            entityManager.persist(vet3);
            System.out.println("Created vet: Linda Douglas with surgery and dentistry");
            
            Vet vet4 = new Vet();
            vet4.setFirstName("Rafael");
            vet4.setLastName("Ortega");
            vet4.addSpecialty(surgery);
            entityManager.persist(vet4);
            System.out.println("Created vet: Rafael Ortega with surgery");
            
            Vet vet5 = new Vet();
            vet5.setFirstName("Henry");
            vet5.setLastName("Stevens");
            vet5.addSpecialty(radiology);
            entityManager.persist(vet5);
            System.out.println("Created vet: Henry Stevens with radiology");
            
            Vet vet6 = new Vet();
            vet6.setFirstName("Sharon");
            vet6.setLastName("Jenkins");
            entityManager.persist(vet6);
            System.out.println("Created vet: Sharon Jenkins");
            
            // Create owners
            Owner owner1 = new Owner();
            owner1.setFirstName("George");
            owner1.setLastName("Franklin");
            owner1.setAddress("110 W. Liberty St.");
            owner1.setCity("Madison");
            owner1.setTelephone("6085551023");
            entityManager.persist(owner1);
            System.out.println("Created owner: George Franklin");
            
            Owner owner2 = new Owner();
            owner2.setFirstName("Betty");
            owner2.setLastName("Davis");
            owner2.setAddress("638 Cardinal Ave.");
            owner2.setCity("Sun Prairie");
            owner2.setTelephone("6085551749");
            entityManager.persist(owner2);
            System.out.println("Created owner: Betty Davis");
            
            Owner owner3 = new Owner();
            owner3.setFirstName("Eduardo");
            owner3.setLastName("Rodriquez");
            owner3.setAddress("2693 Commerce St.");
            owner3.setCity("McFarland");
            owner3.setTelephone("6085558763");
            entityManager.persist(owner3);
            System.out.println("Created owner: Eduardo Rodriquez");
            
            Owner owner4 = new Owner();
            owner4.setFirstName("Harold");
            owner4.setLastName("Davis");
            owner4.setAddress("563 Friendly St.");
            owner4.setCity("Windsor");
            owner4.setTelephone("6085553198");
            entityManager.persist(owner4);
            System.out.println("Created owner: Harold Davis");
            
            Owner owner5 = new Owner();
            owner5.setFirstName("Peter");
            owner5.setLastName("McTavish");
            owner5.setAddress("2387 S. Fair Way");
            owner5.setCity("Madison");
            owner5.setTelephone("6085552765");
            entityManager.persist(owner5);
            System.out.println("Created owner: Peter McTavish");
            
            System.out.println("Programmatic data creation completed successfully");
            
        } catch (Exception e) {
            System.err.println("Error in createProgrammaticData: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
} 