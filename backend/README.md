# Tutor Desk - Student Management System

A comprehensive backend system for teachers to manage students, track progress, and handle monthly fees with automated notifications.

## 🚀 Features

- **Student Management**: Register, update, and manage student information
- **Progress Tracking**: Record and monitor student academic progress
- **Fee Management**: Track monthly payments and subscription status
- **Automated Notifications**: Email alerts for subscription expiry and payment reminders
- **RESTful API**: Complete REST API with Swagger documentation
- **MongoDB Integration**: NoSQL database with Flyway migrations
- **Docker Support**: Easy deployment with Docker and Docker Compose

## 🏗️ Architecture

The project follows Clean Architecture principles with the following structure:

```
src/
├── main/java/com/corespace/tutordesk/
│   ├── domain/           # Business entities and core business logic
│   ├── application/      # Application services and DTOs
│   └── infrastructure/  # Controllers, repositories, and external integrations
```

## 🛠️ Technology Stack

- **Java 17** with Spring Boot 3.5.5
- **Spring Data MongoDB** for database operations
- **Flyway** for database migrations
- **Lombok** for reducing boilerplate code
- **Swagger/OpenAPI** for API documentation
- **Docker** for containerization
- **MongoDB** as the primary database

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- Docker and Docker Compose
- MongoDB (if running locally)

## 🚀 Quick Start

### Option 1: Using Docker Compose (Recommended)

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Tutor-Desk
   ```

2. **Set environment variables** (optional)
   ```bash
   export MAIL_USERNAME=your-email@gmail.com
   export MAIL_PASSWORD=your-app-password
   ```

3. **Run with Docker Compose**
   ```bash
   docker-compose up -d
   ```

4. **Access the application**
   - API: http://localhost:8080/api
   - Swagger UI: http://localhost:8080/api/swagger-ui.html
   - MongoDB Express: http://localhost:8081

### Option 2: Local Development

1. **Start MongoDB locally**
   ```bash
   docker run -d -p 27017:27017 --name mongodb mongo:7.0
   ```

2. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

## 📚 API Documentation

### Base URL
```
http://localhost:8080/api
```

### Student Management Endpoints

#### Create Student
```http
POST /students
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "startDate": "2024-01-15",
  "course": "Advanced Mathematics",
  "level": "Intermediate",
  "monthlyFee": 150.00,
  "paymentDay": 15,
  "notes": "Student shows strong analytical skills"
}
```

#### Get All Students
```http
GET /students
```

#### Get Student by ID
```http
GET /students/{id}
```

#### Update Student
```http
PUT /students/{id}
Content-Type: application/json

{
  "name": "John Doe Updated",
  "email": "john.doe@example.com",
  "phone": "+1234567890",
  "startDate": "2024-01-15",
  "course": "Advanced Mathematics",
  "level": "Advanced",
  "monthlyFee": 180.00,
  "paymentDay": 15,
  "notes": "Student shows excellent analytical skills"
}
```

#### Delete Student
```http
DELETE /students/{id}
```

#### Get Students by Status
```http
GET /students/status/{status}
```
Status values: `ACTIVE`, `INACTIVE`, `SUSPENDED`, `GRADUATED`

#### Get Students by Course
```http
GET /students/course/{course}
```

### Progress Management

#### Add Progress Entry
```http
POST /students/{id}/progress?topic=Calculus&description=Derivatives&grade=85&maxGrade=100&comments=Good work
```

### Payment Management

#### Add Payment
```http
POST /students/{id}/payments?amount=150.00&method=CREDIT_CARD&month=January&year=2024&notes=Monthly payment
```

Payment methods: `CASH`, `BANK_TRANSFER`, `CREDIT_CARD`, `DEBIT_CARD`, `PIX`

### Subscription Management

#### Get Students with Expiring Subscription
```http
GET /students/expiring?daysBeforeExpiry=7
```

#### Extend Subscription
```http
PUT /students/{id}/subscription?monthsToAdd=3
```

## 🗄️ Database Schema

### Students Collection
```json
{
  "_id": "ObjectId",
  "name": "String",
  "email": "String (unique)",
  "phone": "String",
  "start_date": "Date",
  "course": "String",
  "level": "String",
  "status": "Enum (ACTIVE, INACTIVE, SUSPENDED, GRADUATED)",
  "monthly_fee": "Number",
  "payment_day": "Number (1-31)",
  "subscription_expiry": "Date",
  "progress": [
    {
      "date": "Date",
      "topic": "String",
      "description": "String",
      "grade": "Number",
      "max_grade": "Number",
      "comments": "String",
      "created_at": "Date"
    }
  ],
  "payments": [
    {
      "date": "Date",
      "amount": "Number",
      "method": "Enum (CASH, BANK_TRANSFER, CREDIT_CARD, DEBIT_CARD, PIX)",
      "reference": "String",
      "status": "Enum (PENDING, COMPLETED, FAILED, CANCELLED)",
      "month": "String",
      "year": "Number",
      "notes": "String",
      "created_at": "Date"
    }
  ],
  "notes": "String",
  "created_at": "Date",
  "updated_at": "Date"
}
```

## 🔧 Configuration

### Application Properties
Key configuration options in `application.properties`:

```properties
# MongoDB Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=tutor_desk

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${MAIL_USERNAME:}
spring.mail.password=${MAIL_PASSWORD:}

# Notification Settings
app.notification.expiry-days-before=7
app.notification.check-interval=86400000
```

### Environment Variables
- `MAIL_USERNAME`: Gmail username for sending notifications
- `MAIL_PASSWORD`: Gmail app password for sending notifications

## 📅 Scheduled Tasks

The system includes automated tasks that run daily:

- **9:00 AM**: Check for students with expiring subscriptions and send notifications
- **8:00 AM**: Send payment reminders to students whose payment day is today or tomorrow

## 🧪 Testing

### Run Tests
```bash
mvn test
```

### Test with Sample Data
The system includes sample data that gets loaded automatically via Flyway migrations.

## 🐳 Docker Commands

### Build and Run
```bash
# Build the application
docker build -t tutor-desk .

# Run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f

# Stop services
docker-compose down

# Stop and remove volumes
docker-compose down -v
```

### Individual Services
```bash
# Start only MongoDB
docker-compose up mongodb

# Start only the application
docker-compose up tutor-desk-app

# Access MongoDB shell
docker exec -it tutor-desk-mongodb mongosh -u admin -p password123
```

## 📊 Monitoring and Management

### MongoDB Express
Access the web-based MongoDB admin interface at:
- URL: http://localhost:8081
- Username: admin
- Password: password123

### Application Logs
```bash
# View application logs
docker-compose logs -f tutor-desk-app

# View MongoDB logs
docker-compose logs -f mongodb
```

## 🔒 Security Considerations

- Email credentials should be stored as environment variables
- MongoDB authentication is enabled by default
- Consider implementing JWT authentication for production use
- Validate all input data to prevent injection attacks

## 🚀 Production Deployment

For production deployment:

1. **Update configuration** for production MongoDB instance
2. **Set proper environment variables** for email and database credentials
3. **Configure SSL/TLS** for secure communication
4. **Set up monitoring** and logging solutions
5. **Implement proper backup strategies** for MongoDB data

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

## 🆘 Support

For support and questions:
- Create an issue in the repository
- Contact the development team
- Check the API documentation at `/api/swagger-ui.html`

## 🔄 Version History

- **v1.0.0**: Initial release with core student management functionality
- Core features: Student CRUD, Progress tracking, Payment management, Automated notifications
