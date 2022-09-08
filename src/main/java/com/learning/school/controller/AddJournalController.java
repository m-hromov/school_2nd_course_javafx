/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.Journal;
import com.learning.school.entity.Lesson;
import com.learning.school.entity.Schedule;
import com.learning.school.entity.Student;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class AddJournalController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = new DBProvider();
            olStudent = db.selectStudent();
            cbStudent.setItems(olStudent);
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void selected(ActionEvent event) {
        try {
            if(cbStudent.getValue() == null) return;
            olSubject = db.selectSchedule(cbStudent.getValue().getCl().getNumber());
            cbSubject.setItems(olSubject);
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    ObservableList<Schedule> olSubject;
    ObservableList<Student> olStudent;
    DBProvider db;
    @FXML
    private Button btnApply;

    @FXML
    private Button btnCancel;

    @FXML
    private SearchableComboBox<Student> cbStudent;

    @FXML
    private SearchableComboBox<Schedule> cbSubject;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextField tfGrade;

    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
            db.insertJournal(new Journal(null, cbStudent.getValue(), new Lesson(null, cbSubject.getValue(), dpDate.getValue()), Double.parseDouble(tfGrade.getText())));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    void btnCancelClicked(MouseEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
