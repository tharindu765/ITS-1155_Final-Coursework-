package org.example.orm_cw.bo;

import org.example.orm_cw.bo.Custom.Impl.CourseBoImpl;
import org.example.orm_cw.bo.Custom.Impl.StudentBoImpl;
import org.example.orm_cw.bo.Custom.Impl.UserBoImpl;

public class BoFactory {
    private static BoFactory boFactory;
    private BoFactory(){}
    public static BoFactory getBoFactory() {
        return boFactory == null ? boFactory = new BoFactory() : boFactory;
    }

    public SuperBo getBo(BoTypes boTypes){
        switch (boTypes){
            case CourseBo:
                return (SuperBo) new CourseBoImpl();
            case StudentBo:
                return (SuperBo) new StudentBoImpl();
            case UserBo:
                return (SuperBo) new UserBoImpl();
            default:
                return null;
        }
    }
}
