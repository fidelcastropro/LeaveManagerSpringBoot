# Leave It To Us - College Leave Management System

A comprehensive web-based leave management system built with Spring Boot for managing student leave requests in a college environment.

## Features

- **Multi-Role Support**: Separate dashboards for Students, Faculty, HOD, and Warden
- **Leave Request Management**: Students can submit hostel leave and outing requests
- **Approval Workflow**: Hierarchical approval system with Faculty, HOD, and Warden roles
- **Leave Tracking**: Real-time status tracking for submitted leave requests
- **History Management**: Complete audit trail of all leave requests and approvals
- **User Authentication**: Secure login system with role-based access control

## Tech Stack

- **Backend**: Spring Boot 3.5.7
- **Language**: Java 21
- **Database**: MySQL 8
- **ORM**: Spring Data JPA with Hibernate
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven
- **Additional Libraries**: 
  - Lombok (for reducing boilerplate code)
  - Spring Validation
  - Spring DevTools

## Prerequisites

- Java 21 or higher
- MySQL 8.0 or higher
- Maven 3.6+

## Database Setup

1. Create a MySQL database:
```sql
CREATE DATABASE leaveDB;
```

2. Update database credentials in `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/leaveDB
spring.datasource.username=<your-username>
spring.datasource.password=<your-password>
```

## Installation & Running

1. Clone the repository:
```bash
git clone <repository-url>
cd leaveittous
```

2. Build the project:
```bash
./mvnw clean install
```

3. Run the application:
```bash
./mvnw spring-boot:run
```

The application will start on `http://localhost:8081`

## Project Structure

```
leaveittous/
├── src/main/java/com/example/leaveittous/
│   ├── controller/          # REST controllers
│   │   ├── LoginController
│   │   ├── LeaveController
│   │   ├── FacultyDashboardController
│   │   ├── HodDashboardController
│   │   └── studentLeaveStatusController
│   ├── entity/              # JPA entities
│   │   ├── Student
│   │   ├── Faculty
│   │   ├── HOD
│   │   ├── Warden
│   │   ├── LeaveRequest
│   │   ├── Credentials
│   │   └── History
│   ├── repository/          # Data access layer
│   ├── service/             # Business logic layer
│   └── LeaveittousApplication.java
├── src/main/resources/
│   ├── static/              # Static resources (CSS, images, HTML)
│   ├── templates/           # Thymeleaf templates
│   └── application.properties
└── pom.xml
```

## User Roles

1. **Student**: 
   - Submit leave requests
   - View leave status
   - Track approval history

2. **Faculty**: 
   - Review and approve/reject student leave requests
   - View pending and granted approvals

3. **HOD (Head of Department)**: 
   - Review faculty-approved requests
   - Final approval authority for departmental leaves

4. **Warden**: 
   - Manage hostel-related leave requests
   - Approve/reject hostel leaves and outings

## API Endpoints

- `/login` - User authentication
- `/student/*` - Student leave operations
- `/faculty/*` - Faculty dashboard and approvals
- `/hod/*` - HOD dashboard and approvals
- `/leave/*` - Leave request management

## Configuration

Key application properties:
- **Server Port**: 8081
- **Database**: MySQL (leaveDB)
- **JPA**: Auto DDL update enabled
- **SQL Logging**: Enabled for debugging

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

# Author

**Antony Fidel Castro A**

Engineering Student – Information Technology
Interested in:

* Full Stack Development
* Artificial Intelligence
* Machine Learning
* Computer Vision



