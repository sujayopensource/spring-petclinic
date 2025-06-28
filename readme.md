# PetClinic Micronaut Application

This is a sample application built with [Micronaut](https://micronaut.io/) that demonstrates how to build a simple but complete web application.

## Technology Stack

- **Framework**: Micronaut 4.4.2
- **Language**: Java 21
- **Build Tool**: Gradle 8.5
- **Database**: H2 (in-memory)
- **ORM**: Hibernate JPA with Micronaut Data
- **Template Engine**: Thymeleaf
- **Cache**: Caffeine
- **Testing**: JUnit 5

## High-Level Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        PetClinic Application                    │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │   Web Layer     │    │  Business Layer │    │  Data Layer  │ │
│  │                 │    │                 │    │              │ │
│  │ ┌─────────────┐ │    │ ┌─────────────┐ │    │ ┌──────────┐ │ │
│  │ │Controllers  │ │    │ │Services     │ │    │ │Repositories│ │ │
│  │ │             │ │    │ │             │ │    │ │           │ │ │
│  │ │•OwnerCtrl   │ │    │ │•ClinicSvc   │ │    │ │•OwnerRepo │ │ │
│  │ │•PetCtrl     │ │    │ │•Validation  │ │    │ │•PetRepo   │ │ │
│  │ │•VetCtrl     │ │    │ │•Business    │ │    │ │•VetRepo   │ │ │
│  │ │•VisitCtrl   │ │    │ │  Logic      │ │    │ │•VisitRepo │ │ │
│  │ └─────────────┘ │    │ └─────────────┘ │    │ └──────────┘ │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
│           │                       │                    │        │
│           │                       │                    │        │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │  Presentation   │    │   Domain Model  │    │   Database   │ │
│  │                 │    │                 │    │              │ │
│  │ ┌─────────────┐ │    │ ┌─────────────┐ │    │ ┌──────────┐ │ │
│  │ │Thymeleaf    │ │    │ │Entities     │ │    │ │H2 Database│ │ │
│  │ │Templates    │ │    │ │             │ │    │ │           │ │ │
│  │ │             │ │    │ │•Owner       │ │    │ │•In-Memory │ │ │
│  │ │•HTML Views  │ │    │ │•Pet         │ │    │ │•Auto-Init │ │ │
│  │ │•CSS/JS      │ │    │ │•Vet         │ │    │ │•Sample    │ │ │
│  │ │•Bootstrap   │ │    │ │•Visit       │ │    │ │  Data     │ │ │
│  │ └─────────────┘ │    │ └─────────────┘ │    │ └──────────┘ │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
│                                                                 │
├─────────────────────────────────────────────────────────────────┤
│                    Micronaut Framework                          │
│  ┌─────────────────┐    ┌─────────────────┐    ┌──────────────┐ │
│  │   HTTP Server   │    │  DI Container   │    │   Runtime    │ │
│  │                 │    │                 │    │              │ │
│  │•Netty Server    │    │•Compile-time    │    │•Fast Startup │ │ │
│  │•Routing         │    │  DI             │    │•Low Memory   │ │ │
│  │•Request/Response│    │•Validation      │    │•Cloud Native │ │ │
│  └─────────────────┘    └─────────────────┘    └──────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

### Architecture Layers

1. **Web Layer**: Handles HTTP requests and responses
   - Controllers process incoming requests
   - Thymeleaf templates render HTML responses
   - Static resources (CSS, JS, images)

2. **Business Layer**: Contains application logic
   - Services implement business rules
   - Validation and data processing
   - Transaction management

3. **Data Layer**: Manages data persistence
   - Repositories provide data access
   - Hibernate JPA for ORM
   - H2 in-memory database

4. **Domain Model**: Core business entities
   - Owner, Pet, Vet, Visit entities
   - Relationships and business rules
   - Data validation annotations

### Key Features

- **Layered Architecture**: Clear separation of concerns
- **Dependency Injection**: Micronaut's compile-time DI
- **RESTful Design**: Clean URL structure and HTTP methods
- **Template Engine**: Server-side rendering with Thymeleaf
- **Database Integration**: JPA with automatic schema generation
- **Caching**: Built-in caching support for performance

## Features

- **Owner Management**: Create, read, update owners
- **Pet Management**: Add pets to owners, update pet information
- **Veterinarian Management**: View list of veterinarians and their specialties
- **Visit Management**: Schedule and view pet visits
- **Error Handling**: Demonstrates exception handling
- **Database Initialization**: Automatic data population on startup

## Getting Started

### Prerequisites

- Java 21 or higher
- Gradle 8.5 or higher (or use the included wrapper)

### Running the Application

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd spring-petclinic
   ```

2. **Run the application**:
   ```bash
   ./gradlew run
   ```

3. **Access the application**:
   - Home page: http://localhost:8080
   - Veterinarians: http://localhost:8080/vets.html
   - Find Owners: http://localhost:8080/owners/find
   - Test endpoint: http://localhost:8080/test

### Building the Application

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/springframework/samples/petclinic/
│   │       ├── model/          # Domain entities (Person, BaseEntity, etc.)
│   │       ├── owner/          # Owner and Pet management
│   │       ├── vet/            # Veterinarian management
│   │       ├── visit/          # Visit management
│   │       └── system/         # System controllers and utilities
│   ├── resources/
│   │   ├── views/              # Thymeleaf templates
│   │   ├── static/             # Static resources (CSS, images)
│   │   ├── db/                 # Database scripts
│   │   └── application.yml     # Application configuration
│   └── test/                   # Test classes
```

## Key Components

### Controllers
- **OwnerController**: Handles owner CRUD operations
- **PetController**: Manages pet creation and updates
- **VetController**: Displays veterinarian information
- **VisitController**: Handles pet visit scheduling

### Data Access
- **Micronaut Data**: JPA repositories for database operations
- **Hibernate**: ORM for entity management
- **H2 Database**: In-memory database for development

### Templates
- **Thymeleaf**: Server-side template engine
- **Bootstrap**: CSS framework for responsive design
- **Views Directory**: Templates located in `src/main/resources/views/`

## Benefits of Micronaut

- **Fast Startup**: Compile-time dependency injection
- **Low Memory Footprint**: Minimal runtime overhead
- **Native Image Support**: Can be compiled to native executables
- **Reactive Programming**: Built-in support for reactive streams
- **Cloud-Native**: Designed for microservices and cloud deployment
- **Type Safety**: Compile-time validation of dependency injection

## Database Configuration

The application uses H2 in-memory database by default. The database is automatically initialized with sample data including:
- 6 veterinarians with specialties
- 5 pet owners
- 6 pet types (cat, dog, lizard, snake, bird, hamster)

## Troubleshooting

### Vets Not Displaying
If the veterinarians page shows empty, check:
1. Console logs for database initialization messages
2. Visit `/test` endpoint to verify data exists
3. Check if caching is disabled (currently disabled for debugging)

### Form Submission Issues
- Ensure forms use `@Consumes(MediaType.APPLICATION_FORM_URLENCODED)`
- Check that form fields match controller parameter names

### Template Issues
- Templates are located in `src/main/resources/views/`
- Thymeleaf expressions may need adjustment for Micronaut compatibility

## Known Issues

### 🚨 **Critical Issues**

#### 1. Veterinarians Page Not Displaying Data
- **Issue**: `/vets.html` page shows empty table despite data being in database
- **Status**: Under investigation
- **Symptoms**: 
  - Console shows "Found 6 vets in database" but page is empty
  - Caching has been disabled but issue persists
  - `/test` endpoint shows data exists
- **Workaround**: Use `/test` endpoint to verify data
- **Root Cause**: Suspected controller routing or template rendering issue

#### 2. Database Initialization Timing
- **Issue**: Database initialization may run before Hibernate creates tables
- **Status**: Partially resolved with background thread approach
- **Symptoms**: "Table not found" errors during startup
- **Workaround**: 5-second delay added to initialization
- **Root Cause**: Race condition between Hibernate schema creation and data initialization

### ⚠️ **Medium Priority Issues**

#### 3. Pet Creation Form Parameter Issues
- **Issue**: `/owners/{id}/pets/new` endpoint expects `typeId` parameter
- **Status**: Partially resolved
- **Symptoms**: "Required argument [Integer typeId] not specified" error
- **Workaround**: Ensure pet types are created in database initialization
- **Root Cause**: Form parameter binding issues with Micronaut

#### 4. Internationalization (i18n) Issues
- **Issue**: Message keys showing as `??key??` instead of translated text
- **Status**: Partially resolved with custom message resolver
- **Symptoms**: 
  - Welcome page shows `??welcome??`
  - Some messages not properly resolved
- **Workaround**: Custom Thymeleaf message resolver implemented
- **Root Cause**: Micronaut doesn't automatically load message bundles like Spring Boot

#### 5. Form Data Binding Limitations
- **Issue**: Micronaut doesn't bind form data directly to objects
- **Status**: Workaround implemented
- **Symptoms**: Form submissions fail with "Content Type not allowed"
- **Workaround**: Controllers accept individual parameters and manually create objects
- **Root Cause**: Framework differences between Spring Boot and Micronaut

### 🔧 **Minor Issues**

#### 6. IDE Import Resolution Warnings
- **Issue**: Linter shows import resolution errors for Micronaut annotations
- **Status**: Cosmetic issue
- **Symptoms**: Red squiggly lines under imports like `io.micronaut`, `jakarta.inject`
- **Workaround**: Code compiles and runs correctly despite warnings
- **Root Cause**: IDE configuration or temporary indexing issues

#### 7. Verbose Logging
- **Issue**: Application produces very verbose logs during startup
- **Status**: Partially resolved with logging configuration
- **Symptoms**: Console flooded with debug messages
- **Workaround**: Logging levels configured in `application.yml`
- **Root Cause**: Default Micronaut logging verbosity

#### 8. Template Expression Compatibility
- **Issue**: Some Thymeleaf expressions not compatible with Micronaut
- **Status**: Partially resolved
- **Symptoms**: 
  - OGNL parsing errors for safe navigation operators (`?.`)
  - `#fields.hasAnyErrors()` not available
- **Workaround**: Simplified template expressions used
- **Root Cause**: Different Thymeleaf configuration in Micronaut

### 📋 **Migration-Related Issues**

#### 9. Framework Differences
- **Issue**: Various Spring Boot to Micronaut migration challenges
- **Status**: Ongoing
- **Areas Affected**:
  - Database initialization approach
  - Form handling and validation
  - Template engine configuration
  - Dependency injection patterns
  - Caching mechanisms

### 🔄 **In Progress**

#### 10. Comprehensive Testing
- **Issue**: Limited test coverage for migrated components
- **Status**: Not started
- **Needed**: Unit tests, integration tests, end-to-end tests
- **Priority**: Medium

### 📝 **Documentation Gaps**

#### 11. API Documentation
- **Issue**: No OpenAPI/Swagger documentation
- **Status**: Not implemented
- **Needed**: API endpoint documentation
- **Priority**: Low

## Contributing

This is a sample application for learning purposes. Feel free to explore the code and experiment with different features.

## License

This project is licensed under the Apache License, Version 2.0.
