/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditStudentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        student = MenuAdminController.getStudent();
        try {
            var olClass = db.selectClass();
            cbClass.setItems(olClass);
            cbClass.setValue(student.getCl());
            cbAdd.setItems(olClass);
            tfName.setText(student.getName());
            tfPhone.setText(student.getPhone());
            tfPhoneParent.setText(student.getPhone_parent());

        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }

    }

    @FXML
    void btnAddClicked(MouseEvent event) throws SQLException {
        db.insertClass(new Class(cbAdd.getEditor().getText(), null));
        var olClass = db.selectClass();
        if (olClass.isEmpty()) {
            return;
        }
        cbAdd.setItems(olClass);
        cbClass.setItems(olClass);
        db.insertLog(new Log(null,MenuAdminController.getUser(),"class added",LocalTime.now(),LocalDate.now()));
    }

    @FXML
    void btnApplyClicked(MouseEvent event) {
        try {
            db.updateStudent(new Student(student.getId(), tfName.getText(), tfPhone.getText(), tfPhoneParent.getText(), cbClass.getValue()));
            db.insertLog(new Log(null,MenuAdminController.getUser(),"student changed",LocalTime.now(),LocalDate.now()));
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
    void btnDeleteClicked(MouseEvent event) {
        try {
            db.deleteClass(cbAdd.getEditor().getText());
            cbAdd.getEditor().clear();
            var olClass = db.selectClass();
            if (olClass.isEmpty()) {
                return;
            }
            cbAdd.setItems(olClass);
            cbClass.setItems(olClass);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"class deleted",LocalTime.now(),LocalDate.now()));
    }
    DBProvider db;
    private Student student;
    @FXML
    private Button btnApply;

    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfName, tfPhone, tfPhoneParent;

    @FXML
    private ComboBox<Class> cbAdd;
    @FXML
    private ComboBox<Class> cbClass;

}
