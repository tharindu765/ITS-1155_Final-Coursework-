package org.example.orm_cw.dao.Custom.Impl;

import org.example.orm_cw.dao.Custom.StudentDao;
import org.example.orm_cw.entity.Student;
import org.example.orm_cw.entity.Course;
import org.example.orm_cw.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class StudentDaoImpl implements StudentDao {
    @Override
    public List<Student> loadAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Create a query to load all Student entities
        Query<Student> query = session.createQuery("FROM Student", Student.class);
        List<Student> allStudents = query.list();

        transaction.commit();
        session.close();

        return allStudents;
    }

    @Override
    public void Save(Student student) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Save the student entity, including the associated courses
        session.save(student);

        transaction.commit();
        session.close();
    }

    @Override
    public void Update(Student student) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Update student entity
        Query updateStudentQuery = session.createQuery("UPDATE Student SET name = :name, email = :email, phone = :phone WHERE id = :id");
        updateStudentQuery.setParameter("name", student.getName());
        updateStudentQuery.setParameter("email", student.getEmail());
        updateStudentQuery.setParameter("phone", student.getPhone());
        updateStudentQuery.setParameter("id", student.getId());

        int studentUpdateResult = updateStudentQuery.executeUpdate();

        if (studentUpdateResult > 0) {
            System.out.println("Student updated successfully!");
        } else {
            System.out.println("Student not found!");
        }

        transaction.commit();
        session.close();
    }

    @Override
    public void Delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        // Delete student entity using field name
        Query deleteStudentQuery = session.createQuery("DELETE FROM Student WHERE id = :id");
        deleteStudentQuery.setParameter("id", id);
        int studentDeleteResult = deleteStudentQuery.executeUpdate();

        if (studentDeleteResult > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }

        transaction.commit();
        session.close();
    }
    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<Long> query = session.createQuery("SELECT COUNT(s) FROM Student s", Long.class);
        Long count = query.uniqueResult();
        session.close();
        return count.intValue();
    }

}
