package org.example.orm_cw.dao.Custom.Impl;

import org.example.orm_cw.dao.Custom.UserDao;
import org.example.orm_cw.entity.User;
import org.example.orm_cw.config.FactoryConfiguration;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    // Save a new user
    @Override
    public void Save(User user) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user); // Save the User entity to the database
        transaction.commit();
        session.close();
    }

    // Retrieve a user by username
    @Override
    public User getUserByUsername(String username) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Query<User> query = session.createQuery("FROM User WHERE username = :username", User.class);
        query.setParameter("username", username);
        User user = query.uniqueResult(); // Fetch a single user by username
        session.close();
        return user;
    }

    // Retrieve all users
    @Override
    public List<User> loadAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query<User> query = session.createQuery("FROM User", User.class);
        List<User> allUsers = query.list(); // Retrieve all users
        transaction.commit();
        session.close();
        return allUsers;
    }

    // Update an existing user
    @Override
    public void Update(User user) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(user); // Update the user entity in the database
        transaction.commit();
        session.close();
    }

    // Delete a user by ID
    @Override
    public void Delete(String userId) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("DELETE FROM User WHERE id = :userId");
        query.setParameter("userId", userId); // Delete user by ID
        query.executeUpdate();
        transaction.commit();
        session.close();
    }
}
