/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class AuthorizationController implements Initializable {

    private DBProvider db;
    private static User user;
    @FXML
    TextField tfLogin, tfPassword;
    @FXML
    Label labelAuthError;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private void btnSignIn(MouseEvent e) throws IOException {
        try {
            user = db.auth(tfLogin.getText(), tfPassword.getText()).get(0);
            switch (user.getPrivilege()) {
                case "admin":
                    App.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("menu_admin.fxml"))));
                    App.stage.centerOnScreen();
                    break;
                case "student":
                    App.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("menu_student.fxml"))));
                    App.stage.centerOnScreen();
                    break;
                case "teacher":
                    App.stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("menu_teacher.fxml"))));
                    App.stage.centerOnScreen();
                    break;
                default:
                    labelAuthError.setText("The login and/or password is incorrect");
                    break;
            }

            db.insertLog(new Log(null, user, "signed in", LocalTime.now(), LocalDate.now()));
        } catch (Exception ex) {
            labelAuthError.setText("login or password is incorrect");
        }

    }

    public static User getUser() {
        return user;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
    }

}
