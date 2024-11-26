package org.example.orm_cw.tdm;

import java.util.List;

public class CourseTm {

    private int courseId;
    private String courseName;
    private String duration;
    private double fee;
    private List<String> enrolledStudents; // To display multiple student names

    public CourseTm(int courseId, String courseName, String duration, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
    }

    public CourseTm(int courseId, String courseName, String duration, double fee, List<String> enrolledStudents) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
        this.enrolledStudents = enrolledStudents;
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

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
}
