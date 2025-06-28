workspace {
    name: "PetClinic Micronaut Application"
    description: "A sample application built with Micronaut that demonstrates how to build a simple but complete web application"

    model {
        # People
        user = person "Pet Clinic User" "A user of the PetClinic application"
        admin = person "System Administrator" "An administrator managing the PetClinic application"

        # Software Systems
        petclinic = softwareSystem "PetClinic Application" "Allows users to manage pet owners, pets, veterinarians, and visits" {
            webLayer = container "Web Layer" "Handles HTTP requests and responses" "Micronaut HTTP Server" {
                ownerController = component "Owner Controller" "Handles owner CRUD operations" "Micronaut Controller"
                petController = component "Pet Controller" "Manages pet creation and updates" "Micronaut Controller"
                vetController = component "Vet Controller" "Displays veterinarian information" "Micronaut Controller"
                visitController = component "Visit Controller" "Handles pet visit scheduling" "Micronaut Controller"
                testController = component "Test Controller" "Provides debugging and testing endpoints" "Micronaut Controller"
            }

            businessLayer = container "Business Layer" "Contains application logic and business rules" "Micronaut Services" {
                databaseInitializer = component "Database Initializer" "Initializes database with sample data" "Micronaut Component"
                messageResolver = component "Message Resolver" "Handles internationalization" "Thymeleaf Component"
            }

            dataLayer = container "Data Layer" "Manages data persistence and access" "Micronaut Data" {
                ownerRepository = component "Owner Repository" "Data access for owners" "Micronaut Data Repository"
                petRepository = component "Pet Repository" "Data access for pets" "Micronaut Data Repository"
                vetRepository = component "Vet Repository" "Data access for veterinarians" "Micronaut Data Repository"
                visitRepository = component "Visit Repository" "Data access for visits" "Micronaut Data Repository"
                petTypeRepository = component "Pet Type Repository" "Data access for pet types" "Micronaut Data Repository"
            }

            presentationLayer = container "Presentation Layer" "Handles UI rendering and static resources" "Thymeleaf Templates" {
                thymeleafEngine = component "Thymeleaf Engine" "Server-side template engine" "Thymeleaf"
                staticResources = component "Static Resources" "CSS, JavaScript, and images" "Bootstrap, CSS, JS"
            }

            domainModel = container "Domain Model" "Core business entities and relationships" "JPA Entities" {
                owner = component "Owner" "Pet owner entity" "JPA Entity"
                pet = component "Pet" "Pet entity" "JPA Entity"
                vet = component "Vet" "Veterinarian entity" "JPA Entity"
                visit = component "Visit" "Pet visit entity" "JPA Entity"
                petType = component "Pet Type" "Pet type entity" "JPA Entity"
                specialty = component "Specialty" "Veterinarian specialty entity" "JPA Entity"
            }
        }

        database = softwareSystem "H2 Database" "In-memory database for storing application data" "Database"

        # External Systems
        browser = softwareSystem "Web Browser" "Users access the application through a web browser" "Browser"

        # Relationships
        user -> browser "Uses"
        browser -> petclinic "Makes HTTP requests to"
        admin -> petclinic "Manages"

        # Internal relationships
        webLayer -> businessLayer "Uses"
        webLayer -> dataLayer "Uses"
        webLayer -> presentationLayer "Uses"
        webLayer -> domainModel "Uses"
        businessLayer -> dataLayer "Uses"
        businessLayer -> domainModel "Uses"
        dataLayer -> domainModel "Manages"
        dataLayer -> database "Reads from and writes to"
        presentationLayer -> domainModel "Displays"
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

        component webLayer "WebLayer" {
            include *
            autoLayout
        }

        component businessLayer "BusinessLayer" {
            include *
            autoLayout
        }

        component dataLayer "DataLayer" {
            include *
            autoLayout
        }

        component domainModel "DomainModel" {
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
        }

        theme: default
    }
} 