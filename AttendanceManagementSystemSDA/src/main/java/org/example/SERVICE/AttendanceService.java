package org.example.SERVICE;

import org.example.DAO.AttendanceDAO;
import org.example.DATA.Attendance;

import java.sql.SQLException;
import java.util.List;

public class AttendanceService {
    private final AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    public void markAttendance(List<Attendance> attendances) {
        try {
            attendanceDAO.markAttendance(attendances);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to mark attendance", e);
        }
    }

    public String getCourseStatistics(int courseId, String courseName) {
        try {
            return attendanceDAO.getCourseStatistics(courseId, courseName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get course statistics", e);
        }
    }

    public String getAttendanceSummary() {
        try {
            return attendanceDAO.getAttendanceSummary();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get attendance summary", e);
        }
    }
}