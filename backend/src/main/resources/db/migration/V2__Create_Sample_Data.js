// Insert sample students for testing
db.students.insertMany([
  {
    "name": "John Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890",
    "start_date": new Date("2024-01-15"),
    "course": "Advanced Mathematics",
    "level": "Intermediate",
    "status": "ACTIVE",
    "monthly_fee": 150.00,
    "payment_day": 15,
    "subscription_expiry": new Date("2024-12-15"),
    "progress": [
      {
        "date": new Date("2024-01-20"),
        "topic": "Calculus Fundamentals",
        "description": "Introduction to derivatives and limits",
        "grade": 85.0,
        "max_grade": 100.0,
        "comments": "Good understanding of basic concepts",
        "created_at": new Date("2024-01-20T10:00:00Z")
      }
    ],
    "payments": [
      {
        "date": new Date("2024-01-15"),
        "amount": 150.00,
        "method": "CREDIT_CARD",
        "reference": "CC001",
        "status": "COMPLETED",
        "month": "January",
        "year": 2024,
        "notes": "Initial payment",
        "created_at": new Date("2024-01-15T09:00:00Z")
      }
    ],
    "notes": "Student shows strong analytical skills",
    "created_at": new Date("2024-01-15T08:00:00Z"),
    "updated_at": new Date("2024-01-20T10:00:00Z")
  },
  {
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "phone": "+1234567891",
    "start_date": new Date("2024-02-01"),
    "course": "English Literature",
    "level": "Beginner",
    "status": "ACTIVE",
    "monthly_fee": 120.00,
    "payment_day": 1,
    "subscription_expiry": new Date("2024-12-01"),
    "progress": [
      {
        "date": new Date("2024-02-10"),
        "topic": "Shakespeare Analysis",
        "description": "Understanding Hamlet's soliloquy",
        "grade": 92.0,
        "max_grade": 100.0,
        "comments": "Excellent interpretation and analysis",
        "created_at": new Date("2024-02-10T14:00:00Z")
      }
    ],
    "payments": [
      {
        "date": new Date("2024-02-01"),
        "amount": 120.00,
        "method": "BANK_TRANSFER",
        "reference": "BT001",
        "status": "COMPLETED",
        "month": "February",
        "year": 2024,
        "notes": "Initial payment",
        "created_at": new Date("2024-02-01T10:00:00Z")
      }
    ],
    "notes": "Student has a natural talent for literary analysis",
    "created_at": new Date("2024-02-01T09:00:00Z"),
    "updated_at": new Date("2024-02-10T14:00:00Z")
  },
  {
    "name": "Mike Johnson",
    "email": "mike.johnson@example.com",
    "phone": "+1234567892",
    "start_date": new Date("2024-01-01"),
    "course": "Physics",
    "level": "Advanced",
    "status": "ACTIVE",
    "monthly_fee": 180.00,
    "payment_day": 5,
    "subscription_expiry": new Date("2024-11-01"),
    "progress": [
      {
        "date": new Date("2024-01-25"),
        "topic": "Quantum Mechanics",
        "description": "Wave-particle duality and uncertainty principle",
        "grade": 88.0,
        "max_grade": 100.0,
        "comments": "Good grasp of complex concepts",
        "created_at": new Date("2024-01-25T16:00:00Z")
      }
    ],
    "payments": [
      {
        "date": new Date("2024-01-05"),
        "amount": 180.00,
        "method": "PIX",
        "reference": "PIX001",
        "status": "COMPLETED",
        "month": "January",
        "year": 2024,
        "notes": "Initial payment",
        "created_at": new Date("2024-01-05T11:00:00Z")
      }
    ],
    "notes": "Student demonstrates excellent problem-solving skills",
    "created_at": new Date("2024-01-01T08:00:00Z"),
    "updated_at": new Date("2024-01-25T16:00:00Z")
  }
]);

print("V2 migration completed: Sample data inserted");
