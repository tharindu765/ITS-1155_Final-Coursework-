package org.example.orm_cw.bo.Custom;

import org.example.orm_cw.bo.SuperBo;
import org.example.orm_cw.dto.CourseDto;

import java.sql.SQLException;
import java.util.List;

public interface CourseBo extends SuperBo {
    List<CourseDto> loadAll() throws SQLException, ClassNotFoundException;
    void add(CourseDto d) throws SQLException, ClassNotFoundException;
    void update(CourseDto course) throws SQLException, ClassNotFoundException;
    void delete(String id) throws SQLException, ClassNotFoundException;
    int getCount() throws SQLException, ClassNotFoundException;
    CourseDto getCourseByName(String courseName) throws SQLException, ClassNotFoundException;
}
