// Create students collection with indexes
db.createCollection("students");

// Create indexes for better query performance
db.students.createIndex({ "email": 1 }, { unique: true });
db.students.createIndex({ "status": 1 });
db.students.createIndex({ "course": 1 });
db.students.createIndex({ "subscription_expiry": 1 });
db.students.createIndex({ "payment_day": 1 });
db.students.createIndex({ "start_date": 1 });
db.students.createIndex({ "created_at": 1 });

// Create compound index for subscription expiry queries
db.students.createIndex({ "status": 1, "subscription_expiry": 1 });

print("V1 migration completed: Students collection and indexes created");
