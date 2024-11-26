package org.example.orm_cw.dao;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> extends SuperDao{
    List<T> loadAll() throws SQLException, ClassNotFoundException;
    void Save(T TM) throws SQLException, ClassNotFoundException;
    void Update(T TM) throws SQLException, ClassNotFoundException;
    void Delete(String id) throws SQLException, ClassNotFoundException;
}
