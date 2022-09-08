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
import com.learning.school.entity.*;
import com.learning.school.entity.Class;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditTeacherController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        teacher = new Teacher(MenuAdminController.getTeacher());
        event = false;
        try {
            olAttestation = db.selectAttestation(teacher.getId());
            tfName.setText(teacher.getName());
            tfPhone.setText(teacher.getPhone());
            cbDegree2.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
            if (olAttestation.isEmpty()) {
                return;
            }
            var olCl = db.selectClass();
            cbDegree1.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
            cbDegree1.setValue(olAttestation.get(0).getCategory());
            cbSpeciality.setItems(olAttestation);
            cbSpeciality.setValue(olAttestation.get(0));
            dpPrev.setValue(olAttestation.get(0).getDatePassed());
            dpNext.setValue(olAttestation.get(0).getDateNext());
            if (olCl.isEmpty()) {
                return;
            }
            cbClass.setItems(olCl);
            cbClass.getSelectionModel().select(olCl.indexOf(db.selectClassTeacher(teacher.getId())));
            event = true;
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    private void btnCancelClicked(MouseEvent e) {
        Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void selectedSpec(ActionEvent e) {
        if (event) {
            for (var j : olAttestation) {
                if (j.getSpeciality().equals(cbSpeciality.getValue().getSpeciality())) {
                    cbDegree1.setValue(j.getCategory());
                    dpPrev.setValue(j.getDatePassed());
                    dpNext.setValue(j.getDateNext());
                }
            }
        }

    }

    @FXML
    private void btnApplyAttest(MouseEvent e) {
        event = false;
        for (var j : olAttestation) {
            if (j.getSpeciality().equals(cbSpeciality.getValue().getSpeciality())) {
                try {
                    j.setCategory(cbDegree1.getValue());
                    db.updateAttestation(j);
                    olAttestation = db.selectAttestation(teacher.getId());
                    cbDegree1.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
                    cbDegree1.setValue(olAttestation.get(0).getCategory());
                    cbSpeciality.setItems(olAttestation);
                    cbSpeciality.setValue(olAttestation.get(0));
                    dpPrev.setValue(olAttestation.get(0).getDatePassed());
                    dpNext.setValue(olAttestation.get(0).getDateNext());
                } catch (SQLException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                    a.show();
                }
            }
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"attestation changed",LocalTime.now(),LocalDate.now()));
        event = true;

    }

    @FXML
    private void btnDeleteClicked(MouseEvent e) {
        event = false;
        for (var j : olAttestation) {
            if (j.getSpeciality().equals(cbSpeciality.getValue().getSpeciality())) {
                try {
                    db.delete("attestation", j.getId());
                    olAttestation = db.selectAttestation(teacher.getId());
                    if (olAttestation.isEmpty()) {
                        return;
                    }
                    cbDegree1.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
                    cbDegree1.setValue(olAttestation.get(0).getCategory());
                    cbSpeciality.setItems(olAttestation);
                    cbSpeciality.setValue(olAttestation.get(0));
                    dpPrev.setValue(olAttestation.get(0).getDatePassed());
                    dpNext.setValue(olAttestation.get(0).getDateNext());
                } catch (SQLException ex) {
                    Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                    a.show();
                }
            }
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"attestation deleted",LocalTime.now(),LocalDate.now()));
        event = true;
    }

    @FXML
    void btnAddClicked(MouseEvent e) {
        event = false;
        try {
            db.insertAttestation(new Attestation(null, dpPrevAdd.getValue(),
                    dpNextAdd.getValue(), new Speciality(null, tfSpeciality.getText()), cbDegree2.getValue(), new Teacher(teacher)));
            olAttestation = db.selectAttestation(teacher.getId());
            cbDegree1.setItems(FXCollections.observableArrayList(1, 2, 3, 4));
            cbDegree1.setValue(olAttestation.get(0).getCategory());
            cbSpeciality.setItems(olAttestation);
            cbSpeciality.setValue(olAttestation.get(0));

            dpPrev.setValue(olAttestation.get(0).getDatePassed());
            dpNext.setValue(olAttestation.get(0).getDateNext());
            tfSpeciality.clear();
            cbDegree2.setValue(null);
            dpNextAdd.setValue(null);
            dpPrevAdd.setValue(null);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        } finally {
            db.insertLog(new Log(null,MenuAdminController.getUser(),"attestation added",LocalTime.now(),LocalDate.now()));
            event = true;
        }
    }

    @FXML
    private void btnApplyClicked(MouseEvent e) {
        try {
            db.updateTeacher(new Teacher(teacher.getId(), tfName.getText(), tfPhone.getText()), cbClass.getValue());
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"teacher changed",LocalTime.now(),LocalDate.now()));
    }

    DBProvider db;
    ObservableList<Attestation> olAttestation;
    boolean event;
    private Teacher teacher;
    @FXML
    private ComboBox<Integer> cbDegree1;

    @FXML
    private ComboBox<Integer> cbDegree2;

    @FXML
    private ComboBox<Attestation> cbSpeciality;

    @FXML
    private DatePicker dpNext;

    @FXML
    private DatePicker dpNextAdd;

    @FXML
    private DatePicker dpPrev;

    @FXML
    private DatePicker dpPrevAdd;

    @FXML
    private ComboBox<Class> cbClass;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfPhone;

    @FXML
    private TextField tfSpeciality;

}
