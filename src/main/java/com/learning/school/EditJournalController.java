/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditJournalController implements Initializable {

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
    ObservableList<Schedule> olSubject;
    ObservableList<Student> olStudent;
    ObservableList<Journal> olJournal;
    DBProvider db;

    @FXML
    private ComboBox<Journal> cbDate;

    @FXML
    private SearchableComboBox<Student> cbStudent;

    @FXML
    private SearchableComboBox<Schedule> cbSubject;

    @FXML
    private TextField tfGrade;

    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
            db.updateJournal(new Journal(cbDate.getValue().getId(),null,null,Double.parseDouble(tfGrade.getText())));
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

    @FXML
    void selected(ActionEvent event) {
        try {
            if(cbStudent.getValue() == null) return;
            olSubject = db.selectSchedule(cbStudent.getValue().getCl().getNumber());
            cbSubject.setItems(olSubject);
            olJournal = db.selectJournal(cbStudent.getValue().getId());
            cbDate.setItems(olJournal);
            
        } catch (SQLException ex) {
            Logger.getLogger(AddJournalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
