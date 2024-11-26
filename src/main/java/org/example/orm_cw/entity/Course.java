package org.example.orm_cw.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private int courseId;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Column(name = "fee", nullable = false)
    private double fee;

    @ManyToMany(mappedBy = "courses", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Student> students;

    // Default constructor
    public Course() {}

    // Parameterized constructor
    public Course(String courseName, String duration, double fee) {
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
    }

    public Course(int courseId, String courseName, String duration, double fee) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
        this.fee = fee;
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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", courseName='" + courseName + '\'' +
                ", duration='" + duration + '\'' +
                ", fee=" + fee +
                '}';
    }
}
