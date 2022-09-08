/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.Class;
import com.learning.school.entity.Event;
import com.learning.school.entity.Log;
import com.learning.school.entity.Organizator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class AddEventController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        try {
            var olClass = db.selectClass();
            var olOrg = db.selectOrganizator();
            if (olClass.isEmpty() || olOrg.isEmpty()) {
                return;
            }
            cbClass.getItems().setAll(olClass);
            cbOrganizator.getItems().setAll(olOrg);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Something went wrong");
            a.show();
        }
    }  
    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
        db.insertEvent(new Event(null, tfDescription.getText(), LocalTime.parse(tfTime.getText()),dpDate.getValue()),
                cbOrganizator.getCheckModel().getCheckedItems(),cbClass.getCheckModel().getCheckedItems());
        db.insertLog(new Log(null,MenuAdminController.getUser(),"event added",LocalTime.now(),LocalDate.now()));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Incorrect data");
            a.show();
        }
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
    DBProvider db;
    @FXML
    private CheckComboBox<Class> cbClass;

    @FXML
    private CheckComboBox<Organizator> cbOrganizator;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfTime;

    
}
