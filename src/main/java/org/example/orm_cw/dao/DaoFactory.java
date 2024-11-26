package org.example.orm_cw.dao;

import org.example.orm_cw.dao.Custom.Impl.CourseDaoImpl;
import org.example.orm_cw.dao.Custom.Impl.StudentDaoImpl;
import org.example.orm_cw.dao.Custom.Impl.UserDaoImpl;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {

    }

    public static DaoFactory getDaoFactory() {
        return daoFactory == null ? daoFactory = new DaoFactory() : daoFactory;
    }

    public SuperDao getDao(DaoTypes daoTypes) {
        switch (daoTypes) {
            case StudentDao:
                return new StudentDaoImpl();
            case CourseDao:
                return new CourseDaoImpl();
            case UserDao:
                return new UserDaoImpl();
               default:
                return null;
        }
    }
}
