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
import java.util.stream.Collectors;

import com.learning.school.dao.TeacherDAO;
import com.learning.school.dao.connection.DBProvider;
import com.learning.school.entity.*;
import com.learning.school.entity.Class;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class EditScheduleController implements Initializable {
    
    private Schedule schedule;

    TeacherDAO teacherDAO;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        teacherDAO = new TeacherDAO();
        schedule = new Schedule(MenuAdminController.getSchedule());
        //<editor-fold defaultstate="collapsed" desc="setting cell factories">
        cbClass.setCellFactory((ListView<Class> param) -> {
            final ListCell<Class> cell = new ListCell<Class>() {
                
                @Override
                protected void updateItem(Class item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getNumber());
                    } else {
                        setText(null);
                    }
                }
            };
            return cell;
        });
        cbClass.setButtonCell(new ListCell<Class>() {
            
            @Override
            protected void updateItem(Class item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getNumber());
                } else {
                    setText(null);
                }
            }
        });
        cbDay.setCellFactory((ListView<Day> param) -> {
            final ListCell<Day> cell = new ListCell<Day>() {

                @Override
                protected void updateItem(Day item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        setText(item.getDay());
                    } else {
                        setText(null);
                    }
                }
            };
            return cell;
        });
        cbDay.setButtonCell(new ListCell<Day>() {

            @Override
            protected void updateItem(Day item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null) {
                    setText(item.getDay());
                } else {
                    setText(null);
                }
            }
        });
//</editor-fold>
        try {
            var olCl = db.selectClass();
            var olDay = db.selectDay();
            var olTeacher = teacherDAO.getAll();
            var olSubject = db.selectSubject();
            cbTeacher.setItems(olTeacher);
            cbTeacher.setValue(schedule.getTeacher());
            cbSubject.setItems(olSubject);
            cbSubject.setValue(schedule.getSubject());
            tfStart.setText(schedule.getTime_start().toString());
            tfEnd.setText(schedule.getTime_end().toString());
            cbDay.setItems(olDay);
            cbDay.getSelectionModel().select(olDay.indexOf(schedule.getDay()));
            cbClass.setItems(olCl);
            cbClass.getSelectionModel().select(olCl.indexOf(schedule.getCl()));
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
    private void btnAddClicked(MouseEvent e) {
        try {
            db.updateSchedule(new Schedule(schedule.getId(), LocalTime.parse(tfStart.getText()),
                    LocalTime.parse(tfEnd.getText()), cbDay.getValue(), cbSubject.getValue(),
                    cbTeacher.getValue(), cbClass.getValue()));
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
        db.insertLog(new Log(null,MenuAdminController.getUser(),"schedule changed",LocalTime.now(),LocalDate.now()));
    }

    //<editor-fold defaultstate="collapsed" desc="declaring variables">
    DBProvider db; 
    @FXML
    private Button btnAdd;
    
    @FXML
    private Button btnCancel;
    
    @FXML
    private ComboBox<Class> cbClass;
    
    @FXML
    private ComboBox<Day> cbDay;
    
    @FXML
    private TextField tfEnd;
    
    @FXML
    private TextField tfStart;
    
    @FXML
    private SearchableComboBox<Subject> cbSubject;

    @FXML
    private SearchableComboBox<Teacher> cbTeacher;
    @FXML
    private TextFlow tflClass, tflTeacher;
//</editor-fold>
}
