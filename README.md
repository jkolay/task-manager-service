# 📌 Task Manager Service

A simple and scalable **Task Management REST API** built using **Java (Spring Boot)**.  
This service allows users to create, update, and manage tasks with status tracking.

---

## 🚀 Features

- ✅ Create a new task  
- ✅ Update task details  
- ✅ Automatic task status progression  
- ✅ Enum-based status management  
- ✅ Clean layered architecture (Controller → Service → Repository)  
- ✅ Logging for observability  

---

## 🏗️ Tech Stack

- **Java 21**
- **Spring Boot**
- **Spring Data JPA**
- **Maven**
- **MapStruct**

---

## 🧠 Task Status Flow

OPEN → IN_PROGRESS → DONE

If no status is provided:

- ✅ OPEN → IN_PROGRESS
- ✅ IN_PROGRESS → DONE
- ✅ DONE → remains DONE
---
## Mapping to packages

- Controller: src/main/java/com/task/controller/TaskController.java
- Service: src/main/java/com/task/service/TaskService.java
- Mapper: src/main/java/com/task/mapper/TaskMapper.java
- Repository: src/main/java/com/task/repository/
- Entities & DTOs: src/main/java/com/task/entity and src/main/java/com/task/dto
- Config: src/main/java/com/task/config
- Exception handling: src/main/java/com/task/exception
---

##  Database Flow
The application uses a tasks table mapped via the Task JPA entity.

- Primary Key: UUID (id)
- Fields: title, description, status, priority
- Auditing: Automatically tracks createdAt and updatedAt
- Optimistic Locking: Managed using a version field
- Indexes: Added on status and priority for faster filtering
The design ensures efficient querying, data consistency, and support for concurrent updates
---
## 🔌 API Endpoints

### ➕ Create Task
- Create a new task
```bash
curl -X 'POST' \
  'http://127.0.0.1:8085/tasks' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer client-a-token' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Implement login feature",
  "description": "Support OAuth2 and basic auth flows",
  "status": "OPEN",
  "priority": "HIGH"
}'
 ```

### 🔄 Update Task
- Update an existing task
```bash
curl -X 'PUT' \
  'http://127.0.0.1:8085/tasks/97a1ad1e-c5f6-4fc2-b908-a9ae260fcb78' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer client-a-token' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Implement login feature",
  "description": "Support OAuth2 and basic auth flows",
  "priority": "HIGH"
}
 ```

### 📋 Get Tasks
- Retrive task list with filtering and pagination
```bash
curl -X 'GET' \
  'http://127.0.0.1:8085/tasks?status=DONE&priority=LOW&page=0&size=20' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer client-a-token'
 ```

### 🔍 Get Task by ID
- Retrieve a task by its Task Id
```bash
curl -X 'GET' \
  'http://127.0.0.1:8085/tasks/550e8400-e29b-41d4-a716-446655440000' \
  -H 'accept: */*' \
  -H 'Authorization: Bearer client-a-token'
 ```
---

## ⚙️ How to Run

```bash
git clone https://github.com/jkolay/task-manager-service.git
cd task-manager-service

## Build & Run Commands


# Build and run tests
mvn clean test

# Run with dev profile (no real IdP needed)
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Generate test coverage report
mvn clean test jacoco:report
# Report at: target/site/jacoco/index.html

# Package as JAR
mvn clean package -DskipTests
java -jar target/task-manager-service-1.0.0.jar --spring.profiles.active=dev
```



---
### ✅ Run Container (DEV profile)

```bash
docker run -p 8085:8085 \
           -p 8084:8084 \
           -e SPRING_PROFILES_ACTIVE=dev \
           task-manager-service
```
---
### ✅ Swagger

Please access the swagger ui at
```bash
http://127.0.0.1:8085/swagger-ui/index.html
```
Authenticate with value
```bash
client-a-token

```

## 👤 Author

Jayati Kolay  
GitHub: https://github.com/jkolay


