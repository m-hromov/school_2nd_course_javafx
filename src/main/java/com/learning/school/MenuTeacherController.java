/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class MenuTeacherController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        user = AuthorizationController.getUser();
        lUser.setText(user.getPrivilege().toUpperCase() + ": " + user.getTeacher().getName());
        try {
            initSchedule();
            initJournal();

        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }    
    private void initSchedule() throws SQLException {
        var olSchedule = db.selectSchedule(user.getTeacher().getId());
        scheduleClass.setCellValueFactory(new PropertyValueFactory<>("cl"));
        scheduleDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        scheduleEnd.setCellValueFactory(new PropertyValueFactory<>("time_end"));
        scheduleStart.setCellValueFactory(new PropertyValueFactory<>("time_start"));
        scheduleSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        FilteredList<Schedule> filteredData = new FilteredList<>(olSchedule);
        tbSchedule.setItems(filteredData);
        tfSchedule.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateSchedule(newValue))
        );
    }
    private void initJournal() throws SQLException {
        var olJournal = db.selectJournalT(user.getTeacher().getId());
        jDate.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getLesson().getDate().toString()));
        jStudent.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getStudent().getName()));
        jGrade.setCellValueFactory(new PropertyValueFactory<>("grade"));
        jSubject.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getLesson().getSchedule().getSubject().getDescription()));
        FilteredList<Journal> filteredData = new FilteredList<>(olJournal);
        tbJournal.setItems(filteredData);
        tfJournal.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateJournal(newValue))
        );
    }
    private boolean searchFindsSchedule(Schedule schedule, String searchText) {
        return (schedule.getCl().getNumber().toLowerCase().contains(searchText.toLowerCase()))
                || (schedule.getDay().getDay().toLowerCase().contains(searchText.toLowerCase()))
                || (schedule.getSubject().getDescription().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsJournal(Journal journal, String searchText) {
        return (journal.getLesson().getDate().toString().toLowerCase().contains(searchText.toLowerCase()))
                || (journal.getLesson().getSchedule().getSubject().getDescription().toLowerCase().contains(searchText.toLowerCase()));
    }
    private Predicate<Schedule> createPredicateSchedule(String searchText) {
        return schedule -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsSchedule(schedule, searchText);
        };
    }

    private Predicate<Journal> createPredicateJournal(String searchText) {
        return journal -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsJournal(journal, searchText);
        };
    }
    DBProvider db;    
    User user;
    @FXML
    private Label lUser;
    
    @FXML
    private TableColumn<Journal, String> jDate;

    @FXML
    private TableColumn<Journal, Double> jGrade;

    @FXML
    private TableColumn<Journal, String> jStudent;

    @FXML
    private TableColumn<Journal, String> jSubject;

    @FXML
    private TableColumn<Schedule, Class> scheduleClass;

    @FXML
    private TableColumn<Schedule, Day> scheduleDay;

    @FXML
    private TableColumn<Schedule, LocalTime> scheduleEnd;

    @FXML
    private TableColumn<Schedule, LocalTime> scheduleStart;

    @FXML
    private TableColumn<Schedule, Subject> scheduleSubject;

    @FXML
    private TableView<Journal> tbJournal;

    @FXML
    private TableView<Schedule> tbSchedule;

    @FXML
    private TextField tfJournal;

    @FXML
    private TextField tfSchedule;

    @FXML
    void btnAddClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("add_journal.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void btnChangeClicked(MouseEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("edit_journal.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void btnRefreshClicked(MouseEvent event) {
        
        try {
            initJournal();
        } catch (SQLException ex) {
            Logger.getLogger(MenuTeacherController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
