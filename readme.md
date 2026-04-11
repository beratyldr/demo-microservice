# EazyBank Microservices Platform

A comprehensive microservices-based banking application built with Spring Boot 4.0.3, Spring Cloud 2025.1.0, and Java 21. This platform demonstrates modern distributed system architecture with service discovery, API gateway, centralized configuration, circuit breakers, and independent microservices.

## 📋 Project Overview

EazyBank is a multi-service banking platform consisting of interconnected microservices that handle different banking domains:

- **Gateway Server** - API Gateway for routing and load balancing with circuit breaker support
- **Accounts Service** - Customer account management and operations
- **Cards Service** - Credit/debit card management
- **Loans Service** - Loan processing and management
- **Eureka Server** - Service discovery and registration
- **Config Server** - Centralized configuration management

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────┐
│           API Clients / Frontend                     │
└────────────┬────────────────────────────────────────┘
             │
┌────────────▼────────────────────────────────────────┐
│          Gateway Server (Port 8072)                  │
│     (Routing, Circuit Breaker, Resilience4j)         │
└────────────┬────────────────────────────────────────┘
             │
┌────────────▼────────────────────────────────────────┐
│         Eureka Service Discovery (Port 8070)         │
└────────────┬────────────────────────────────────────┘
             │
    ┌────────┼────────┐
    │        │        │
┌───▼───┐ ┌──▼────┐ ┌──▼────┐
│Accounts│ │Cards  │ │Loans  │
│ :8080  │ │ :9000 │ │ :8090 │
└───┬────┘ └───┬───┘ └───┬───┘
    │          │         │
┌───▼──────────▼─────────▼───┐
│      Config Server          │
│         (Port 8071)         │
└─────────────────────────────┘
```

## 🚀 Tech Stack

### Core Technologies
- **Language**: Java 21
- **Framework**: Spring Boot 4.0.3
- **Cloud**: Spring Cloud 2025.1.0
- **Build Tool**: Maven 3.9+ (Maven Wrapper included)
- **Package Manager**: Maven

### Spring Cloud Components
- **API Gateway**: Spring Cloud Gateway (WebFlux-based)
- **Service Discovery**: Spring Cloud Netflix Eureka
- **Configuration Management**: Spring Cloud Config Server (Native profile)
- **Circuit Breaker**: Resilience4j
- **Client-Side Load Balancing**: Spring Cloud LoadBalancer
- **Inter-Service Communication**: OpenFeign

### Data & Persistence
- **ORM**: Spring Data JPA / Hibernate
- **Database**: MySQL 8.0
- **Validation**: Jakarta Bean Validation

### Monitoring & Documentation
- **API Documentation**: SpringDoc OpenAPI 3.0.2
- **Monitoring**: Spring Boot Actuator
- **Logging**: SLF4J with Logback

### Development Tools
- **Boilerplate Reduction**: Lombok
- **Container Building**: Jib Maven Plugin 3.5.1
- **Hot Reload**: Spring Boot DevTools

## 📦 Project Structure

```
section2/
├── accounts/                   # Accounts microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/accounts/
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entity/
│   │   │   │   ├── dto/
│   │   │   │   └── AccountsApplication.java
│   │   │   └── resources/
│   │   │       └── application.yml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw (Maven Wrapper)
│
├── cards/                      # Cards microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/cards/
│   │   │   └── resources/application.yml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
│
├── loans/                      # Loans microservice
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/loans/
│   │   │   └── resources/application.yml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
│
├── gatewayserver/              # API Gateway
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/gatewayserver/
│   │   │   │   ├── config/
│   │   │   │   │   └── EazybankRouteLocator.java
│   │   │   │   ├── filters/
│   │   │   │   │   ├── RequestTraceFilter.java
│   │   │   │   │   ├── ResponseTraceFilter.java
│   │   │   │   │   └── FilterUtility.java
│   │   │   │   └── GatewayserverApplication.java
│   │   │   └── resources/
│   │   │       └── application.yaml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
│
├── eurekaserver/               # Service Discovery Server
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/eurekaserver/
│   │   │   └── resources/
│   │   │       └── application.yaml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
│
├── configserver/               # Centralized Configuration Server
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/eazybytes/configserver/
│   │   │   └── resources/
│   │   │       ├── application.yaml
│   │   │       └── config/
│   │   │           └── eurekaserver.yml
│   │   └── test/
│   ├── pom.xml
│   └── mvnw
│
├── docker-compose/             # Docker deployment configurations
│   ├── default/
│   │   ├── docker-compose.yml
│   │   └── common-config.yml
│   ├── prod/
│   │   └── docker-compose.yml
│   └── qa/
│       └── docker-compose.yml
│
└── README.md                   # This file
```

## 🛠️ Prerequisites

- **Java 21** or higher ([Download](https://adoptium.net/))
- **Maven 3.9+** (Maven Wrapper included in each service)
- **MySQL 8.0** (for local development)
- **Docker & Docker Compose** (for containerized deployment)
- **Git** (for version control)

## 📥 Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd section2
```

### 2. Build All Services

Using Maven Wrapper (recommended):

```bash
# Build all services from their respective directories
cd accounts && ./mvnw clean install
cd ../cards && ./mvnw clean install
cd ../loans && ./mvnw clean install
cd ../configserver && ./mvnw clean install
cd ../eurekaserver && ./mvnw clean install
cd ../gatewayserver && ./mvnw clean install
```

Or with Maven (if installed globally):

```bash
cd accounts && mvn clean install
# Repeat for other services
```

### 3. Setup MySQL Databases

For local development, create the required databases:

```sql
CREATE DATABASE accountsdb;
CREATE DATABASE cardsdb;
CREATE DATABASE loansdb;
```

Update database credentials in each service's `application.yml` if needed:
- Default username: `root`
- Default password: `root`

## 🚀 Running the Application

### Option 1: Run Services Individually (Development)

**Important**: Start services in the following order to ensure proper initialization:

#### Step 1: Start Config Server (Port 8071)
```bash
cd configserver
./mvnw spring-boot:run
```

Wait until Config Server is fully started.

#### Step 2: Start Eureka Server (Port 8070)
```bash
cd eurekaserver
./mvnw spring-boot:run
```

Wait until Eureka Server is registered with Config Server.

#### Step 3: Start Business Services

**Accounts Service** (Port 8080)
```bash
cd accounts
./mvnw spring-boot:run
```

**Cards Service** (Port 9000)
```bash
cd cards
./mvnw spring-boot:run
```

**Loans Service** (Port 8090)
```bash
cd loans
./mvnw spring-boot:run
```

#### Step 4: Start Gateway Server (Port 8072)
```bash
cd gatewayserver
./mvnw spring-boot:run
```

### Option 2: Docker Compose (Recommended for Testing)

The project includes Docker Compose configurations for different environments.

**Note**: Currently, most microservices are commented out in the default docker-compose.yml. Only databases, Config Server, and Eureka Server are active by default.

```bash
cd docker-compose/default
docker-compose up -d
```

To start in different environments:
```bash
# QA Environment
cd docker-compose/qa
docker-compose up -d

# Production Environment
cd docker-compose/prod
docker-compose up -d
```

To stop services:
```bash
docker-compose down
```

## 📊 Service Ports & Endpoints

| Service | Port | Purpose | Health Check |
|---------|------|---------|--------------|
| Config Server | 8071 | Centralized Configuration | http://localhost:8071/actuator/health |
| Eureka Server | 8070 | Service Discovery Dashboard | http://localhost:8070/actuator/health |
| Gateway Server | 8072 | API Gateway & Routing | http://localhost:8072/actuator/health |
| Accounts Service | 8080 | Account Operations | http://localhost:8080/actuator/health |
| Loans Service | 8090 | Loan Processing | http://localhost:8090/actuator/health |
| Cards Service | 9000 | Card Management | http://localhost:9000/actuator/health |

### Gateway Routes

Access business services through the Gateway Server:

- **Accounts**: `http://localhost:8072/eazybank/accounts/**`
- **Loans**: `http://localhost:8072/eazybank/loans/**`
- **Cards**: `http://localhost:8072/eazybank/cards/**`

## 🔍 Service Discovery

Access the **Eureka Dashboard** to view all registered services:

```
http://localhost:8070
```

All microservices automatically register with Eureka and can discover each other for inter-service communication.

## 📖 API Documentation

Each microservice provides OpenAPI 3.0 documentation via Swagger UI:

- **Accounts**: http://localhost:8080/swagger-ui.html
- **Cards**: http://localhost:9000/swagger-ui.html
- **Loans**: http://localhost:8090/swagger-ui.html

**Note**: API documentation through Gateway Server may require additional configuration.

## 🏥 Health Checks & Monitoring

Spring Boot Actuator provides comprehensive health and monitoring endpoints:

### Health Checks
```bash
# Config Server
curl http://localhost:8071/actuator/health

# Eureka Server
curl http://localhost:8070/actuator/health

# Gateway Server
curl http://localhost:8072/actuator/health

# Business Services
curl http://localhost:8080/actuator/health  # Accounts
curl http://localhost:8090/actuator/health  # Loans
curl http://localhost:9000/actuator/health  # Cards
```

### Readiness & Liveness Probes
All services include Kubernetes-ready probes:
- Readiness: `/actuator/health/readiness`
- Liveness: `/actuator/health/liveness`

### Additional Actuator Endpoints
- **Metrics**: `/actuator/metrics`
- **Environment**: `/actuator/env`
- **Gateway Routes**: `/actuator/gateway/routes` (Gateway Server only)

All actuator endpoints are exposed by default. **Restrict access in production environments**.

## 🧪 Testing

### Run All Tests

From each service directory:

```bash
cd accounts
./mvnw test

cd ../cards
./mvnw test

cd ../loans
./mvnw test

cd ../gatewayserver
./mvnw test
```

### Test Dependencies

Each service includes:
- `spring-boot-starter-test` (JUnit 5, Mockito, AssertJ)
- `spring-boot-starter-actuator-test`
- `spring-boot-starter-data-jpa-test`
- `spring-boot-starter-validation-test`
- `spring-boot-starter-webmvc-test`

## 🔧 Configuration Management

### Centralized Configuration

The Config Server manages configurations for all services:
- **Profile**: Native (file-based)
- **Location**: `configserver/src/main/resources/config/`
- **URL**: http://localhost:8071/

### Configuration Files

Services fetch configuration from Config Server on startup:
- `spring.config.import: "optional:configserver:http://localhost:8071/"`

### Local Configuration Override

Each service has local `application.yml` for development:
- Database connections
- Service-specific settings
- Local overrides

## 🌍 Environment Variables

### Common Environment Variables

All microservices support the following environment variables:

**Spring Configuration**
- `SPRING_PROFILES_ACTIVE` - Active profile (default, qa, prod)
- `SPRING_CONFIG_IMPORT` - Config Server URL
- `SPRING_APPLICATION_NAME` - Service name

**Database Configuration**
- `SPRING_DATASOURCE_URL` - Database JDBC URL
- `SPRING_DATASOURCE_USERNAME` - Database username (default: `root`)
- `SPRING_DATASOURCE_PASSWORD` - Database password (default: `root`)

**Service Discovery**
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - Eureka Server URL (default: http://localhost:8070/eureka/)

**Gateway Configuration** (Gateway Server only)
- `SPRING_CLOUD_GATEWAY_SERVER_WEBFLUX_DISCOVERY_LOCATOR_ENABLED` - Enable/disable service discovery routing

### Docker Environment Variables

Defined in `docker-compose/default/common-config.yml`:
- `MYSQL_ROOT_PASSWORD: root`
- `SPRING_JPA_HIBERNATE_DDL_AUTO: update`
- Memory limits: 700MB per microservice

## 🐳 Docker & Container Deployment

### Build Docker Images

The project uses **Jib Maven Plugin** for efficient, daemonless containerization:

```bash
# Build and push images for all services
cd accounts && ./mvnw jib:build
cd ../cards && ./mvnw jib:build
cd ../loans && ./mvnw jib:build
cd ../configserver && ./mvnw jib:build
cd ../eurekaserver && ./mvnw jib:build
cd ../gatewayserver && ./mvnw jib:build
```

**Multi-platform Support**: Images are built for both ARM64 and AMD64 architectures.

**Image Tags**:
- `eazybytes/accounts:s4`
- `eazybytes/cards:s4`
- `eazybytes/loans:s4`
- `eazybytes/configserver:s4`
- `eazybytes/eurekaserver:s4`
- `eazybytes/gatewayserver:s4`

### Docker Compose Deployment

```bash
# Start all services
cd docker-compose/default
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down

# Remove volumes
docker-compose down -v
```

### Docker Networks

Services communicate via the `eazybank` bridge network defined in docker-compose.yml.

### Persistent Volumes

MySQL data is persisted in named volumes:
- `accounts_data`
- `loans_data`
- `cards_data`

## 📋 Key Dependencies

### Core Spring Boot Starters
- `spring-boot-starter-webmvc` - REST API development (Business services)
- `spring-boot-starter-data-jpa` - Data persistence
- `spring-boot-starter-validation` - Jakarta Bean Validation
- `spring-boot-starter-actuator` - Health checks and monitoring

### Spring Cloud Dependencies
- `spring-cloud-starter-config` - Config client
- `spring-cloud-starter-netflix-eureka-client` - Service registration
- `spring-cloud-starter-netflix-eureka-server` - Service discovery (Eureka Server)
- `spring-cloud-config-server` - Configuration server (Config Server)
- `spring-cloud-starter-gateway-server-webflux` - API Gateway (Gateway Server)
- `spring-cloud-starter-openfeign` - Declarative REST clients (Accounts)

### Resilience & Fault Tolerance
- `spring-cloud-starter-circuitbreaker-resilience4j` - Circuit breaker pattern (Accounts)
- `spring-cloud-starter-circuitbreaker-reactor-resilience4j` - Reactive circuit breaker (Gateway Server)

### Database
- `mysql-connector-j` - MySQL JDBC driver

### Documentation & Development
- `springdoc-openapi-starter-webmvc-ui` - OpenAPI 3.0 / Swagger UI
- `lombok` - Reduce boilerplate code
- `spring-boot-devtools` - Development enhancements

### Additional Libraries
- `commons-collections4:4.4` - Apache Commons Collections (Gateway Server)

## 🎯 Features

### Implemented Features
- ✅ Multi-service microservices architecture
- ✅ API Gateway with Spring Cloud Gateway
- ✅ Service discovery and registration with Eureka
- ✅ Centralized configuration management
- ✅ Circuit breaker pattern with Resilience4j
- ✅ Retry mechanism with exponential backoff
- ✅ Request/Response tracing filters
- ✅ Client-side load balancing
- ✅ Inter-service communication with OpenFeign
- ✅ RESTful APIs with OpenAPI documentation
- ✅ Health monitoring with Actuator
- ✅ Docker containerization with Jib
- ✅ Multi-platform Docker images (ARM64/AMD64)
- ✅ Database persistence with JPA/Hibernate
- ✅ Input validation with Jakarta Bean Validation
- ✅ MySQL database integration
- ✅ Docker Compose orchestration
- ✅ Multiple environment configurations (default, qa, prod)

### Gateway Features
- Route configuration with path rewriting
- Circuit breaker on Accounts service with fallback
- Retry policy on Loans service (3 retries with exponential backoff)
- Custom request and response trace filters
- Response headers injection (X-Response-Time)

### Resilience4j Configuration
- **Sliding window size**: 10
- **Failure rate threshold**: 50%
- **Wait duration in open state**: 10 seconds
- **Permitted calls in half-open state**: 2
- **Timeout duration**: 4 seconds

## 📝 Development Guidelines

### Code Style
- Use Lombok annotations (`@Data`, `@AllArgsConstructor`, etc.) to reduce boilerplate
- Follow Spring Boot conventions for package structure
- Implement proper exception handling and validation
- Use constructor injection for dependencies

### API Design
- Follow RESTful conventions
- Use proper HTTP methods (GET, POST, PUT, DELETE)
- Return appropriate HTTP status codes
- Document endpoints with OpenAPI annotations
- Validate all request payloads

### Database
- Use Spring Data JPA repositories
- Leverage JPA/Hibernate annotations for entity mapping
- Configure `ddl-auto: update` for development (use migrations in production)

### Testing
- Write unit tests for services and controllers
- Use `@SpringBootTest` for integration tests
- Mock external dependencies with Mockito

## 🔐 Security Considerations

### Current State
- **Authentication/Authorization**: Not implemented (TODO)
- **Input Validation**: Implemented with Jakarta Bean Validation
- **Actuator Endpoints**: Fully exposed (restrict in production)

### Recommendations for Production
- Implement Spring Security for authentication/authorization
- Use environment variables for sensitive credentials
- Enable HTTPS/TLS
- Restrict actuator endpoints
- Implement API rate limiting
- Use secrets management (e.g., HashiCorp Vault)
- Enable CORS with specific origins only

## 🐛 Troubleshooting

### Services Not Starting

**Issue**: Service fails to connect to Config Server

**Solution**: Ensure Config Server is running on port 8071 before starting other services
```bash
curl http://localhost:8071/actuator/health
```

### Service Discovery Issues

**Issue**: Services not appearing in Eureka Dashboard

**Solution**: 
1. Verify Eureka Server is running: http://localhost:8070
2. Check service logs for registration errors
3. Verify `eureka.client.serviceUrl.defaultZone` configuration
4. Wait 30-60 seconds for registration to complete

### Database Connection Errors

**Issue**: `Communications link failure` or connection refused

**Solution**:
1. Verify MySQL is running on the correct port
2. Check database credentials in `application.yml`
3. Ensure databases exist (accountsdb, cardsdb, loansdb)
4. Verify MySQL port mappings (3306, 3307, 3308)

### Port Already in Use

**Issue**: `Port 8080 is already in use`

**Solution**:
```bash
# Find and kill the process
lsof -ti:8080 | xargs kill -9

# Or change port in application.yml
server.port: 8081
```

### Circuit Breaker Not Triggering

**Issue**: Circuit breaker remains closed despite failures

**Solution**:
- Verify Resilience4j configuration in application.yml
- Check failure rate threshold (default: 50%)
- Ensure enough requests to trigger sliding window evaluation
- Review logs for circuit breaker state changes

### Docker Compose Issues

**Issue**: Services fail to start with docker-compose

**Solution**:
1. Uncomment desired services in `docker-compose/default/docker-compose.yml`
2. Ensure Docker images are built: `./mvnw jib:build`
3. Check service dependencies and health checks
4. Review logs: `docker-compose logs -f [service-name]`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit changes (`git commit -m 'Add AmazingFeature'`)
4. Push to branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

Apache License 2.0

See the OpenAPI documentation in AccountsApplication.java for license details:
- **Name**: Apache 2.0
- **URL**: https://www.apache.org/licenses/LICENSE-2.0

## 👥 Authors & Maintainers

- **Project**: EazyBank Microservices Platform
- **Organization**: EazyBytes
- **Contact**: eazybank@bank.com
- **Website**: https://www.eazybank.com

## 📞 Support & Contact

For issues, questions, or contributions:
- Create an issue in the repository
- Check Swagger UI documentation for API details
- Review application logs in `target/` directories
- Check Eureka Dashboard for service status

## 🗺️ Roadmap & TODOs

### TODO Items
- [ ] Add authentication/authorization with Spring Security
- [ ] Implement distributed tracing (Spring Cloud Sleuth/Micrometer Tracing)
- [ ] Add centralized logging (ELK Stack integration)
- [ ] Implement database migration scripts (Flyway or Liquibase)
- [ ] Add rate limiting on Gateway
- [ ] Implement API versioning strategy
- [ ] Add comprehensive integration tests
- [ ] Configure production-ready logging levels
- [ ] Implement secrets management
- [ ] Add Kubernetes deployment manifests
- [ ] Configure HTTPS/TLS for all services
- [ ] Implement message queue (RabbitMQ/Kafka) for async communication
- [ ] Add performance monitoring (Prometheus/Grafana)
- [ ] Uncomment and configure all services in Docker Compose

### Recently Completed
- ✅ Gateway Server implementation with routing
- ✅ Circuit breaker integration with Resilience4j
- ✅ Retry mechanism with configurable policies
- ✅ Custom request/response filters

---

**Last Updated**: April 2026  
**Version**: 1.0.0  
**Spring Boot**: 4.0.3  
**Spring Cloud**: 2025.1.0  
**Java**: 21
