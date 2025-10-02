package org.example.DAO;

import org.example.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.example.DATA.Student;
public class StudentDAO {
    public List<Student> getStudentsByCourse(String courseName) throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = """
            SELECT s.id, s.name, 
                   (SELECT a.attendance_status
                    FROM attendance a
                    WHERE a.student_id = s.id
                    ORDER BY a.attendance_date DESC
                    LIMIT 1) AS latest_status
            FROM students s
            JOIN courses c ON s.course_id = c.id
            WHERE c.name = ?""";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseName);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String status = rs.getString("latest_status");
                    if (status == null) status = "No record";
                    students.add(new Student(
                            rs.getInt("id"),
                            rs.getString("name"),
                            courseName,
                            status
                    ));
                }
            }
        }
        return students;
    }
    public String getAttendanceHistory(int studentId) throws SQLException {
        StringBuilder history = new StringBuilder();
        String sql = """
        SELECT attendance_date, attendance_status 
        FROM attendance 
        WHERE student_id = ?
        ORDER BY attendance_date DESC""";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                while (rs.next()) {
                    history.append(String.format("%s - %s%n",
                            rs.getString("attendance_date"),
                            rs.getString("attendance_status")));
                    found = true;
                }
                if (!found) {
                    history.append("No attendance records found.");
                }
            }
        }
        return history.toString();
    }

    public void addStudent(String name, String courseName) throws SQLException {
        String sql = "INSERT INTO students (name, course_id) VALUES (?, " +
                "(SELECT id FROM courses WHERE name = ?))";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, courseName);
            ps.executeUpdate();
        }
    }

    public void deleteStudent(int studentId) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ps.executeUpdate();
        }
    }

    public List<String> getAllCourseNames() throws SQLException {
        List<String> courseNames = new ArrayList<>();
        String sql = "SELECT name FROM courses";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courseNames.add(rs.getString("name"));
            }
        }
        return courseNames;
    }
}
