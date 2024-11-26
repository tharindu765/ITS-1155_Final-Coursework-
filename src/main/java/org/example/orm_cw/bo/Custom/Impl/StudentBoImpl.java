package org.example.orm_cw.bo.Custom.Impl;

import org.example.orm_cw.bo.Custom.StudentBo;
import org.example.orm_cw.dao.Custom.StudentDao;
import org.example.orm_cw.dao.DaoFactory;
import org.example.orm_cw.dao.DaoTypes;
import org.example.orm_cw.dto.StudentDto;
import org.example.orm_cw.entity.Student;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBoImpl implements StudentBo {
    private StudentDao studentDAO = (StudentDao) DaoFactory.getDaoFactory().getDao(DaoTypes.StudentDao);

    @Override
    public List<StudentDto> loadAll() throws SQLException, ClassNotFoundException {
        List<Student> studentEntities = studentDAO.loadAll();
        List<StudentDto> studentDtos = new ArrayList<>();

        for (Student student : studentEntities) {
            studentDtos.add(new StudentDto(student.getId(), student.getName(), student.getEmail(), student.getCourse(), student.getPhone()));
        }

        return studentDtos;
    }

    @Override
    public void add(StudentDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student(dto.getName(), dto.getEmail(), dto.getCourse(), dto.getPhone());
        studentDAO.Save(student);
    }

    @Override
    public void update(StudentDto dto) throws SQLException, ClassNotFoundException {
        Student student = new Student(dto.getId(), dto.getName(), dto.getEmail(), dto.getCourse(), dto.getPhone());
        studentDAO.Update(student);
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
        studentDAO.Delete(id);
    }
    @Override
    public int getCount() throws SQLException, ClassNotFoundException {
        return  studentDAO.getCount();
    }

}
