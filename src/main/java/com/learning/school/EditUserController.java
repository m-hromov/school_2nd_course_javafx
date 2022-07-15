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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditUserController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        user = MenuAdminController.getUser();
        db = new DBProvider();
        tfLogin.setText(user.getLogin());
        tfPassword.setText(user.getPassword());
    }

    @FXML
    void btnApplyClicked(MouseEvent event) {
        try {
            db.updateUser(new User(user.getId(),tfLogin.getText(),tfPassword.getText(),null,null,null));
            db.insertLog(new Log(null,MenuAdminController.getUser(),"user changed",LocalTime.now(),LocalDate.now()));
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
    private User user;
    DBProvider db;
    @FXML
    private TextField tfLogin;

    @FXML
    private TextField tfPassword;

}
