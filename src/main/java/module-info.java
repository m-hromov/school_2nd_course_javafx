module com.learning.school {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.base;
    requires org.controlsfx.controls;
    requires kernel;
    requires layout;
    opens com.learning.school to javafx.fxml;
    exports com.learning.school;
}
