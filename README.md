# Consultant Management System

A web-based Consultant Management System developed using Spring Boot, Spring MVC, Thymeleaf, Spring Data JPA, and MySQL.

## Features

- Add a new consultant
- View all consultants
- Edit consultant information
- Delete consultants
- Search consultants by name or technology
- Input validation
- Global exception handling
- Dashboard showing total number of consultants
- Responsive user interface using Bootstrap

## Technologies Used

- Java
- Spring Boot
- Spring MVC
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven
- Bootstrap
- HTML/CSS
- Git/GitHub
- IntelliJ IDEA

## Project Structure

```text
src/main/java/com/simran/consultantmanagement
│
├── controller
│   └── ConsultantController.java
│
├── entity
│   └── Consultant.java
│
├── exception
│   └── GlobalExceptionHandler.java
│
├── repository
│   └── ConsultantRepository.java
│
└── service
    └── ConsultantService.java

src/main/resources
│
├── templates
│   ├── consultant-form.html
│   ├── consultants.html
│   ├── dashboard.html
│   └── error.html
│
└── application.properties

database
└── consultant_management.sql