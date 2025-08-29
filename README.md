# MediSure Healthcare Management System

A comprehensive healthcare management system built with Spring Boot, featuring user management, client management, policy management, and claims processing.

## 🏗️ Project Structure

The project follows a clean, layered architecture pattern for better maintainability and scalability:

```
src/main/java/com/progrp251/medisure/
├── MediSureApplication.java          # Main application class
├── config/                           # Configuration classes
├── constant/                         # Application constants
├── controller/                       # REST controllers (API endpoints)
├── domain/                          # Domain layer
│   └── entity/                      # JPA entities
├── exception/                        # Custom exception classes
├── repository/                       # Data access layer (JPA repositories)
├── security/                         # Security configuration
│   ├── config/                      # Security configuration classes
│   ├── oauth/                       # OAuth2 authentication
│   └── service/                     # Security-related services
├── service/                         # Business logic layer
└── util/                            # Utility classes
```

## 📦 Package Descriptions

### `config/`
- Application configuration classes
- Bean configurations
- External service configurations

### `constant/`
- Application-wide constants
- API endpoints
- Security constants
- Error messages

### `controller/`
- REST API controllers
- Handle HTTP requests and responses
- Input validation
- Exception handling

### `domain/entity/`
- JPA entity classes
- Database table mappings
- Entity relationships
- Business domain objects

### `exception/`
- Custom exception classes
- Exception handling utilities
- Error response models

### `repository/`
- JPA repository interfaces
- Data access methods
- Custom query methods

### `security/`
- Security configuration
- OAuth2 authentication
- User authentication services
- Authorization rules

### `service/`
- Business logic implementation
- Transaction management
- Service layer interfaces
- Business rules enforcement

### `util/`
- Utility classes
- Helper methods
- Common functionality

## 🚀 Getting Started

### Prerequisites
- Java 21
- Maven 3.6+
- PostgreSQL database

### Installation
1. Clone the repository
2. Configure database connection in `application.properties`
3. Run `mvn clean install`
4. Start the application with `mvn spring-boot:run`

### Configuration
Update `src/main/resources/application.properties` with your database credentials:
```properties
spring.datasource.url=${DB_URL}
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```

## 🔐 Authentication

The application supports:
- **OAuth2 with Google** - Primary authentication method
- **Form-based login** - Fallback authentication
- **In-memory users** - For development/testing

## 📋 Features

- **User Management**: CRUD operations for system users
- **Client Management**: Insurance client profiles
- **Policy Management**: Insurance policy handling
- **Claims Processing**: Insurance claims workflow
- **Role-based Access Control**: Different user roles and permissions

## 🛠️ Development Guidelines

### Code Organization
- Keep controllers thin - delegate business logic to services
- Use constructor injection for dependencies
- Follow naming conventions consistently
- Add proper validation and error handling

### Database
- Use JPA entities for database mapping
- Implement proper relationships between entities
- Use repository pattern for data access

### Security
- All endpoints are secured by default
- Public endpoints are defined in `ApplicationConstants.PUBLIC_URLS`
- OAuth2 is the primary authentication method

### Testing
- Unit tests for services
- Integration tests for controllers
- Repository tests for data access

## 📝 API Documentation

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `POST /api/users` - Create new user
- `PUT /api/users/{id}` - Update user
- `DELETE /api/users/{id}` - Delete user

### Authentication
- `GET /login` - Login page
- `GET /oauth2/authorization/google` - Google OAuth2 login

## 🔧 Development Workflow

1. **Feature Development**: Create feature branch from main
2. **Code Review**: Submit pull request for review
3. **Testing**: Ensure all tests pass
4. **Documentation**: Update relevant documentation
5. **Merge**: Merge to main after approval

## 📊 Database Schema

The application uses the following main entities:
- **User**: System users with roles and authentication
- **Client**: Insurance clients with personal information
- **Policy**: Insurance policies linked to clients
- **Claim**: Insurance claims with status tracking

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.
