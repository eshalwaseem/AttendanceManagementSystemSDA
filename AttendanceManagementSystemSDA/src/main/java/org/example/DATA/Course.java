package org.example.DATA;

public class Course {
    private int id;
    private String name;
    private int studentCount;

    public Course(int id, String name, int studentCount) {
        this.id = id;
        this.name = name;
        this.studentCount = studentCount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getStudentCount() { return studentCount; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setStudentCount(int studentCount) { this.studentCount = studentCount; }
}
