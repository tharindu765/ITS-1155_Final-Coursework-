package org.example.orm_cw.bo.Custom;

import org.example.orm_cw.bo.SuperBo;
import org.example.orm_cw.dto.StudentDto;

import java.sql.SQLException;
import java.util.List;

public interface StudentBo extends SuperBo {
    List<StudentDto> loadAll() throws SQLException, ClassNotFoundException;
    void add(StudentDto d) throws SQLException, ClassNotFoundException;
    void update(StudentDto student) throws SQLException, ClassNotFoundException;
    void delete(String id) throws SQLException, ClassNotFoundException;
    int getCount() throws SQLException, ClassNotFoundException;

}
