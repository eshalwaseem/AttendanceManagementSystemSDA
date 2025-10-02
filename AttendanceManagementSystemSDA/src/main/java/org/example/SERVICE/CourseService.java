package org.example.SERVICE;

import java.sql.SQLException;
import java.util.List;

import org.example.DAO.CourseDAO;
import org.example.DATA.Course;

public class CourseService {
    private final CourseDAO courseDAO;
    private final AttendanceService attendanceService;


    public CourseService() {
        this.courseDAO = new CourseDAO();
        this.attendanceService = new AttendanceService();

    }

    public List<Course> getAllCourses() {
        try {
            return courseDAO.getAllCourses();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load courses", e);
        }
    }

    public void addCourse(String name) {
        try {
            courseDAO.addCourse(name);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to add course", e);
        }
    }

    public void deleteCourse(int courseId) {
        try {
            courseDAO.deleteCourse(courseId);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete course", e);
        }
    }

    public void renameCourse(int courseId, String newName) {
        try {
            courseDAO.renameCourse(courseId, newName);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to rename course", e);
        }
    }

    public List<Course> searchCourses(String searchTerm) {
        try {
            return courseDAO.searchCourses(searchTerm);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to search courses", e);
        }
    }

    public String getCourseStatistics(int courseId, String courseName) {
        try {
            return attendanceService.getCourseStatistics(courseId, courseName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get course statistics", e);
        }
    }
}
