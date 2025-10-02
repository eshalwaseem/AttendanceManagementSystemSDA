package org.example.SERVICE;

import org.example.DAO.StudentDAO;
import org.example.DATA.Student;

import java.sql.SQLException;
import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO;

    public StudentService() {
        this.studentDAO = new StudentDAO();
    }

    public List<Student> getStudentsByCourse(String courseName) {
        try {
            return studentDAO.getStudentsByCourse(courseName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load students", e);
        }
    }

    public void addStudent(String name, String courseName) {
        try {
            studentDAO.addStudent(name, courseName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student", e);
        }
    }

    public void deleteStudent(int studentId) {
        try {
            studentDAO.deleteStudent(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete student", e);
        }
    }

    public List<String> getAllCourseNames() {
        try {
            return studentDAO.getAllCourseNames();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load course names", e);
        }
    }

    public String getAttendanceHistory(int studentId) {
        try {
            return studentDAO.getAttendanceHistory(studentId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get attendance history", e);
        }
    }
}
