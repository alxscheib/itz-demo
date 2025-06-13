# Entwicklerdokumentation – Tutorial Management API

## Übersicht

Dieses Projekt stellt eine RESTful API zur Verwaltung von Tutorials bereit. Es basiert auf dem Spring Boot Framework und nutzt moderne Java-Technologien wie JPA, MapStruct, Lombok und OpenAPI/Swagger zur Dokumentation. Zudem sind automatisierte Unit- und Integrationstests mit JUnit implementiert.

## Technologie-Stack

- **Java 17+**
- **Spring Boot**: Backend Framework
- **Spring Web & Spring Data JPA**:Spring Frameworks für Web/REST schnittstellen und Datenbankzugriff
- **H2 Datenbank**: In-Memory-Datenbank für Entwicklung und Tests
- **Lombok**: Reduzierung von Boilerplate-Code
- **MapStruct**: Automatisches Mapping zwischen DTO und Entity
- **OpenAPI (Swagger)**: API-Dokumentation
- **JUnit 5 & MockMvc**: Test-Framework

## Projektstruktur

```text
src/
├── main/
│   ├── java/ipu/example/demo/
│   │   ├── config/              # OpenAPI-Konfiguration
│   │   ├── controller/          # REST-Controller
│   │   ├── dto/                 # Data Transfer Objects
│   │   ├── mapper/              # MapStruct Mapper für DTO ↔ Entity
│   │   ├── model/               # JPA-Entities und benutzerdefinierte Exceptions
│   │   ├── repository/          # Spring Data JPA Repository
│   │   └── service/             # Services und Interfaces
│   └── resources/               # Applikationsressourcen (application.properties etc.)
└── test/
    ├── java/ipu/example/demo/
    │   ├── controller/          # Tests für Controller
    │   └── service/             # Tests für Services
    └── resources/               # Testressourcen
```

## Wichtige Komponenten

### 1. `Tutorial`
Ein JPA-Entity, das ein Tutorial beschreibt (`id`, `title`, `description`).

### 2. `TutorialDto`
Ein DTO zur Kommunikation mit der API. 

### 3. `TutorialMapper`
MapStruct-Schnittstelle zur Konvertierung zwischen Entity und DTO.

### 4. `TutorialService`
Service-Interface mit typischen CRUD-Operationen, inkl. Suche nach `title` und `description`.

### 5. `TutorialServiceImpl`
Implementierung von `TutorialService`, verwendet `TutorialRepository`.

### 6. `TutorialController`
REST-Controller mit Endpunkten:
- `GET /api/tutorials?title=....&description=....`
- `GET /api/tutorials/{id}`
- `POST /api/tutorials`
- `PUT /api/tutorials/{id}`
- `DELETE /api/tutorials/{id}`
- `DELETE /api/tutorials`

### 7. `OpenAPIConfig`
Konfiguriert die OpenAPI/Swagger-Dokumentation mit mehreren Server-URLs und Metadaten.

### 8. `TutorialRepository`
JPA-Repository mit Methoden zur Textsuche in `title` und `description`.

## Tests

### `TutorialControllerTest`
Integrationstests mit `MockMvc`. Testet u.a.:
- Alle Tutorials abrufen
- Einzelnes Tutorial abrufen

### `TutorialServiceTest`
Unit-Tests für alle Methoden des Services:
- Erstellung, Aktualisierung, Löschung, Anzeigen
- Suche nach title
- Suche nach description

## Konfiguration (`application.properties`)

```properties
server.port=8081

spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.h2.console.path=/h2-ui

springdoc.api-docs.path=/api-docs
springdoc.swagger-ui.path=/swagger-ui

ipu.example.demo.dev-url=http://localhost:8081
ipu.example.demo.test-url=https://to_be_defined.com
```

## Ausführen und Testen

- Projekt mit Maven bauen:
  ```bash
  mvn clean install
  ```

- Spring Boot Anwendung starten:
  ```bash
  mvn spring-boot:run
  ```
- Swagger UI ist verfügbar unter::
  ```bash
  http://localhost:8081/swagger-ui.html
  ```

- Tests können über Postman, Curl oder andere REST-Clients durchgeführt werden.

---

## Hinweise für Entwickler

- Nutze `MapStruct`-Methode zum DTO-Mapping (keine manuelle Konvertierung).
- Validierung erfolgt über Jakarta Bean Validation (`@NotEmpty`).
- Exceptions wie `ServiceException` zentral verwenden.
- Testdaten werden in Tests dynamisch erstellt.

## Erweiterungsmöglichkeiten
- Authentifizierung und Autorisierung (z.B. OAuth2, JWT)
- Paging und Sorting der Tutorials
- Persistente Datenbank (z.B. PostgreSQL)
- Erweiterte Suchfilte
- Erweiterung der Tutorial-Entity (z.B. Autor, Veröffentlichkeitsdatum)
- Verwaltung von Tutorial-Benutzern
- Integration mit Frontend-Anwendung


## Lizenz

Dieses Projekt verwendet **The Unlicense**.
