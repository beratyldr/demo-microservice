# EazyBytes Microservices Platform

A multi-service banking application built with Spring Boot, Spring Cloud, and Jakarta EE. The platform demonstrates a modern microservices architecture with service discovery, centralized configuration, and independent domain services.

Last updated: 2026-03-23 19:54

## Overview

This repository contains the following microservices and infrastructure components:
- Accounts Service — customer accounts domain
- Cards Service — card domain
- Loans Service — loans domain
- Config Server — centralized configuration
- Eureka Server — service discovery
- API Gateway — edge service (Spring Cloud Gateway)

## Tech Stack

- Language: Java 21
- Frameworks: Spring Boot 4.x, Spring Cloud 2025.1.x, Jakarta Bean Validation
- APIs: Spring Web (MVC/WebFlux where applicable)
- Persistence: Spring Data JPA
- Database: MySQL (local/dev via Docker Compose)
- Service Discovery: Netflix Eureka (Spring Cloud)
- Central Config: Spring Cloud Config (native profile)
- Documentation: SpringDoc OpenAPI 3.0.2 (accounts, loans, cards)
- Observability: Spring Boot Actuator
- Packaging: Maven (per-module), Jib for container images

Notes
- There is no root aggregator Maven POM. Each module is an independent Spring Boot application with its own `pom.xml` and Maven Wrapper.

## Project Structure

section2/
- accounts/
  - src/main/java/com/eazybytes/accounts/AccountsApplication.java (entrypoint)
  - src/main/resources/application.yml (local overrides)
  - pom.xml, mvnw
- cards/
  - src/main/java/com/eazybytes/cards/CardsApplication.java (entrypoint)
  - src/main/resources/application.yml (local overrides)
  - pom.xml, mvnw
- loans/
  - src/main/java/com/eazybytes/loans/LoansApplication.java (entrypoint)
  - src/main/resources/application.yml (local overrides)
  - pom.xml, mvnw
- configserver/
  - src/main/java/com/eazybytes/configserver/ConfigserverApplication.java (entrypoint)
  - src/main/resources/application.yaml
  - pom.xml, mvnw
- eurekaserver/
  - src/main/java/com/eazybytes/eurekaserver/EurekaserverApplication.java (entrypoint)
  - src/main/resources/application.yaml
  - pom.xml, mvnw
- gatewayserver/
  - src/main/java/com/eazybytes/gatewayserver/GatewayserverApplication.java (entrypoint)
  - src/main/resources/application.yaml
  - pom.xml, mvnw
- docker-compose/
  - default/ (infra only by default; app services commented)
  - prod/ (infra + app services enabled)
- readme.md (this file)

## Requirements

- Java 21 (set `JAVA_HOME` accordingly)
- Docker + Docker Compose
- Optional: Maven 3.9+ (or use each module’s `mvnw` wrapper)

## Ports and Endpoints

- Eureka Server: 8070 (dashboard at http://localhost:8070)
- Config Server: 8071
- API Gateway: 8072
- Accounts: 8080
- Loans: 8090
- Cards: 9000
- Actuator endpoints are exposed (see each service’s `application.yml`).

## Local Development (without Docker)

Run infrastructure first, then services. Ensure local MySQL is available, or run DBs via Docker (see below). Local `application.yml` files are configured to use MySQL on localhost with user `root`/`root`.

1) Start Config Server
- Path: `configserver/`
- Command: `./mvnw spring-boot:run`
- Port: 8071

2) Start Eureka Server
- Path: `eurekaserver/`
- Command: `./mvnw spring-boot:run`
- Port: 8070

3) Start domain services (in any order after Eureka/Config are up)
- Accounts (8080): `cd accounts && ./mvnw spring-boot:run`
- Loans (8090): `cd loans && ./mvnw spring-boot:run`
- Cards (9000): `cd cards && ./mvnw spring-boot:run`

4) Start API Gateway (after upstream services are healthy)
- Path: `gatewayserver/`
- Command: `./mvnw spring-boot:run`
- Port: 8072

Swagger UI (if enabled by SpringDoc in the service)
- Accounts: http://localhost:8080/swagger-ui/index.html
- Loans: http://localhost:8090/swagger-ui/index.html
- Cards: http://localhost:9000/swagger-ui/index.html

Health checks (example)
- `curl http://localhost:8080/actuator/health`

## Containerized Setup (Docker Compose)

This repository provides Compose files for different environments.

Option A — Infra only (Config, Eureka, MySQL DBs)
- File: `docker-compose/default/docker-compose.yml`
- Starts: configserver (8071), eurekaserver (8070), MySQL for accounts (3306), loans (3307), cards (3308)
- Domain services and gateway are present but commented out in this file.
- Usage:
  - `cd docker-compose/default`
  - `docker compose up -d`

Option B — Full stack (Infra + All services + Gateway)
- File: `docker-compose/prod/docker-compose.yml`
- Starts: configserver, eurekaserver, gatewayserver (8072), accounts (8080), loans (8090), cards (9000), and their MySQL DBs
- Usage:
  - `cd docker-compose/prod`
  - `docker compose up -d`

Images and tags
- Built via Jib with tag `eazybytes/<artifactId>:s4` (multi-arch: arm64, amd64)
  - accounts: `eazybytes/accounts:s4`
  - cards: `eazybytes/cards:s4`
  - loans: `eazybytes/loans:s4`
  - configserver: `eazybytes/configserver:s4`
  - eurekaserver: `eazybytes/eurekaserver:s4`
  - gatewayserver: `eazybytes/gatewayserver:s4`

## Build

Per-module builds (no root aggregator):
- Accounts: `cd accounts && ./mvnw clean package`
- Cards: `cd cards && ./mvnw clean package`
- Loans: `cd loans && ./mvnw clean package`
- Config: `cd configserver && ./mvnw clean package`
- Eureka: `cd eurekaserver && ./mvnw clean package`
- Gateway: `cd gatewayserver && ./mvnw clean package`

Build container images with Jib (per module):
- `./mvnw jib:dockerBuild` (to local Docker daemon)
- or `./mvnw jib:build` (to a registry; configure `to.image` and credentials)

## Environment Variables and Configuration

Common (from docker-compose/default/common-config.yml)
- SPRING_PROFILES_ACTIVE: typically `default` or `native` (for config server)
- SPRING_CONFIG_IMPORT: e.g., `configserver:http://configserver:8071/`
- SPRING_DATASOURCE_USERNAME: `root`
- SPRING_DATASOURCE_PASSWORD: `root`
- EUREKA_CLIENT_SERVICEURL_DEFAULTZONE: `http://eurekaserver:8070/eureka/`
- SPRING_CLOUD_GATEWAY_SERVER_WEBFLUX_DISCOVERY_LOCATOR_ENABLED: `false`

Service-local database URLs (when running locally without Compose)
- accounts: `jdbc:mysql://localhost:3306/accountsdb`
- loans:    `jdbc:mysql://localhost:3307/loansdb`
- cards:    `jdbc:mysql://localhost:3308/cardsdb`

Notable application properties
- Each service imports config from Config Server: `spring.config.import=optional:configserver:http://localhost:8071/`
- Actuator endpoints are exposed (see each `application.yml`) for health, info, gateway, etc.
- Some controllers reference `build.version` (e.g., Accounts/Card/Loans controllers). Define it via config server or local `application.yml` as needed.
  - TODO: Add `build.version` to central config and document its intended semantics.

## Tests

Run tests per module:
- `cd accounts && ./mvnw test`
- `cd cards && ./mvnw test`
- `cd loans && ./mvnw test`
- `cd configserver && ./mvnw test`
- `cd eurekaserver && ./mvnw test`
- `cd gatewayserver && ./mvnw test`

## API Gateway

- Port: 8072
- Spring Cloud Gateway configured in `gatewayserver/src/main/resources/application.yaml` and Java config (`EazybankRouteLocator`).
- Resilience4j circuit breaker defaults are provided in gateway `application.yaml`.

## Troubleshooting

- Service cannot fetch configuration
  - Ensure Config Server is running on 8071 and reachable
  - Check `spring.config.import` in each service
- Service not registering in Eureka
  - Ensure Eureka is running on 8070
  - Verify `eureka.client.serviceUrl.defaultZone` property
- Database connection errors locally
  - Ensure MySQL containers are running (3306/3307/3308) or adjust URLs
- Port conflicts
  - Change `server.port` in the affected service `application.yml`

## License

TODO: Add license details (e.g., Apache-2.0/MIT) and include a `LICENSE` file at repo root.

## Maintainers

- Project: EazyBytes Microservices Platform (section 2)
- Contact: TODO (add maintainer names/emails or link to issues)