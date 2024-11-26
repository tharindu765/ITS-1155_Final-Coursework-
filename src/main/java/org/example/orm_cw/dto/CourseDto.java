package org.example.orm_cw.dto;

import java.util.Set;

public class CourseDto {

    private int courseId;
    private String courseName;
    private String duration;
    private double fee;
    private Set<StudentDto> students; // Many-to-Many relationship

    public CourseDto(int courseId, String courseName, String duration, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
    }

    public CourseDto(int courseId, String courseName, String duration, double fee, Set<StudentDto> students) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
        this.students = students;
    }

    // Getters and Setters
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public Set<StudentDto> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentDto> students) {
        this.students = students;
    }
}
