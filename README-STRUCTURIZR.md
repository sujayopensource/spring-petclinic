# PetClinic Micronaut - Structurizr Architecture

This directory contains Structurizr DSL files that define the architecture of the PetClinic Micronaut application.

## Files

- `structurizr.dsl` - High-level architecture overview
- `structurizr-detailed.dsl` - Detailed component-level architecture

## What is Structurizr?

Structurizr is a tool for creating software architecture diagrams as code. It provides a DSL (Domain Specific Language) for defining software architecture models and automatically generates various views of the architecture.

## Getting Started

### Prerequisites

1. **Structurizr CLI** (recommended):
   ```bash
   # Install Structurizr CLI
   curl -L https://github.com/structurizr/cli/releases/latest/download/structurizr-cli-1.0.0.zip -o structurizr-cli.zip
   unzip structurizr-cli.zip
   ```

2. **Structurizr Online** (alternative):
   - Visit [Structurizr Online](https://structurizr.com/)
   - Create a free account
   - Use the web-based editor

### Using Structurizr CLI

1. **Generate diagrams from DSL**:
   ```bash
   # Generate PlantUML diagrams
   structurizr-cli export -workspace structurizr.dsl -format plantuml
   
   # Generate Mermaid diagrams
   structurizr-cli export -workspace structurizr.dsl -format mermaid
   
   # Generate JSON
   structurizr-cli export -workspace structurizr.dsl -format json
   ```

2. **Validate DSL files**:
   ```bash
   structurizr-cli validate -workspace structurizr.dsl
   ```

3. **Generate documentation**:
   ```bash
   structurizr-cli export -workspace structurizr.dsl -format documentation
   ```

### Using Structurizr Online

1. Copy the content of `structurizr.dsl` or `structurizr-detailed.dsl`
2. Go to [Structurizr Online](https://structurizr.com/)
3. Create a new workspace
4. Paste the DSL content
5. View the generated diagrams

## Architecture Views

### High-Level Architecture (`structurizr.dsl`)

This file provides:
- **System Context View**: Shows the PetClinic application in relation to external systems
- **Container View**: Shows the main containers within the application
- **Component Views**: Detailed views of each container's components

### Detailed Architecture (`structurizr-detailed.dsl`)

This file provides:
- **Component-Level Details**: Individual methods and properties
- **Database Schema**: Detailed table structure
- **Data Flow**: How data moves between components
- **API Endpoints**: Specific HTTP methods and routes

## Key Architecture Elements

### Containers
- **Web Layer**: Micronaut HTTP controllers
- **Business Layer**: Application services and business logic
- **Data Layer**: Micronaut Data repositories
- **Presentation Layer**: Thymeleaf templates and static resources
- **Domain Model**: JPA entities

### Components
- **Controllers**: Handle HTTP requests and responses
- **Repositories**: Provide data access
- **Entities**: Domain model objects
- **Services**: Business logic components

### Relationships
- **Uses**: Component dependencies
- **Reads from and writes to**: Data access patterns
- **Displays**: Presentation layer relationships

## Benefits of Architecture as Code

1. **Version Control**: Architecture diagrams are versioned with code
2. **Automation**: Generate diagrams automatically in CI/CD
3. **Consistency**: Ensure diagrams stay in sync with code
4. **Documentation**: Self-documenting architecture
5. **Collaboration**: Team can review and discuss architecture changes

## Integration with CI/CD

You can integrate Structurizr into your build process:

```yaml
# Example GitHub Actions workflow
- name: Generate Architecture Diagrams
  run: |
    structurizr-cli export -workspace structurizr.dsl -format plantuml -output docs/
    structurizr-cli export -workspace structurizr.dsl -format mermaid -output docs/
```

## Customization

You can customize the architecture by:

1. **Adding Components**: Define new components in the DSL
2. **Modifying Relationships**: Change how components interact
3. **Custom Views**: Create specific views for different stakeholders
4. **Styling**: Customize colors, shapes, and themes

## Resources

- [Structurizr Documentation](https://structurizr.com/help)
- [Structurizr DSL Reference](https://github.com/structurizr/dsl)
- [C4 Model](https://c4model.com/) - Architecture modeling approach
- [Structurizr Examples](https://github.com/structurizr/examples)

## Contributing

When making architectural changes:

1. Update the appropriate Structurizr DSL file
2. Generate new diagrams
3. Update this documentation if needed
4. Commit both code and architecture changes together 