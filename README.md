The Hospital Management System (HMS) is a desktop-based Java application built using Java Swing for the front-end and MySQL for the back-end.
It streamlines hospital operations such as patient management, room allocation, billing, and record tracking, providing a simple yet effective interface for hospital receptionists and administrators.

🚀 Features

👤 Patient Management

Add new patients with essential details (Name, Gender, Contact, Room, Deposit, etc.)
Display all patient information in a table format.
Update or remove patient details if required.

🏠 Room Management

Automatically allocate available rooms to new patients.
View room availability status.
Free up rooms once a patient is discharged after billing.

💳 Billing System

Calculate total charges based on patient stay duration and room cost.
Generate and display patient bills.
Automatically free the assigned room after billing is completed.

🔐 Login System

Secure login authentication for reception/admin users.
Credentials stored securely in the MySQL database.

📊 Database Management

All records (patients, rooms, billing, login) are stored and retrieved from MySQL.
Supports real-time data refresh to ensure accuracy.

🧱 Tech Stack
Category	Technology
Programming Language	Java
GUI Framework	Java Swing
Database	MySQL
JDBC Driver	MySQL Connector/J
IDE (Recommended)	IntelliJ IDEA / Eclipse / NetBeans
Build Tool	Manual / Ant / Maven (optional)

🗄️ Database Schema
1️⃣ login
Column	Type	Key
ID	VARCHAR(20)	PRIMARY KEY
pw	VARCHAR(20)	
2️⃣ patient_info
Column	Type	Description
ID	VARCHAR(30)	Primary Key (Patient ID)
Number	VARCHAR(40)	ID type number (e.g., Aadhar, Passport)
Name	VARCHAR(100)	Patient name
Gender	VARCHAR(20)	Male/Female
Room	VARCHAR(20)	Assigned room number
Time	DATETIME	Check-in time
Mobile_Number	VARCHAR(15)	Contact number
Deposit	DECIMAL(10,2)	Initial deposit
3️⃣ room
Column	Type	Description
Room_Number	VARCHAR(10)	Primary Key
Availability	VARCHAR(10)	Yes/No
Price	DECIMAL(10,2)	Room rate per day
⚙️ Setup Instructions
🔧 Step 1: Database Setup

Install MySQL Server.

Open MySQL Workbench / phpMyAdmin.

Run the following commands:

CREATE DATABASE hospital_management_system;
USE hospital_management_system;


Import the provided SQL script (hospital_management_system.sql) or manually create tables as shown above.

🖥️ Step 2: Application Setup

Install Java JDK 8 or above.

Open the project in your preferred IDE.

Add the MySQL JDBC Connector (mysql-connector-j.jar) to your project’s classpath.

Update database credentials in your connection file:

Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_management_system", "root", "your_password");

▶️ Step 3: Run the Application

Run Login.java to start the application.

Use credentials:

Username: techcoder
Password: 123456789


Navigate through the dashboard to manage patients, rooms, and billing.

🧮 Modules Overview
Module	Description
Login	Secure entry point for users
Reception Dashboard	Main menu to access all modules
New Patient Form	Add new patients and assign rooms
Patient Info	Display all patient records
Billing Window	Handle patient billing and free rooms
<img width="800" height="506" alt="image" src="https://github.com/user-attachments/assets/f7c04f88-55be-4990-8d53-13a2bcebf3b1" />
<img width="1425" height="749" alt="image" src="https://github.com/user-attachments/assets/0642284e-af26-4aa6-85fb-1c5e4e132609" />
<img width="867" height="575" alt="image" src="https://github.com/user-attachments/assets/79c6c48f-3f16-4a67-827d-12edc728361b" />
<img width="1425" height="695" alt="image" src="https://github.com/user-attachments/assets/47a7d5ee-25b7-4571-95e7-6b4c22bb836d" />
<img width="872" height="523" alt="image" src="https://github.com/user-attachments/assets/276e567f-2cab-47e4-b243-136620dc8b41" />
<img width="870" height="484" alt="image" src="https://github.com/user-attachments/assets/7a496154-bf74-499a-bec2-d438c369648f" />
<img width="1405" height="699" alt="image" src="https://github.com/user-attachments/assets/85c6d75f-7a5c-4639-9cff-2cd138dc2c22" />
<img width="976" height="633" alt="image" src="https://github.com/user-attachments/assets/dbf239e4-d888-4b28-950b-6429aa2816e6" />

💡 Future Enhancements

Add Doctor and Staff management.

Generate printable PDF bills and reports.

Add search and filter options for patients.

Implement multi-user roles (Admin, Receptionist, Doctor).

Integrate email/SMS notifications for discharge updates.
