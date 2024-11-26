package org.example.orm_cw.bo.Custom;

import org.example.orm_cw.bo.SuperBo;
import org.example.orm_cw.dto.UserDto;

import java.sql.SQLException;
import java.util.List;

public interface UserBo extends SuperBo {
    // Save a new user
    void add(UserDto userDto) throws SQLException, ClassNotFoundException;

    // Retrieve a user by username
    UserDto getUserByUsername(String username) throws SQLException, ClassNotFoundException;

    // Retrieve all users
    List<UserDto> loadAll() throws SQLException, ClassNotFoundException;

    // Update an existing user
    void update(UserDto userDto) throws SQLException, ClassNotFoundException;

    // Delete a user by ID
    void delete(String userId) throws SQLException, ClassNotFoundException;
}
