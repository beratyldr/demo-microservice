```markdown
# EazyBytes Microservices Platform

A comprehensive microservices-based banking application built with Spring Boot 4.0.3, Spring Cloud, and Jakarta EE. This platform demonstrates modern distributed system architecture with service discovery, centralized configuration, and independent microservices.

## 📋 Project Overview

EazyBytes is a multi-service banking platform consisting of interconnected microservices that handle different banking domains:

- **Accounts Service** - Customer account management and operations
- **Cards Service** - Credit/debit card management
- **Loans Service** - Loan processing and management
- **Config Server** - Centralized configuration management
- **Eureka Server** - Service discovery and registration

## 🏗️ Architecture
```

┌─────────────────────────────────────────────────────┐
│           API Clients / Frontend                     │
└────────────┬────────────────────────────────────────┘
│
┌────────────┴────────────────────────────────────────┐
│              Eureka Service Discovery                │
└────────────┬────────────────────────────────────────┘
│
┌────────┼────────┐
│        │        │
┌───▼──┐ ┌──▼───┐ ┌──▼───┐
│Accts │ │Cards │ │Loans │
└──────┘ └──────┘ └──────┘
│        │        │
└────────────┼────────┘
│
┌────────▼─────────┐
│ Config Server    │
└──────────────────┘
```
## 🚀 Tech Stack

- **Framework**: Spring Boot 4.0.3
- **Java Version**: Java 21
- **Build Tool**: Maven 3.9+
- **ORM**: Spring Data JPA
- **Database**: H2 (Development), PostgreSQL (Production-ready)
- **Service Discovery**: Spring Cloud Eureka
- **Configuration Management**: Spring Cloud Config
- **Validation**: Jakarta Bean Validation
- **API Documentation**: SpringDoc OpenAPI 3.0.2
- **Monitoring**: Spring Boot Actuator
- **Additional Tools**: Lombok, Docker (Jib plugin)

## 📦 Project Structure
```

eazybytes/
├── accounts/              # Accounts microservice
│   ├── src/main/java/
│   ├── src/test/
│   ├── pom.xml
│   └── mvnw
├── cards/                 # Cards microservice
│   ├── src/main/java/
│   ├── src/test/
│   ├── pom.xml
│   └── mvnw
├── loans/                 # Loans microservice
│   ├── src/main/java/
│   ├── src/test/
│   ├── pom.xml
│   └── mvnw
├── configserver/          # Centralized configuration server
│   ├── src/main/java/
│   ├── src/test/
│   ├── pom.xml
│   └── mvnw
├── eurekaserver/          # Service discovery server
│   ├── src/main/java/
│   ├── src/test/
│   ├── pom.xml
│   └── mvnw
├── docker-compose/        # Docker Compose configurations
└── readme.md              # This file
```
## 🛠️ Prerequisites

- **Java 21** or higher
- **Maven 3.9+** (included via Maven Wrapper)
- **Docker & Docker Compose** (for containerized deployment)
- **Git** (for version control)

## 📥 Installation

### 1. Clone the Repository
```
bash
git clone <repository-url>
cd eazybytes
```
### 2. Build All Services

Using Maven Wrapper (recommended):
```
bash
# From project root, build all modules
./mvnw clean install
```
Or with Maven:
```
bash
mvn clean install
```
### 3. Build Individual Services
```
bash
cd accounts && ./mvnw clean install
cd ../cards && ./mvnw clean install
cd ../loans && ./mvnw clean install
cd ../configserver && ./mvnw clean install
cd ../eurekaserver && ./mvnw clean install
```
## 🚀 Running the Application

### Option 1: Run Services Individually

**Start Eureka Server** (Port: 8761)
```
bash
cd eurekaserver
./mvnw spring-boot:run
```
**Start Config Server** (Port: 8888)
```
bash
cd configserver
./mvnw spring-boot:run
```
**Start Accounts Service** (Port: 8080)
```
bash
cd accounts
./mvnw spring-boot:run
```
**Start Cards Service** (Port: 8081)
```
bash
cd cards
./mvnw spring-boot:run
```
**Start Loans Service** (Port: 8082)
```
bash
cd loans
./mvnw spring-boot:run
```
### Option 2: Docker Compose (Recommended)
```
bash
cd docker-compose
docker-compose up -d
```
This will start all services with proper networking and environment configuration.

## 📊 Service Ports

| Service | Port | Purpose |
|---------|------|---------|
| Eureka Server | 8761 | Service Discovery Dashboard |
| Config Server | 8888 | Configuration Management |
| Accounts Service | 8080 | Account Operations |
| Cards Service | 8081 | Card Management |
| Loans Service | 8082 | Loan Processing |

## 🔍 Service Discovery

Once all services are running, access the **Eureka Dashboard**:
```

http://localhost:8761
```
All microservices are automatically registered with Eureka and can discover each other.

## 📖 API Documentation

Each microservice provides OpenAPI 3.0 documentation via Swagger UI:

- **Accounts**: `http://localhost:8080/swagger-ui.html`
- **Cards**: `http://localhost:8081/swagger-ui.html`
- **Loans**: `http://localhost:8082/swagger-ui.html`

## 🏥 Health Checks

Spring Boot Actuator provides health endpoints:
```
bash
curl http://localhost:8080/actuator/health
```
## 🧪 Testing

Run tests for all services:
```
bash
./mvnw test
```
Run tests for a specific service:
```
bash
cd accounts && ./mvnw test
```
## 🔧 Configuration Management

Configuration files are centralized in the Config Server. Services fetch their configuration on startup and can refresh it dynamically without restart.

### Local Configuration

Each service can have local `application.yml` or `application.properties`:
```
properties
spring.application.name=accounts-service
spring.datasource.url=jdbc:h2:mem:accountsdb
spring.jpa.hibernate.ddl-auto=update
```
## 🐳 Docker & Container Deployment

### Build Docker Images

The project uses **Jib Maven Plugin** for efficient containerization:
```
bash
./mvnw jib:build
```
Images are built for both ARM64 and AMD64 architectures:
```

eazybytes/accounts:s4
eazybytes/cards:s4
eazybytes/loans:s4
```
### Run with Docker Compose
```
bash
cd docker-compose
docker-compose up
```
## 📋 Key Dependencies

### Core Dependencies
- `spring-boot-starter-webmvc` - REST API development
- `spring-boot-starter-data-jpa` - Data persistence
- `spring-boot-starter-validation` - Input validation
- `spring-boot-starter-actuator` - Monitoring & metrics

### Testing Dependencies
- `spring-boot-starter-test` - Comprehensive testing framework
- JUnit 5, Mockito, AssertJ

### Development Tools
- Lombok - Reduce boilerplate code
- SpringDoc OpenAPI - API documentation
- H2 Database - Embedded database for development
- Spring Boot DevTools - Development enhancements

## 📝 Development Guidelines

### Code Style
- Use Lombok annotations to reduce boilerplate
- Follow Spring Boot conventions for package structure
- Implement proper exception handling and validation

### Database
- Use Spring Data JPA repositories for data access
- Leverage Hibernate/JPA annotations for entity mapping
- Apply database migrations as needed

### API Design
- Follow RESTful conventions
- Use proper HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Document endpoints with OpenAPI annotations

## 🔐 Security Considerations

- Use environment variables for sensitive configuration
- Implement proper authentication/authorization (to be added)
- Validate all user inputs
- Use HTTPS in production environments

## 📈 Monitoring & Observability

The project includes Spring Boot Actuator for:
- Health checks: `/actuator/health`
- Metrics: `/actuator/metrics`
- Environment information: `/actuator/env`

## 🐛 Troubleshooting

### Application Context Load Failure

**Issue**: `Failed to load ApplicationContext`

**Solution**: Ensure all dependencies are correctly resolved:
```
bash
./mvnw clean install -U
```
### Service Discovery Issues

**Issue**: Services not registering with Eureka

**Solution**: 
- Verify Eureka Server is running on port 8761
- Check service application names in configuration
- Review Eureka dashboard for registered instances

### Port Already in Use

**Issue**: `Port 8080 is already in use`

**Solution**: Kill the process using the port or change port in configuration:
```
properties
server.port=8080
```
## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

[Add your license information here]

## 👥 Authors & Maintainers

- **Project**: EazyBytes Microservices Platform
- **Organization**: EazyBytes

## 📞 Support & Contact

For issues, questions, or contributions:
- Create an issue in the repository
- Check existing documentation
- Review application logs for error details

## 🗺️ Roadmap

- [ ] Add authentication/authorization (Spring Security)
- [ ] Implement API Gateway (Spring Cloud Gateway)
- [ ] Add distributed tracing (Spring Cloud Sleuth)
- [ ] Implement circuit breaker pattern (Resilience4j)
- [ ] Add comprehensive logging (ELK Stack integration)
- [ ] Database migration scripts (Flyway/Liquibase)
- [ ] Production deployment guides

## ✨ Features

- ✅ Multi-service microservices architecture
- ✅ Service discovery with Eureka
- ✅ Centralized configuration management
- ✅ RESTful APIs with OpenAPI documentation
- ✅ Health monitoring with Actuator
- ✅ Docker containerization support
- ✅ Database persistence with JPA
- ✅ Input validation with Jakarta Bean Validation
- ✅ Development tools and IDE support

---

**Last Updated**: March 2026  
**Version**: 1.0.0
```