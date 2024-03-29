/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import com.learning.school.dao.TeacherDAO;
import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.Log;
import com.learning.school.entity.Student;
import com.learning.school.entity.Teacher;
import com.learning.school.entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class AddUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    TeacherDAO teacherDAO;
    DBProvider db;
    @FXML
    private Button btnApply;
    @FXML
    private Button btnCancel;
    @FXML
    private SearchableComboBox<Student> cbNameStudent;
    @FXML
    private SearchableComboBox<Teacher> cbNameTeacher;
    @FXML
    private ComboBox<String> cbPrivilege;
    @FXML
    private TextField tfLogin;
    @FXML
    private TextField tfPassword;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        teacherDAO = new TeacherDAO();
        cbPrivilege.setItems(FXCollections.observableArrayList("admin", "student", "teacher"));
        cbPrivilege.setValue("admin");
    }

    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
            switch (cbPrivilege.getValue()) {
                case "admin" ->
                        db.insertUser(new User(null, tfLogin.getText(), tfPassword.getText(), cbPrivilege.getValue(), null, null));
                case "student" ->
                        db.insertUser(new User(null, tfLogin.getText(), tfPassword.getText(), cbPrivilege.getValue(),
                                new Student(cbNameStudent.getValue().getId(), null, null, null, null), null));
                case "teacher" ->
                        db.insertUser(new User(null, tfLogin.getText(), tfPassword.getText(), cbPrivilege.getValue(), null,
                                new Teacher(cbNameTeacher.getValue().getId(), null, null)));
                default -> {
                }

            }
            db.insertLog(new Log(null, MenuAdminController.getUser(), "user added", LocalTime.now(), LocalDate.now()));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void selectedPrivilege(ActionEvent event) {
        switch (cbPrivilege.getValue()) {
            case "admin" -> {
                cbNameStudent.getItems().clear();
                cbNameTeacher.getItems().clear();
            }
            case "student" -> {
                try {
                    var ol = db.selectStudent();
                    cbNameStudent.getItems().setAll(ol);
                    cbNameTeacher.getItems().clear();
                } catch (SQLException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                    a.show();
                }
            }

            case "teacher" -> {
                ObservableList<Teacher> ol = teacherDAO.getAll();
                cbNameStudent.getItems().clear();
                cbNameTeacher.getItems().setAll(ol);
            }
        }
    }

}
