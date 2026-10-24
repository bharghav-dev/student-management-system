package com.studentmanagement.main;

import java.util.Scanner;

import com.studentmanagement.model.Student;
import com.studentmanagement.service.StudentService;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        StudentService service = new StudentService();

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. Display All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("6. Exit");

            choice = readInt(scanner, "Enter your choice: ");

            switch (choice) {

                case 1: {
                    System.out.println("\n--- Register Student ---");

                    int id = readStudentId(scanner, "Enter Student ID: ");

                    String name = readPersonName(
                            scanner, "Enter Student Name: ");

                    String email = readEmail(scanner);

                    String course = readNonEmptyString(
                            scanner, "Enter Course: ");

                    double marks = readMarks(scanner, "Enter Marks: ");

                    Student student = new Student(
                            id, name, email, course, marks);

                    if (service.addStudent(student)) {
                        System.out.println(
                                "Student registered successfully!");
                    } else {
                        System.out.println(
                                "Could not register student. "
                                + "The ID may already exist, or a database error occurred.");
                    }

                    break;
                }

                case 2: {
                    System.out.println("\n--- All Students ---");
                    service.displayAllStudents();
                    break;
                }

                case 3: {
                    System.out.println("\n--- Search Student ---");

                    int searchId = readStudentId(
                            scanner, "Enter Student ID to search: ");

                    Student foundStudent =
                            service.searchStudentById(searchId);

                    if (foundStudent != null) {
                        foundStudent.displayStudent();
                    } else {
                        System.out.println(
                                "Student not found, or a database error occurred.");
                    }

                    break;
                }

                case 4: {
                    System.out.println("\n--- Update Student ---");

                    int updateId = readStudentId(
                            scanner, "Enter Student ID to update: ");

                    Student existingStudent =
                            service.searchStudentById(updateId);

                    if (existingStudent == null) {
                        System.out.println(
                                "Student not found, or a database error occurred.");
                        break;
                    }

                    System.out.println(
                            "Leave a field empty to keep its current value.");

                    System.out.print(
                            "Enter New Name (" + existingStudent.getName() + "): ");

                    String newName = scanner.nextLine().trim();

                    if (!newName.isEmpty()) {
                        while (!isValidPersonName(newName)) {
                            System.out.println(
                                    "Invalid name! Enter a name containing letters, not numbers only.");
                            System.out.print("Enter New Name: ");
                            newName = scanner.nextLine().trim();

                            if (newName.isEmpty()) {
                                newName = existingStudent.getName();
                                break;
                            }
                        }
                    } else {
                        newName = existingStudent.getName();
                    }

                    System.out.print(
                            "Enter New Email (" + existingStudent.getEmail() + "): ");

                    String newEmail = scanner.nextLine().trim();

                    if (!newEmail.isEmpty()) {
                        while (!isValidEmail(newEmail)) {
                            System.out.println(
                                    "Invalid email! Please enter a valid email address.");
                            System.out.print("Enter New Email: ");
                            newEmail = scanner.nextLine().trim();

                            if (newEmail.isEmpty()) {
                                newEmail = existingStudent.getEmail();
                                break;
                            }
                        }
                    } else {
                        newEmail = existingStudent.getEmail();
                    }

                    System.out.print(
                            "Enter New Course (" + existingStudent.getCourse() + "): ");

                    String newCourse = scanner.nextLine().trim();

                    if (newCourse.isEmpty()) {
                        newCourse = existingStudent.getCourse();
                    }

                    double newMarks = readMarks(
                            scanner, "Enter New Marks: ");

                    boolean updated = service.updateStudent(
                            updateId, newName, newEmail,
                            newCourse, newMarks);

                    if (updated) {
                        System.out.println(
                                "Student updated successfully!");
                    } else {
                        System.out.println(
                                "Could not update student. Please check the database connection.");
                    }

                    break;
                }

                case 5: {
                    System.out.println("\n--- Delete Student ---");

                    int deleteId = readStudentId(
                            scanner, "Enter Student ID to delete: ");

                    Student studentToDelete =
                            service.searchStudentById(deleteId);

                    if (studentToDelete == null) {
                        System.out.println(
                                "Student not found, or a database error occurred.");
                        break;
                    }

                    System.out.print(
                            "Are you sure you want to delete this student? (yes/no): ");

                    String confirmation = scanner.nextLine().trim();

                    if (confirmation.equalsIgnoreCase("yes")) {
                        if (service.deleteStudent(deleteId)) {
                            System.out.println(
                                    "Student deleted successfully!");
                        } else {
                            System.out.println(
                                    "Could not delete student.");
                        }
                    } else {
                        System.out.println("Delete cancelled.");
                    }

                    break;
                }

                case 6: {
                    System.out.println(
                            "Thank you for using Student Management System!");
                    break;
                }

                default: {
                    System.out.println(
                            "Invalid choice! Please enter a number from 1 to 6.");
                }
            }

        } while (choice != 6);

        scanner.close();
    }

    // Reads an integer and rejects non-numeric input.
    private static int readInt(Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } else {
                System.out.println(
                        "Invalid input! Please enter a whole number.");
                scanner.nextLine();
            }
        }
    }

    // Reads a positive student ID.
    private static int readStudentId(
            Scanner scanner, String prompt) {

        while (true) {
            int id = readInt(scanner, prompt);

            if (id > 0) {
                return id;
            }

            System.out.println(
                    "Invalid ID! Student ID must be greater than zero.");
        }
    }

    // Reads a required name containing letters.
    private static String readPersonName(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);
            String name = scanner.nextLine().trim();

            if (isValidPersonName(name)) {
                return name;
            }

            System.out.println(
                    "Invalid name! Enter a name containing letters. "
                    + "Numbers only and empty names are not allowed.");
        }
    }

    // Validates names, allowing spaces, hyphens and apostrophes.
    private static boolean isValidPersonName(String name) {

        return name != null
                && name.matches("(?U)[\\p{L}][\\p{L} '\\-]*");
    }

    // Reads and validates an email address.
    private static String readEmail(Scanner scanner) {

        while (true) {
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();

            if (isValidEmail(email)) {
                return email;
            }

            System.out.println(
                    "Invalid email! Please enter a valid email address.");
        }
    }

    private static boolean isValidEmail(String email) {

        return email != null
                && email.matches(
                        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    // Reads a required text field and rejects numbers only.
    private static String readNonEmptyString(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();

            if (value.isEmpty()) {
                System.out.println(
                        "This field cannot be empty!");
            } else if (value.matches("\\d+")) {
                System.out.println(
                        "Invalid input! Please enter text, not numbers only.");
            } else {
                return value;
            }
        }
    }

    // Reads marks between 0 and 100.
    private static double readMarks(
            Scanner scanner, String prompt) {

        while (true) {
            System.out.print(prompt);

            if (scanner.hasNextDouble()) {
                double marks = scanner.nextDouble();
                scanner.nextLine();

                if (Double.isFinite(marks)
                        && marks >= 0 && marks <= 100) {
                    return marks;
                }

                System.out.println(
                        "Invalid marks! Enter a value between 0 and 100.");
            } else {
                System.out.println(
                        "Invalid input! Please enter numeric marks.");
                scanner.nextLine();
            }
        }
    }
}