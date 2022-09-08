/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.ResourceBundle;

import com.learning.school.App;
import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.Log;
import com.learning.school.entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
                case "admin" -> {
                    App.stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learning/school/menu_admin.fxml")))));
                    App.stage.centerOnScreen();
                }
                case "student" -> {
                    App.stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learning/school/menu_student.fxml")))));
                    App.stage.centerOnScreen();
                }
                case "teacher" -> {
                    App.stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/learning/school/menu_teacher.fxml")))));
                    App.stage.centerOnScreen();
                }
                default -> labelAuthError.setText("The login and/or password is incorrect");
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
