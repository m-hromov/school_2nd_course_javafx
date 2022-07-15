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
public class AddStudentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        try {
            var olClass = db.selectClass();
            if (olClass.isEmpty()) {
                return;
            }
            cbClass.setItems(olClass);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    } 
    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
            db.insertStudent(new Student(null,tfName.getText(),tfPhone.getText(),tfPhoneParent.getText(),cbClass.getValue()));
            db.insertLog(new Log(null,MenuAdminController.getUser(),"student added",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    DBProvider db;
     @FXML
    private Button btnApply;

    @FXML
    private Button btnCancel;

    @FXML
    private ComboBox<Class> cbClass;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfPhoneParent;

    
}
