module org.example.orm_cw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.naming;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires jbcrypt;

    opens org.example.orm_cw to javafx.fxml;
    opens org.example.orm_cw.entity to org.hibernate.orm.core;
    opens org.example.orm_cw.Controller to javafx.fxml;
    opens org.example.orm_cw.tdm to javafx.base;

    exports org.example.orm_cw;
    exports org.example.orm_cw.entity;

}