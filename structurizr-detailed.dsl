workspace {
    name: "PetClinic Micronaut - Detailed Architecture"
    description: "Detailed component-level architecture for the PetClinic Micronaut application"

    model {
        # People
        user = person "Pet Clinic User" "A user of the PetClinic application"

        # Software Systems
        petclinic = softwareSystem "PetClinic Application" "Allows users to manage pet owners, pets, veterinarians, and visits" {
            
            # Web Layer Components
            ownerController = component "Owner Controller" "Handles owner CRUD operations" "Micronaut Controller" {
                initFindForm = component "initFindForm" "Shows find owners form" "GET /owners/find"
                processFindForm = component "processFindForm" "Processes owner search" "GET /owners/"
                showOwner = component "showOwner" "Shows owner details" "GET /owners/{id}"
                initCreationForm = component "initCreationForm" "Shows create owner form" "GET /owners/new"
                processCreationForm = component "processCreationForm" "Creates new owner" "POST /owners/new"
            }

            petController = component "Pet Controller" "Manages pet creation and updates" "Micronaut Controller" {
                initCreationForm = component "initCreationForm" "Shows create pet form" "GET /owners/{id}/pets/new"
                processCreationForm = component "processCreationForm" "Creates new pet" "POST /owners/{id}/pets/new"
                initUpdateForm = component "initUpdateForm" "Shows edit pet form" "GET /owners/{id}/pets/{petId}/edit"
                processUpdateForm = component "processUpdateForm" "Updates pet" "POST /owners/{id}/pets/{petId}/edit"
            }

            vetController = component "Vet Controller" "Displays veterinarian information" "Micronaut Controller" {
                showVetList = component "showVetList" "Shows all veterinarians" "GET /vets/"
                showResourcesVetList = component "showResourcesVetList" "Shows veterinarians page" "GET /vets.html"
            }

            visitController = component "Visit Controller" "Handles pet visit scheduling" "Micronaut Controller" {
                initNewVisitForm = component "initNewVisitForm" "Shows create visit form" "GET /owners/{id}/pets/{petId}/visits/new"
                processNewVisitForm = component "processNewVisitForm" "Creates new visit" "POST /owners/{id}/pets/{petId}/visits/new"
                showVisits = component "showVisits" "Shows pet visits" "GET /owners/{id}/pets/{petId}/visits/"
            }

            # Business Layer Components
            databaseInitializer = component "Database Initializer" "Initializes database with sample data" "Micronaut Component" {
                initializeDatabase = component "initializeDatabase" "Main initialization method" "Transactional"
                createProgrammaticData = component "createProgrammaticData" "Creates sample data" "Transactional"
            }

            # Data Layer Components
            ownerRepository = component "Owner Repository" "Data access for owners" "Micronaut Data Repository" {
                findByLastName = component "findByLastName" "Finds owners by last name" "Query Method"
                findById = component "findById" "Finds owner by ID" "Query Method"
                save = component "save" "Saves owner" "CRUD Method"
            }

            petRepository = component "Pet Repository" "Data access for pets" "Micronaut Data Repository" {
                findById = component "findById" "Finds pet by ID" "Query Method"
                save = component "save" "Saves pet" "CRUD Method"
            }

            vetRepository = component "Vet Repository" "Data access for veterinarians" "Micronaut Data Repository" {
                findAll = component "findAll" "Finds all vets" "Query Method"
                count = component "count" "Counts vets" "Query Method"
            }

            visitRepository = component "Visit Repository" "Data access for visits" "Micronaut Data Repository" {
                findByPetId = component "findByPetId" "Finds visits by pet ID" "Query Method"
                save = component "save" "Saves visit" "CRUD Method"
            }

            petTypeRepository = component "Pet Type Repository" "Data access for pet types" "Micronaut Data Repository" {
                findAll = component "findAll" "Finds all pet types" "Query Method"
                findById = component "findById" "Finds pet type by ID" "Query Method"
            }

            # Domain Model Components
            owner = component "Owner" "Pet owner entity" "JPA Entity" {
                firstName = component "firstName" "Owner's first name" "String"
                lastName = component "lastName" "Owner's last name" "String"
                address = component "address" "Owner's address" "String"
                city = component "city" "Owner's city" "String"
                telephone = component "telephone" "Owner's phone number" "String"
                pets = component "pets" "Owner's pets" "OneToMany"
            }

            pet = component "Pet" "Pet entity" "JPA Entity" {
                name = component "name" "Pet's name" "String"
                birthDate = component "birthDate" "Pet's birth date" "LocalDate"
                type = component "type" "Pet's type" "ManyToOne"
                owner = component "owner" "Pet's owner" "ManyToOne"
                visits = component "visits" "Pet's visits" "OneToMany"
            }

            vet = component "Vet" "Veterinarian entity" "JPA Entity" {
                firstName = component "firstName" "Vet's first name" "String"
                lastName = component "lastName" "Vet's last name" "String"
                specialties = component "specialties" "Vet's specialties" "ManyToMany"
            }

            visit = component "Visit" "Pet visit entity" "JPA Entity" {
                date = component "date" "Visit date" "LocalDate"
                description = component "description" "Visit description" "String"
                pet = component "pet" "Visited pet" "ManyToOne"
            }

            petType = component "Pet Type" "Pet type entity" "JPA Entity" {
                name = component "name" "Pet type name" "String"
            }

            specialty = component "Specialty" "Veterinarian specialty entity" "JPA Entity" {
                name = component "name" "Specialty name" "String"
            }

            # Presentation Layer Components
            thymeleafEngine = component "Thymeleaf Engine" "Server-side template engine" "Thymeleaf" {
                layoutTemplate = component "layout.html" "Main layout template" "Thymeleaf Template"
                ownerTemplates = component "Owner Templates" "Owner-related templates" "Thymeleaf Templates"
                petTemplates = component "Pet Templates" "Pet-related templates" "Thymeleaf Templates"
                vetTemplates = component "Vet Templates" "Vet-related templates" "Thymeleaf Templates"
            }

            staticResources = component "Static Resources" "CSS, JavaScript, and images" "Static Files" {
                css = component "CSS" "Stylesheets" "CSS Files"
                js = component "JavaScript" "Client-side scripts" "JS Files"
                images = component "Images" "Application images" "Image Files"
            }
        }

        database = softwareSystem "H2 Database" "In-memory database for storing application data" "Database" {
            tables = component "Database Tables" "Application tables" "SQL Tables" {
                owners = component "owners" "Owner data table" "SQL Table"
                pets = component "pets" "Pet data table" "SQL Table"
                vets = component "vets" "Vet data table" "SQL Table"
                visits = component "visits" "Visit data table" "SQL Table"
                types = component "types" "Pet type data table" "SQL Table"
                specialties = component "specialties" "Specialty data table" "SQL Table"
                vet_specialties = component "vet_specialties" "Vet-specialty mapping table" "SQL Table"
            }
        }

        browser = softwareSystem "Web Browser" "Users access the application through a web browser" "Browser"

        # Relationships
        user -> browser "Uses"
        browser -> petclinic "Makes HTTP requests to"

        # Controller to Repository relationships
        ownerController -> ownerRepository "Uses"
        petController -> petRepository "Uses"
        petController -> petTypeRepository "Uses"
        vetController -> vetRepository "Uses"
        visitController -> visitRepository "Uses"
        visitController -> petRepository "Uses"

        # Controller to Domain Model relationships
        ownerController -> owner "Uses"
        petController -> pet "Uses"
        petController -> petType "Uses"
        vetController -> vet "Uses"
        visitController -> visit "Uses"

        # Repository to Database relationships
        ownerRepository -> database.tables.owners "Reads from and writes to"
        petRepository -> database.tables.pets "Reads from and writes to"
        vetRepository -> database.tables.vets "Reads from and writes to"
        visitRepository -> database.tables.visits "Reads from and writes to"
        petTypeRepository -> database.tables.types "Reads from and writes to"

        # Domain Model relationships
        owner -> pet "Has many"
        pet -> owner "Belongs to"
        pet -> petType "Has one"
        pet -> visit "Has many"
        visit -> pet "Belongs to"
        vet -> specialty "Has many"

        # Database Initializer relationships
        databaseInitializer -> ownerRepository "Uses"
        databaseInitializer -> vetRepository "Uses"
        databaseInitializer -> database.tables "Initializes"

        # Template relationships
        thymeleafEngine -> owner "Displays"
        thymeleafEngine -> pet "Displays"
        thymeleafEngine -> vet "Displays"
        thymeleafEngine -> visit "Displays"
    }

    views {
        systemContext petclinic "SystemContext" {
            include *
            autoLayout
        }

        container petclinic "Containers" {
            include *
            autoLayout
        }

        component ownerController "OwnerController" {
            include *
            autoLayout
        }

        component petController "PetController" {
            include *
            autoLayout
        }

        component vetController "VetController" {
            include *
            autoLayout
        }

        component visitController "VisitController" {
            include *
            autoLayout
        }

        component database "Database" {
            include *
            autoLayout
        }

        styles {
            element "Person" {
                shape: Person
                background: #08427B
                color: #ffffff
            }
            element "Software System" {
                background: #1168BD
                color: #ffffff
            }
            element "Container" {
                background: #438DD5
                color: #ffffff
            }
            element "Component" {
                background: #85BBF0
                color: #000000
            }
            element "Database" {
                shape: Cylinder
                background: #FF8C00
                color: #ffffff
            }
            element "SQL Table" {
                shape: Cylinder
                background: #FFA500
                color: #000000
            }
        }

        theme: default
    }
} 