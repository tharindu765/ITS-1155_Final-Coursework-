package org.example.orm_cw.bo.Custom.Impl;

import org.example.orm_cw.bo.Custom.UserBo;
import org.example.orm_cw.dao.Custom.UserDao;
import org.example.orm_cw.dao.DaoFactory;
import org.example.orm_cw.dao.DaoTypes;
import org.example.orm_cw.dto.UserDto;
import org.example.orm_cw.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserBoImpl implements UserBo {

    // Initialize the User DAO
    private final UserDao userDao = (UserDao) DaoFactory.getDaoFactory().getDao(DaoTypes.UserDao);

    // Add a new user
    @Override
    public void add(UserDto userDto) throws SQLException, ClassNotFoundException {
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getRole());
        userDao.Save(user); // Delegate the save operation to the User DAO
    }

    // Retrieve a user by username
    @Override
    public UserDto getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        User user = userDao.getUserByUsername(username); // Retrieve the User entity from DAO
        if (user != null) {
            return new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
        }
        return null; // Return null if the user does not exist
    }

    // Retrieve all users
    @Override
    public List<UserDto> loadAll() throws SQLException, ClassNotFoundException {
        List<User> users = userDao.loadAll(); // Get all users from DAO
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            userDtos.add(new UserDto(user.getId(), user.getUsername(), user.getPassword(), user.getRole()));
        }
        return userDtos; // Return the list of UserDto
    }

    // Update an existing user
    @Override
    public void update(UserDto userDto) throws SQLException, ClassNotFoundException {
        User user = new User(userDto.getId(), userDto.getUsername(), userDto.getPassword(), userDto.getRole());
        userDao.Update(user); // Delegate the update operation to the User DAO
    }

    // Delete a user by ID
    @Override
    public void delete(String userId) throws SQLException, ClassNotFoundException {
        userDao.Delete(userId); // Delegate the delete operation to the User DAO
    }
}
