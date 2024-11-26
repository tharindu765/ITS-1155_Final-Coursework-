package org.example.orm_cw.bo.Custom.Impl;

import org.example.orm_cw.bo.Custom.CourseBo;
import org.example.orm_cw.dao.Custom.CourseDao;
import org.example.orm_cw.dao.DaoFactory;
import org.example.orm_cw.dao.DaoTypes;
import org.example.orm_cw.dto.CourseDto;
import org.example.orm_cw.entity.Course;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseBoImpl implements CourseBo {
    private CourseDao courseDAO = (CourseDao) DaoFactory.getDaoFactory().getDao(DaoTypes.CourseDao);

    @Override
    public List<CourseDto> loadAll() throws SQLException, ClassNotFoundException {
        List<Course> courseEntities = courseDAO.loadAll();
        List<CourseDto> courseDtos = new ArrayList<>();

        for (Course course : courseEntities) {
            courseDtos.add(new CourseDto(course.getCourseId(), course.getCourseName(), course.getDuration(), course.getFee()));
        }

        return courseDtos;
    }

    @Override
    public void add(CourseDto dto) throws SQLException, ClassNotFoundException {
        Course course = new Course(dto.getCourseId(), dto.getCourseName(), dto.getDuration(), dto.getFee());
        courseDAO.Save(course);
    }

    @Override
    public void update(CourseDto dto) throws SQLException, ClassNotFoundException {
        Course course = new Course(dto.getCourseId(), dto.getCourseName(), dto.getDuration(), dto.getFee());
        courseDAO.Update(course);
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        courseDAO.Delete(id);
    }
    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return  courseDAO.getCount();
    }

    @Override
    public CourseDto getCourseByName(String courseName) throws SQLException, ClassNotFoundException {
        return courseDAO.getCourseByName(courseName);
    }

}
