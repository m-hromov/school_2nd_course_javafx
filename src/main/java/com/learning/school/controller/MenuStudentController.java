/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.learning.school.controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.*;
import com.learning.school.entity.Class;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class MenuStudentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        user = AuthorizationController.getUser();
        lUser.setText(user.getPrivilege().toUpperCase() + ": " + user.getStudent().getName());
        try {
            initSchedule();
            initJournal();

        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    private void initSchedule() throws SQLException {
        var olSchedule = db.selectSchedule(user.getStudent().getCl().getNumber());
        scheduleClass.setCellValueFactory(new PropertyValueFactory<>("cl"));
        scheduleDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        scheduleEnd.setCellValueFactory(new PropertyValueFactory<>("time_end"));
        scheduleStart.setCellValueFactory(new PropertyValueFactory<>("time_start"));
        scheduleSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        scheduleSubject.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getSubject().getDescription()+" "+cellData.getValue().getSubject().getNumber_audience()));
        scheduleTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        FilteredList<Schedule> filteredData = new FilteredList<>(olSchedule);
        tbSchedule.setItems(filteredData);
        tfSchedule.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateSchedule(newValue))
        );
    }
    private void initJournal() throws SQLException {
        var olJournal = db.selectJournal(user.getStudent().getId());
        jDate.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getLesson().getDate().toString()));
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
                || (schedule.getTeacher().getName().toLowerCase().contains(searchText.toLowerCase()))
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
    private TabPane mainTabPane;

    @FXML
    private TableColumn<Schedule, Class> scheduleClass;

    @FXML
    private TableColumn<Schedule, Day> scheduleDay;

    @FXML
    private TableColumn<Schedule, LocalTime> scheduleEnd;

    @FXML
    private TableColumn<Schedule, LocalTime> scheduleStart;

    @FXML
    private TableColumn<Schedule, String> scheduleSubject;

    @FXML
    private TableColumn<Schedule, Teacher> scheduleTeacher;

    @FXML
    private TableView<Schedule> tbSchedule;

    @FXML
    private TableView<Journal> tbJournal;

    @FXML
    private TableColumn<Journal, String> jSubject;

    @FXML
    private TableColumn<Journal, String> jDate;
    @FXML
    private TableColumn<Journal, Double> jGrade;

    @FXML
    private TextField tfSchedule;

    @FXML
    private TextField tfJournal;
    @FXML
    private Label lUser;
}
