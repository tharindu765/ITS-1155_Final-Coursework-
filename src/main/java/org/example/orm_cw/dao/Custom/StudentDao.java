package org.example.orm_cw.dao.Custom;

import org.example.orm_cw.dao.CrudDao;
import org.example.orm_cw.entity.Student;

import java.sql.SQLException;

public interface StudentDao extends CrudDao<Student> {
    int getCount() throws SQLException, ClassNotFoundException;
}
