package org.example.DATA;

public class Attendance {
    private int studentId;
    private String status;

    public Attendance(int studentId, String status) {
        this.studentId = studentId;
        this.status = status;
    }

    public int getStudentId() { return studentId; }
    public String getStatus() { return status; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setStatus(String status) { this.status = status; }
}
