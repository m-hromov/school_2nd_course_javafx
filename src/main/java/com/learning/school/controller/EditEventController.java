/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;

import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.Class;
import com.learning.school.entity.Event;
import com.learning.school.entity.Log;
import com.learning.school.entity.Organizator;
import javafx.collections.ListChangeListener.Change;
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
import org.controlsfx.control.CheckComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditEventController implements Initializable {

    private ObservableList<Class> olClass;
    private ObservableList<Organizator> olOrg;
    private ObservableList<Class> olCheckClass;
    private ObservableList<Organizator> olCheckOrg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        ev = false;
        try {
            event = MenuAdminController.getEvent();
            tfDescription.setText(event.getDescription());
            dpDate.setValue(event.getDate());
            tfTime.setText(event.getTime().toString());
            olClass = db.selectClass();
            olOrg = db.selectOrganizator();
            olCheckClass = db.selectCheckClass(event.getId());
            olCheckOrg = db.selectCheckOrg(event.getId());
            if (olClass.isEmpty() || olOrg.isEmpty()) {
                return;
            }
            cbClass.getItems().setAll(olClass);
            cbOrganizator.getItems().setAll(olOrg);
            cbName.setItems(olOrg);
            for (var i : olCheckClass) {
                cbClass.getCheckModel().check(i);
            }
            for (var i : olCheckOrg) {
                cbOrganizator.getCheckModel().check(i);
            }
            cbClass.getCheckModel().getCheckedItems().addListener((Change<? extends Class> c) -> {
                changedClass(c);
            });
            cbOrganizator.getCheckModel().getCheckedItems().addListener((Change<? extends Organizator> c) -> {
                changedOrganizator(c);
            });
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        ev = true;
    }

    private void changedClass(Change<? extends Class> c) {
        ev = false;
        List<? extends Class> lClass;

        c.next();

        if (c.wasRemoved()) {
            try {

                lClass = c.getRemoved();

                db.deleteClassEvent(lClass.get(0).getNumber(), event.getId());
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                a.show();
            }
        }
        if (c.wasAdded()) {
            try {

                lClass = c.getAddedSubList();
                db.insertClassEvent(lClass.get(0).getNumber(), event.getId());

            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                a.show();
            }
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"event classes changed",LocalTime.now(),LocalDate.now()));
        ev = true;
    }

    private void changedOrganizator(Change<? extends Organizator> c) {
        ev = false;
        List<? extends Organizator> lClass;

        c.next();

        if (c.wasRemoved()) {
            try {

                lClass = c.getRemoved();

                db.deleteOrgEvent(lClass.get(0).getId(), event.getId());
            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                a.show();
            }
        }
        if (c.wasAdded()) {
            try {

                lClass = c.getAddedSubList();
                db.insertOrgEvent(lClass.get(0).getId(), event.getId());

            } catch (SQLException ex) {
                Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
                a.show();
            }
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"event organizators changed",LocalTime.now(),LocalDate.now()));
        ev = true;
    }

    @FXML
    void btnAddClicked(MouseEvent e) {
        ev = false;
        try {
            db.insertOrg(new Organizator(null, tfName.getText(), tfAddPhone.getText()));
            tfName.clear();
            tfAddPhone.clear();
            olOrg = db.selectOrganizator();
            olCheckOrg = db.selectCheckOrg(event.getId());
            cbOrganizator.getItems().setAll(olOrg);
            cbName.setItems(olOrg);
            for (var i : olCheckOrg) {
                cbOrganizator.getCheckModel().check(i);
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"organizator added",LocalTime.now(),LocalDate.now()));
        ev = true;
    }

    @FXML
    void btnApplyClicked(MouseEvent e) {
        try {
            db.updateEvent(new Event(event.getId(), tfDescription.getText(), LocalTime.parse(tfTime.getText()), dpDate.getValue()));
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"event changed",LocalTime.now(),LocalDate.now()));
    }

    @FXML
    void btnApplyOrg(MouseEvent e) {
        ev = false;
        try {
            db.updateOrg(new Organizator(org.getId(), cbName.getEditor().getText(), tfEditPhone.getText()));
            olOrg = db.selectOrganizator();
            olCheckOrg = db.selectCheckOrg(event.getId());
            cbOrganizator.getItems().setAll(olOrg);
            cbName.setItems(olOrg);
            for (var i : olCheckOrg) {
                cbOrganizator.getCheckModel().check(i);
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"organizator changed",LocalTime.now(),LocalDate.now()));
        ev = true;
    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    void btnDeleteClicked(MouseEvent e) {
        ev = false;
        try {
            db.delete("organizator", org.getId());
            olOrg = db.selectOrganizator();
            olCheckOrg = db.selectCheckOrg(event.getId());
            cbOrganizator.getItems().setAll(olOrg);
            cbName.getEditor().clear();
            tfEditPhone.clear();
            cbName.setItems(olOrg);
            for (var i : olCheckOrg) {
                cbOrganizator.getCheckModel().check(i);
            }
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"organizator deleted",LocalTime.now(),LocalDate.now()));
        ev = true;
    }

    @FXML
    void selectedName(ActionEvent event) {
        if (ev) {
            try{
            org = cbName.getSelectionModel().getSelectedItem();
            tfEditPhone.setText(cbName.getSelectionModel().getSelectedItem().getPhone());
            }catch(ClassCastException ex){}

        }

    }
    DBProvider db;
    private Event event;
    private Organizator org;
    private boolean ev;
    @FXML
    private CheckComboBox<Class> cbClass;

    @FXML
    private ComboBox<Organizator> cbName;

    @FXML
    private CheckComboBox<Organizator> cbOrganizator;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfAddPhone;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfEditPhone;

    @FXML
    private TextField tfName;

    @FXML
    private TextField tfTime;

}
