package org.example.DATA;

public class Student {
    private int id;
    private String name;
    private String course;
    private String latestStatus;

    public Student(int id, String name, String course, String latestStatus) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.latestStatus = latestStatus;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCourse() { return course; }
    public String getLatestStatus() { return latestStatus; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setCourse(String course) { this.course = course; }
    public void setLatestStatus(String latestStatus) { this.latestStatus = latestStatus; }
}
