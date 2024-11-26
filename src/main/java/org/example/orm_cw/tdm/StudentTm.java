package org.example.orm_cw.tdm;

import java.util.List;

public class StudentTm {

    private int id;
    private String name;
    private String email;
    private String course; // Legacy field, but can still be used for single-course scenarios
    private String phone;
    private List<String> enrolledCourses; // To display multiple course names

    public StudentTm(int id, String name, String email, String course, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.phone = phone;
    }

    public StudentTm(int id, String name, String email, String course, String phone, List<String> enrolledCourses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.phone = phone;
        this.enrolledCourses = enrolledCourses;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
}
