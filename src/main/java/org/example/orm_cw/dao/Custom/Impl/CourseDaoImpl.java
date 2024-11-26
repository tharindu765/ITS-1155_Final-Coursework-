package org.example.orm_cw.dao.Custom.Impl;

import org.example.orm_cw.dao.Custom.CourseDao;
import org.example.orm_cw.dto.CourseDto;
import org.example.orm_cw.entity.Course;
import org.example.orm_cw.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class CourseDaoImpl implements CourseDao {
    @Override
    public List<Course> loadAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Load all Course entities
        Query<Course> query = session.createQuery("FROM Course", Course.class);
        List<Course> allCourses = query.list();

        transaction.commit();
        session.close();

        return allCourses;
    }

    @Override
    public void Save(Course course) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Save the course entity, including the associated students
        session.save(course);

        transaction.commit();
        session.close();
    }

    @Override
    public void Update(Course course) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Update course entity
        Query updateCourseQuery = session.createQuery("UPDATE Course SET courseName = :name, duration = :duration, fee = :fee WHERE courseId = :id");
        updateCourseQuery.setParameter("name", course.getCourseName());
        updateCourseQuery.setParameter("duration", course.getDuration());
        updateCourseQuery.setParameter("fee", course.getFee());
        updateCourseQuery.setParameter("id", course.getCourseId());

        int courseUpdateResult = updateCourseQuery.executeUpdate();

        if (courseUpdateResult > 0) {
            System.out.println("Course updated successfully!");
        } else {
            System.out.println("Course not found!");
        }

        transaction.commit();
        session.close();
    }

    @Override
    public void Delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Delete course entity using field name
        Query deleteCourseQuery = session.createQuery("DELETE FROM Course WHERE courseId = :id");
        deleteCourseQuery.setParameter("id", Integer.parseInt(id)); // Ensure id is parsed to integer if it's passed as String

        int courseDeleteResult = deleteCourseQuery.executeUpdate();

        if (courseDeleteResult > 0) {
            System.out.println("Course deleted successfully!");
        } else {
            System.out.println("Course not found!");
        }

        transaction.commit();
        session.close();
    }

    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(c) FROM Course c", Long.class);
        Long count = query.uniqueResult();
        session.close();
        return count.intValue();
    }

    @Override
    public CourseDto getCourseByName(String courseName) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Query<Course> query = session.createQuery("FROM Course WHERE courseName = :courseName", Course.class);
        query.setParameter("courseName", courseName);

        Course course = query.uniqueResult();

        transaction.commit();
        session.close();

        if (course != null) {
            return new CourseDto(course.getCourseId(), course.getCourseName(), course.getDuration(), course.getFee());
        }

        return null;
    }



}
