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
public class AddScheduleController implements Initializable {
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
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
            var olTeacher = db.selectTeacher();
            var olSubject = db.selectSubject();
            cbTeacher.setItems(olTeacher);
            cbSubject.setItems(olSubject);
            cbDay.setItems(olDay);
            cbClass.setItems(olCl);
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
            db.addSchedule(new Schedule(null, LocalTime.parse(tfStart.getText()),
                    LocalTime.parse(tfEnd.getText()), cbDay.getValue(), cbSubject.getValue(),
                    cbTeacher.getValue(), cbClass.getValue()));
            db.insertLog(new Log(null,MenuAdminController.getUser(),"schedule added",LocalTime.now(),LocalDate.now()));
            Stage stage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
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
