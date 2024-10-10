# **Task Management API**

 ## **Overview**

The Task Management API allows users to manage tasks efficiently. Each user must sign up and log in before they can create tasks. The API supports functionalities like user authentication, task creation, task assignment, and task management.

## **Features**

* User Authentication: Users can register and log in to access the system.

* Task Management: Authenticated users can create, update, view, and delete tasks.

* User & Task Relationship: Each task is associated with a user who created it.


## **Technologies Used**

* Java (Spring Boot): Backend framework for the API.
* JPA (Java Persistence API): For database interaction with User and Task entities.
* H2 Database: In-memory database for testing purposes.


## **Entities**
1. **User**

   Represents a person who can sign up, log in, and manage tasks.

**Fields:**
* id (Long): Unique identifier for the user.
* firstName (String): User's First name.
* lastName(String): User's LastName.
* email (String): User's email address (used for login).
* phoneNumber(String) : User's phoneNumber
* Gender(Enum): User's gender
* address(String): User's address
* password (String): User's encrypted password.
* tasks (List<Task>): List of tasks created by the user.

2.   **Task**

 Represents a task that the user can create, update, or delete.

Fields:

* id (Long): Unique identifier for the task.
* title (String): Task's title.
* description (String): Detailed description of the task.
* priority(String ; Enum): Priority of the task (e.g High, Low or Medium)
* status (String): Status of the task (e.g., Pending, In Progress, Completed).
* createdAt (Date): Date when the task was created.
* createdBy (User): Reference to the user who created the task.

# **Getting Started**

### **Prerequisites**

**Ensure you have the following installed:**

* Java 17+
* Maven
* Swagger