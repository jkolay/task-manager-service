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
./mvnw spring-boot:run
```

---

## 👤 Author

Jayati Kolay  
GitHub: https://github.com/jkolay

---

## 📄 License

MIT License
