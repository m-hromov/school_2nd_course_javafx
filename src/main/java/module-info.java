module com.learning.school {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    requires kernel;
    requires layout;
    opens com.learning.school to javafx.fxml;
    exports com.learning.school.controller;
    opens com.learning.school.controller to javafx.fxml;
    exports com.learning.school.entity;
    opens com.learning.school.entity to javafx.fxml;
    exports com.learning.school.utils;
    opens com.learning.school.utils to javafx.fxml;
    exports com.learning.school.dao;
    opens com.learning.school.dao to javafx.fxml;
    exports com.learning.school.dao.mapper;
    exports com.learning.school.dao.connection;
    opens com.learning.school.dao.connection to javafx.fxml;
    exports com.learning.school;
}
