package org.example.DAO;

import org.example.DATA.Attendance;
import org.example.DatabaseConnectionManager;

import java.sql.*;
//import java.util.ArrayList;
import java.util.List;

public class AttendanceDAO {
    public void markAttendance(List<Attendance> attendances) throws SQLException {
        String sql = "INSERT INTO attendance (student_id, attendance_status, attendance_date) VALUES (?, ?, CURDATE())";
        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (Attendance attendance : attendances) {
                ps.setInt(1, attendance.getStudentId());
                ps.setString(2, attendance.getStatus());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public String getAttendanceHistory(int studentId) throws SQLException {
        StringBuilder historyText = new StringBuilder();
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
                    String date = rs.getString("attendance_date");
                    String status = rs.getString("attendance_status");
                    historyText.append(String.format("%s - %s%n", date, status));
                    found = true;
                }
                if (!found) {
                    historyText.append("No attendance records found.");
                }
            }
        }
        return historyText.toString();
    }

    public String getCourseStatistics(int courseId, String courseName) throws SQLException {
        StringBuilder stats = new StringBuilder();
        String attendanceSql = """
            SELECT 
                COUNT(CASE WHEN a.attendance_status = 'Present' THEN 1 END) AS present_count,
                COUNT(a.id) AS total_record
            FROM students s
            LEFT JOIN attendance a ON s.id = a.student_id
            WHERE s.course_id = ?""";

        String topCourseQuery = """
            SELECT c.name, 
                   COUNT(CASE WHEN a.attendance_status = 'Present' THEN 1 END) * 100.0 / 
                   COUNT(*) as attendance_rate
            FROM courses c
            JOIN students s ON c.id = s.course_id
            JOIN attendance a ON s.id = a.student_id
            GROUP BY c.id
            ORDER BY attendance_rate DESC
            LIMIT 1""";

        String lowAttendanceQuery = """
            SELECT  
                s.id,
                s.name,
                c.name AS course,
                ROUND((COUNT(CASE WHEN a.attendance_status = 'Present' THEN 1 END) * 100.0 / 
                      COUNT(*)), 2) AS attendance_percentage
            FROM students s
            JOIN courses c ON s.course_id = c.id
            JOIN attendance a ON s.id = a.student_id
            WHERE s.course_id = ?
            GROUP BY s.id
            HAVING attendance_percentage < 75 
            ORDER BY attendance_percentage ASC
            LIMIT 1""";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(attendanceSql)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int present = rs.getInt("present_count");
                    int total = rs.getInt("total_record");
                    double percentage = (total == 0) ? 0.0 : (present * 100.0) / total;
                    stats.append(String.format(
                            "Course: %s%nTotal Attendance Records: %d%nPresent: %d%nAttendance Rate: %.2f%%%n%n",
                            courseName, total, present, percentage));
                }
            }
        }

        try (Connection conn = DatabaseConnectionManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(topCourseQuery)) {
            if (rs.next()) {
                String topCourseName = rs.getString("name");
                double topRate = rs.getDouble("attendance_rate");
                stats.append(String.format("Top Course: %s (%.2f%% Attendance)%n%n", topCourseName, topRate));
            }
        }

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(lowAttendanceQuery)) {
            ps.setInt(1, courseId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String lowAttendanceStudent = rs.getString("name");
                    String lowAttendanceCourse = rs.getString("course");
                    double lowAttendancePercentage = rs.getDouble("attendance_percentage");
                    stats.append(String.format("Lowest Attendance Student: %s in %s (%.2f%% Attendance)",
                            lowAttendanceStudent, lowAttendanceCourse, lowAttendancePercentage));
                }
            }
        }

        return stats.toString();
    }

    public String getAttendanceSummary() throws SQLException {
        StringBuilder report = new StringBuilder();
        String sql = """
            SELECT
                c.name AS course_name,
                COUNT(CASE WHEN a.attendance_status = 'Present' THEN 1 END) AS total_present,
                COUNT(CASE WHEN a.attendance_status = 'Absent' THEN 1 END) AS total_absent
            FROM courses c
            JOIN students s ON c.id = s.course_id
            JOIN attendance a ON s.id = a.student_id
            GROUP BY c.id
            ORDER BY c.name""";

        try (Connection conn = DatabaseConnectionManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String courseName = rs.getString("course_name");
                int totalPresent = rs.getInt("total_present");
                int totalAbsent = rs.getInt("total_absent");
                report.append("Course: ").append(courseName).append("\n")
                        .append("Present: ").append(totalPresent).append("\n")
                        .append("Absent: ").append(totalAbsent).append("\n\n");
            }
        }
        return report.toString();
    }
}