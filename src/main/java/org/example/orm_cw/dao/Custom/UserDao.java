package org.example.orm_cw.dao.Custom;

import org.example.orm_cw.dao.CrudDao;
import org.example.orm_cw.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao extends CrudDao<User> {

    // Retrieve a user by username
    User getUserByUsername(String username) throws SQLException, ClassNotFoundException;

}
