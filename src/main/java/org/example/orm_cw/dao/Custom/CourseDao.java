package org.example.orm_cw.dao.Custom;

import org.example.orm_cw.dao.CrudDao;
import org.example.orm_cw.dto.CourseDto;
import org.example.orm_cw.entity.Course;

import java.sql.SQLException;

public interface CourseDao extends CrudDao<Course> {
    int getCount() throws SQLException, ClassNotFoundException;
    CourseDto getCourseByName(String courseName) throws SQLException, ClassNotFoundException;
}
