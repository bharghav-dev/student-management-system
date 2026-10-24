package com.studentmanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.studentmanagement.model.Student;
import com.studentmanagement.util.DBConnection;

public class StudentDAO {

    // Add a new student
    public boolean addStudent(Student student) {

        String sql = "INSERT INTO student (id, name, email, course, marks) "
                   + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, student.getId());
            statement.setString(2, student.getName());
            statement.setString(3, student.getEmail());
            statement.setString(4, student.getCourse());
            statement.setDouble(5, student.getMarks());

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                return true;
            }

            System.out.println("Student was not registered.");
            return false;

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println(
                    "Registration failed: Student ID already exists.");
            return false;

        } catch (SQLException e) {
            printDatabaseError("register student", e);
            return false;
        }
    }

    // Display all students
    public List<Student> displayAllStudents() {

        List<Student> students = new ArrayList<>();

        String sql = "SELECT * FROM student";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("course"),
                        resultSet.getDouble("marks")
                );

                students.add(student);
            }

        } catch (SQLException e) {
            printDatabaseError("display students", e);
        }

        return students;
    }

    // Search for a student by ID
    public Student searchStudentById(int id) {

        String sql = "SELECT * FROM student WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Student(
                            resultSet.getInt("id"),
                            resultSet.getString("name"),
                            resultSet.getString("email"),
                            resultSet.getString("course"),
                            resultSet.getDouble("marks")
                    );
                }
            }

        } catch (SQLException e) {
            printDatabaseError("search student", e);
        }

        return null;
    }

    // Update an existing student
    public boolean updateStudent(Student student) {

        String sql = "UPDATE student "
                   + "SET name = ?, email = ?, course = ?, marks = ? "
                   + "WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, student.getName());
            statement.setString(2, student.getEmail());
            statement.setString(3, student.getCourse());
            statement.setDouble(4, student.getMarks());
            statement.setInt(5, student.getId());

            int rowsUpdated = statement.executeUpdate();

            if (rowsUpdated > 0) {
                return true;
            }

            System.out.println("No student record was updated.");
            return false;

        } catch (SQLException e) {
            printDatabaseError("update student", e);
            return false;
        }
    }

    // Delete a student
    public boolean deleteStudent(int id) {

        String sql = "DELETE FROM student WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            int rowsDeleted = statement.executeUpdate();

            if (rowsDeleted > 0) {
                return true;
            }

            System.out.println("No student record was deleted.");
            return false;

        } catch (SQLException e) {
            printDatabaseError("delete student", e);
            return false;
        }
    }

    // Print a useful database error message
    private void printDatabaseError(String operation, SQLException e) {

        System.err.println(
                "Database error while attempting to " + operation + ".");

        System.err.println("Error details: " + e.getMessage());
        System.err.println("SQL error code: " + e.getErrorCode());
        System.err.println("SQL state: " + e.getSQLState());
    }
}