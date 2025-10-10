# 🏥 Patient Management System (PMS): Enterprise Microservices Architecture

This repository contains the **complete code for a real-world, Enterprise-level Patient Management System (PMS)**.  
The project demonstrates how to design, build, and deploy **production-grade microservices** using **modern enterprise technologies** — integrating backend services, API security, asynchronous communication, and Infrastructure as Code (IaC).

---

## 🚀 Overview

The **Patient Management System (PMS)** provides an end-to-end understanding of how complex distributed systems work in the real world.  
It covers everything from **backend microservices** and **secure API gateways** to **event-driven architecture** and **cloud deployment automation** using **AWS CDK** and **LocalStack**.

/pms-root
│
├── patient-service/
├── auth-service/
├── billing-service/
├── analytic-service/
│
├── api-gateway/
├── docker-compose.yml
├── cdk/
│   ├── src/
│   └── localstack.template.json
│
└── tests/
    └── integration/
---

## 🧠 Core Technology Stack

| **Category** | **Technology / Skill Focus** | **Purpose in PMS** |
|---------------|-----------------------------|--------------------|
| **Backend Core** | Java & Spring Boot | Framework for building microservices fast and efficiently |
| **Architecture** | Microservices | Enables scalability and independent deployment of services |
| **Data Storage** | PostgreSQL & AWS RDS | Persistent data storage and simulation of production setup |
| **Containerization** | Docker | Containerizes services for consistent local and production environments |
| **API / Client Communication** | REST APIs | Handles client communication using HTTP |
| **Service-to-Service Communication** | gRPC & Protocol Buffers | Ensures low-latency, high-performance internal calls |
| **Asynchronous Messaging** | Kafka (MSK) | Enables event-driven, decoupled microservice communication |
| **Security** | JWT (JSON Web Tokens) | Authenticates and secures APIs |
| **Gateway & Routing** | Spring Cloud Gateway | Acts as a single entry point for routing, authentication, and logging |

---

## 🧩 Microservices Overview

The system is composed of **independent and loosely coupled microservices**, each handling a specific business function.

### Core Services:
1. **Patient Service** – Manages patient records (CRUD operations)  
2. **Auth Service** – Handles login, credentials, and JWT generation/validation  
3. **Billing Service** – Manages billing account creation via **gRPC**  
4. **Analytic Service** – Consumes **Kafka events** for analytics and audit logging  

---

## 🔄 Architecture & Communication Flow

### 1️⃣ Client to Microservices (REST + Gateway)
- All client requests (e.g., login, retrieve patients) go through **Spring Cloud Gateway**
- Gateway validates **JWT tokens** before forwarding requests to target services

### 2️⃣ Service-to-Service (gRPC)
- When a patient is created, **Patient Service** triggers a **gRPC request** to **Billing Service**
- Billing Service creates a corresponding billing account instantly

### 3️⃣ Event-Driven (Kafka)
- Patient Service publishes a **"Patient Created"** event to a Kafka topic
- **Analytic Service** consumes this event asynchronously for analysis and logging

---

## 🧰 Local Development Setup

### 🔧 Prerequisites
Make sure you have the following installed:
- **Java JDK 21**
- **Docker Desktop**
- **IntelliJ IDEA Ultimate** (Recommended)
- **Maven** (for builds)
- **PostgreSQL Docker Image**

### 🏗️ Running Locally via Docker

Each microservice includes its own `Dockerfile` supporting **multi-stage builds**:

**Build Steps:**
1. **Build Stage:** Uses Maven + JDK image to compile code and dependencies  
2. **Runner Stage:** Uses a minimal OpenJDK runtime image to execute the `.jar`  


## ☁️ Deployment Strategy — Infrastructure as Code (IaC)

### ⚙️ Tools & Environment
- **IaC Tool:** AWS Cloud Development Kit (**CDK**) – written in Java  
- **Local Environment:** **LocalStack** – simulates AWS services for cost-free local deployment  
- **Deployment Output:** Generates `localstack.template.json` (CloudFormation template)

---

### 🧩 Key AWS Components Simulated

| **AWS Service** | **Purpose** |
|------------------|-------------|
| **VPC** | Private virtual network for isolated resources |
| **ECS Cluster** | Manages all Dockerized microservices |
| **RDS (PostgreSQL)** | Persistent relational database |
| **MSK (Kafka)** | Asynchronous event streaming |
| **ALB (Application Load Balancer)** | Routes external traffic securely to API Gateway |

---

## 🧪 Testing & Validation

### 🎯 Focus:
End-to-end **Integration Tests** verifying multi-service interactions:
- ✅ API Gateway validation  
- ✅ Token verification in Auth Service  
- ✅ CRUD operations in Patient Service  

### 🧰 Tools Used:
- **Rest Assured** – for clean, maintainable API tests  
- **JUnit 5** – for structured test execution  


### 🧪 Example Test Scenario
```java
given()
  .header("Authorization", "Bearer " + token)
  .get("/patients")
.then()
  .statusCode(200)
  .body("size()", greaterThan(0));




To build and run locally:
```bash
docker build -t pms-patient-service ./patient-service
docker run -d -p 8081:8081 --name patient-service pms-patient-service
