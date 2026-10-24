# Student Management System

A console-based Java application that manages student records using Java, JDBC, and MySQL.

## Features

* Register a new student
* Display all students
* Search for a student by ID
* Update student details
* Delete a student record
* Validate user input
* Prevent duplicate student IDs
* Store student records permanently in MySQL

## Technologies Used

* Java
* JDBC
* MySQL
* Maven
* Eclipse IDE
* Git and GitHub

## Project Structure

* `main` — Handles the menu and user input.
* `model` — Represents student data.
* `service` — Handles student-related operations.
* `dao` — Executes database operations using JDBC.
* `util` — Manages the database connection.

## Database Setup

1. Install MySQL and start the MySQL service.

2. Create a database named `student_management`.

3. Create a `student` table with the following columns:

   * `id` — INT, Primary Key
   * `name` — VARCHAR(100), Not Null
   * `email` — VARCHAR(100)
   * `course` — VARCHAR(100)
   * `marks` — DOUBLE

4. Configure the database connection in `DBConnection.java`.

5. Ensure the MySQL JDBC driver is configured in the Maven project.

## How to Run

1. Clone or download the project.
2. Open the project in Eclipse as a Maven project.
3. Configure the MySQL connection details.
4. Ensure the database and table have been created.
5. Run `Main.java`.
6. Use the console menu to manage student records.

## Learning Outcomes

This project demonstrates Java programming, object-oriented programming, exception handling, input validation, JDBC integration, SQL operations, and layered application architecture.

## Future Improvements

* Add a graphical user interface.
* Build REST APIs using Spring Boot.
* Add automated unit tests.
* Improve database error handling.
