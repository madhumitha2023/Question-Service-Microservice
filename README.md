**Question-Service**
**Overview**
The Question-Service is a microservice within the Java Quiz Application that manages questions and their associated metadata. This service is part of a larger microservices architecture that powers a quiz management system, enabling the creation, retrieval, and management of questions for quizzes. The service is built using Spring Boot and provides RESTful APIs for interaction.

**Key Features**
CRUD Operations: Create, Read, Update, and Delete questions.
Microservices Architecture: Designed to integrate seamlessly with other services, including Quiz-Service, Service-Registry, and API-Gateway.
Database Integration: Persists question data using MySQL.
Service Discovery: Registers with Eureka Server for seamless service discovery.
Load Balancing: Supports running multiple instances for scalability.
Resiliency: Configured to work with API Gateway for routing and error handling.

**Technologies Used**
Java 17: Primary programming language.
Spring Boot 3.x: Framework for building and running the microservice.
Spring Cloud Netflix Eureka: Service discovery for microservices.
Spring Data JPA: ORM for database interaction.
H2/MySQL/PostgreSQL: MySQL relational database.
Maven: Build and dependency management.
Docker (Optional): Containerization for deployment.

**Project Structure**
src/
├── main/
│   ├── java/
│   │   └── com.example.questionservice/
│   │       ├── controller/    // Contains REST API controllers
│   │       ├── service/       // Business logic layer
│   │       ├── repository/    // Data access layer
│   │       ├── model/         // Entity classes
│   │       └── QuestionServiceApplication.java
│   └── resources/
│       ├── application.yml    // Configuration file
│       └── data.sql           // Sample data
└── test/                      // Unit and integration tests

**API Endpoints**
Method	Endpoint	Description
GET	/api/questions	Fetch all questions
GET	/api/questions/{id}	Fetch a question by its ID
POST	/api/questions	Create a new question
PUT	/api/questions/{id}	Update an existing question
DELETE	/api/questions/{id}	Delete a question by its ID
**Running the Service**
Prerequisites
Java 17 or higher
Maven
(Optional) Docker
Steps to Run
Clone the repository:
Copy code
git clone https://github.com/yourusername/Question-Service.git
cd Question-Service

**Build the application:**
Copy code
mvn clean install

**Run the application:**
Copy code
mvn spring-boot:run
Access the APIs:

Base URL: http://localhost:8080/api/questions
Eureka Registration
Ensure that the Eureka server is running on port 8761. The Question-Service will automatically register with the Eureka server at http://localhost:8761/eureka.

**Contribution Guidelines**
Fork the repository.
Create a new feature branch:
Copy code
git checkout -b feature/your-feature-name
Commit and push your changes.
Create a pull request.
