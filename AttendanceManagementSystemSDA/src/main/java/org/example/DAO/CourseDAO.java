package org.example.DAO;

import org.example.DATA.Course;
import org.example.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO {
    public List<Course> getAllCourses() throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.name, COUNT(s.id) as student_count " +
                "FROM courses c LEFT JOIN students s ON c.id = s.course_id " +
                "GROUP BY c.id, c.name ORDER BY c.name";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                courses.add(new Course(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("student_count")
                ));
            }
        }
        return courses;
    }

    public void addCourse(String name) throws SQLException {
        String sql = "INSERT INTO courses (name) VALUES (?)";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.executeUpdate();
        }
    }

    public void deleteCourse(int courseId) throws SQLException {
        String deleteStudentsSql = "DELETE FROM students WHERE course_id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteStudentsSql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        }

        String deleteCourseSql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(deleteCourseSql)) {
            ps.setInt(1, courseId);
            ps.executeUpdate();
        }
    }

    public void renameCourse(int courseId, String newName) throws SQLException {
        String sql = "UPDATE courses SET name = ? WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, newName);
            ps.setInt(2, courseId);
            ps.executeUpdate();
        }
    }

    public List<Course> searchCourses(String searchTerm) throws SQLException {
        List<Course> courses = new ArrayList<>();
        String sql = "SELECT c.id, c.name, COUNT(s.id) as student_count " +
                "FROM courses c LEFT JOIN students s ON c.id = s.course_id " +
                "WHERE c.name LIKE ? GROUP BY c.id, c.name ORDER BY c.name";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + searchTerm + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    courses.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("student_count")
                    ));
                }
            }
        }
        return courses;
    }
}