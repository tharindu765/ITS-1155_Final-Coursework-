package org.example.orm_cw.dto;

import java.util.Set;

public class StudentDto {

    private int id;
    private String name;
    private String email;
    private String course;
    private String phone;
    private Set<CourseDto> courses; // Many-to-Many relationship

    public StudentDto(int id, String name, String email, String course, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.phone = phone;
    }

    public StudentDto(int id, String name, String email, String course, String phone, Set<CourseDto> courses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.course = course;
        this.phone = phone;
        this.courses = courses;
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

    public Set<CourseDto> getCourses() {
        return courses;
    }

    public void setCourses(Set<CourseDto> courses) {
        this.courses = courses;
    }
}
