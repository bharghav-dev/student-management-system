package com.studentmanagement.service;

import com.studentmanagement.dao.StudentDAO;
import com.studentmanagement.model.Student;

import java.util.List;

public class StudentService {

private StudentDAO studentDAO = new StudentDAO();

public boolean addStudent(Student student) {
    return studentDAO.addStudent(student);
}

public void displayAllStudents() {
    List<Student> students = studentDAO.displayAllStudents();

    if (students.isEmpty()) {
        System.out.println("No students found.");
        return;
    }

    for (Student student : students) {
        student.displayStudent();
    }
}

public Student searchStudentById(int id) {
    return studentDAO.searchStudentById(id);
}

public boolean updateStudent(int id, String name, String email,
                             String course, double marks) {

    Student student = new Student(id, name, email, course, marks);

    return studentDAO.updateStudent(student);
}

public boolean deleteStudent(int id) {
    return studentDAO.deleteStudent(id);
}

}
