package com.learning.school;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.SearchableComboBox;

/**
 * FXML Controller class
 *
 * @author hromov
 */
public class MenuAdminController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DBProvider();
        user = AuthorizationController.getUser();
        lUser.setText(user.getPrivilege().toUpperCase() + ": " + user.getLogin());
        try {
            
            initEvent();
            initLog();
            initSchedule();
            initStudent();
            initTeacher();
            initUser();
            initQueries();

        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }

    }

    private void initSchedule() throws SQLException {
        olSchedule = db.selectSchedule();
        scheduleClass.setCellValueFactory(new PropertyValueFactory<>("cl"));
        scheduleDay.setCellValueFactory(new PropertyValueFactory<>("day"));
        scheduleEnd.setCellValueFactory(new PropertyValueFactory<>("time_end"));
        scheduleStart.setCellValueFactory(new PropertyValueFactory<>("time_start"));
        scheduleSubject.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getSubject().getDescription()+" "+cellData.getValue().getSubject().getNumber_audience()));
        scheduleTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
        FilteredList<Schedule> filteredData = new FilteredList<>(olSchedule);
        tbSchedule.setItems(filteredData);
        tfSchedule.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateSchedule(newValue))
        );
    }

    private void initTeacher() throws SQLException {
        olTeacher = db.selectTeacher();
        teacherName.setCellValueFactory(new PropertyValueFactory<>("name"));
        teacherPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        FilteredList<Teacher> filteredData = new FilteredList<>(olTeacher);
        tbTeacher.setItems(filteredData);
        tfTeacher.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateTeacher(newValue))
        );
    }

    private void initStudent() throws SQLException {
        olStudent = db.selectStudent();
        studentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        studentPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        studentParentPhone.setCellValueFactory(new PropertyValueFactory<>("phone_parent"));
        studentClass.setCellValueFactory(new PropertyValueFactory<>("cl"));
        FilteredList<Student> filteredData = new FilteredList<>(olStudent);
        tbStudent.setItems(filteredData);
        tfStudent.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateStudent(newValue))
        );
    }

    private void initEvent() throws SQLException {
        olEvent = db.selectEvent();
        eventDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        eventTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        eventDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        FilteredList<Event> filteredData = new FilteredList<>(olEvent);
        tbEvent.setItems(filteredData);
        tfEvent.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateEvent(newValue))
        );
    }

    private void initLog() throws SQLException {
        olLog = db.selectLog();
        logUser.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getUser().getLogin()));
        logPrivilege.setCellValueFactory(cellData
                -> new SimpleStringProperty(cellData.getValue().getUser().getPrivilege()));
        logAction.setCellValueFactory(new PropertyValueFactory<>("action"));
        logTime.setCellValueFactory(new PropertyValueFactory<>("time"));
        logDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        FilteredList<Log> filteredData = new FilteredList<>(olLog);
        tbLog.setItems(filteredData);
        tfLog.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateLog(newValue))
        );
    }

    private void initUser() throws SQLException {
        olUser = db.selectUser();
        userName.setCellValueFactory(cellData -> {
            if (cellData.getValue().getPrivilege().equals("student")) {
                return new SimpleStringProperty(cellData.getValue().getStudent().getName());
            }
            if (cellData.getValue().getPrivilege().equals("teacher")) {
                return new SimpleStringProperty(cellData.getValue().getTeacher().getName());
            }
            if (cellData.getValue().getPrivilege().equals("admin")) {
                return new SimpleStringProperty("");
            }
            return null;
        });
        userLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        userPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        userPrivilege.setCellValueFactory(new PropertyValueFactory<>("privilege"));
        FilteredList<User> filteredData = new FilteredList<>(olUser);
        tbUser.setItems(filteredData);
        tfUser.textProperty().addListener((observable, oldValue, newValue)
                -> filteredData.setPredicate(createPredicateUser(newValue))
        );
    }

    private boolean searchFindsSchedule(Schedule schedule, String searchText) {
        return (schedule.getCl().getNumber().toLowerCase().contains(searchText.toLowerCase()))
                || (schedule.getTeacher().getName().toLowerCase().contains(searchText.toLowerCase()))
                || (schedule.getDay().getDay().toLowerCase().contains(searchText.toLowerCase()))
                || (schedule.getSubject().getDescription().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsTeacher(Teacher teacher, String searchText) {
        return (teacher.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (teacher.getPhone().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsStudent(Student student, String searchText) {
        if(student.getPhone() == null){
        return (student.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getPhone_parent().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getCl().getNumber().toLowerCase().contains(searchText.toLowerCase()));
        }
        if(student.getPhone_parent() == null){
        return (student.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getPhone().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getCl().getNumber().toLowerCase().contains(searchText.toLowerCase()));
        }
        return (student.getName().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getPhone().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getPhone_parent().toLowerCase().contains(searchText.toLowerCase()))
                || (student.getCl().getNumber().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsEvent(Event event, String searchText) {
        return (event.getDescription().toLowerCase().contains(searchText.toLowerCase()))
                || (event.getDate().toString().toLowerCase().contains(searchText.toLowerCase()))
                || (event.getTime().toString().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsLog(Log log, String searchText) {
        return (log.getUser().getLogin().toLowerCase().contains(searchText.toLowerCase()))
                || (log.getUser().getPrivilege().toLowerCase().contains(searchText.toLowerCase()))
                || (log.getDate().toString().toLowerCase().contains(searchText.toLowerCase()))
                || (log.getTime().toString().toLowerCase().contains(searchText.toLowerCase()))
                || (log.getAction().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean searchFindsUser(User user, String searchText) {
        return (user.getLogin().toLowerCase().contains(searchText.toLowerCase()))
                || (user.getPrivilege().toLowerCase().contains(searchText.toLowerCase()))
                || (user.getTeacher().getName().toLowerCase().contains(searchText.toLowerCase()));
    }

    private Predicate<Schedule> createPredicateSchedule(String searchText) {
        return schedule -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsSchedule(schedule, searchText);
        };
    }

    private Predicate<Teacher> createPredicateTeacher(String searchText) {
        return teacher -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsTeacher(teacher, searchText);
        };
    }

    private Predicate<Student> createPredicateStudent(String searchText) {
        return student -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsStudent(student, searchText);
        };
    }

    private Predicate<Event> createPredicateEvent(String searchText) {
        return event -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsEvent(event, searchText);
        };
    }

    private Predicate<Log> createPredicateLog(String searchText) {
        return log -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsLog(log, searchText);
        };
    }

    private Predicate<User> createPredicateUser(String searchText) {
        return useR -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return searchFindsUser(useR, searchText);
        };
    }

    @FXML
    private void btnDeleteClicked(MouseEvent e) {
        try {
            switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
                case "Schedule" -> {
                    var ol = tbSchedule.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("schedule", i.getId());
                    }
                    db.insertLog(new Log(null,user,"schedule deleted",LocalTime.now(),LocalDate.now()));
                }
                case "Teachers" -> {
                    var ol = tbTeacher.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("teacher", i.getId());
                    }
                    db.insertLog(new Log(null,user,"teacher deleted",LocalTime.now(),LocalDate.now()));
                }
                case "Students" -> {
                    var ol = tbStudent.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("student", i.getId());
                        db.insertLog(new Log(null,user,"student deleted",LocalTime.now(),LocalDate.now()));
                    }
                }
                case "Events" -> {
                    var ol = tbEvent.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("event", i.getId());
                        db.insertLog(new Log(null,user,"event deleted",LocalTime.now(),LocalDate.now()));
                    }
                }
                case "Users" -> {
                    var ol = tbUser.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("user", i.getId());
                    }
                    db.insertLog(new Log(null,user,"user deleted",LocalTime.now(),LocalDate.now()));
                }
                case "Logs" -> {
                    var ol = tbLog.getSelectionModel().getSelectedItems();
                    for (var i : ol) {
                        db.delete("log", i.getId());
                    }
                    db.insertLog(new Log(null,user,"log deleted",LocalTime.now(),LocalDate.now()));
                }

            }
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void btnRefreshClicked(MouseEvent e) {
        try {
            switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
                case "Schedule" -> {
                    initSchedule();
                }
                case "Teachers" -> {
                    initTeacher();
                }
                case "Students" -> {
                    initStudent();
                }
                case "Events" -> {
                    initEvent();
                }
                case "Users" -> {
                    initUser();
                }
                case "Logs" -> {
                    initLog();
                }

            }
        } catch (SQLException ex) {
        }
    }

    @FXML
    private void btnCopyClicked(MouseEvent e) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Schedule" -> {
                var ol = tbSchedule.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    content.putString(i.toString() + "\n");
                }
                clipboard.setContent(content);
            }
            case "Teachers" -> {
                var ol = tbTeacher.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    content.putString(i.getName() + " " + i.getPhone());
                }
                clipboard.setContent(content);
            }
            case "Students" -> {
                var ol = tbStudent.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    content.putString(i.getName() + " " + i.getPhone()+ " " + i.getPhone_parent()+ " " + i.getCl().getNumber());
                }
                clipboard.setContent(content);
            }
            case "Events" -> {
                var ol = tbEvent.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    content.putString(i.getDescription() + " "+ i.getTime() + " "+ i.getDate());
                }
                clipboard.setContent(content);
            }
            case "Users" -> {
                var ol = tbUser.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    if (i.getTeacher()!=null) {
                        content.putString(i.getLogin()  + " " + i.getPrivilege() + " " + i.getTeacher().getName() + " " + i.getPassword());
                    }
                    if (i.getStudent()!=null) {
                    content.putString(i.getLogin()  + " " + i.getPrivilege() + " " + i.getStudent().getName()+ " " + i.getPassword());
                    }
                    if (i.getStudent()==null && i.getTeacher()==null){
                        content.putString(i.getLogin()  + " " + i.getPrivilege()+ " " + i.getPassword());
                    }
                }
                clipboard.setContent(content);
            }
            case "Logs" -> {
                var ol = tbLog.getSelectionModel().getSelectedItems();
                for (var i : ol) {
                    content.putString(i.getUser().getLogin() + " " + i.getUser().getPrivilege() + " " + i.getAction() + " " + i.getDate().toString() + " " + i.getTime().toString());
                }
                clipboard.setContent(content);
            }

        }
    }

    @FXML
    private void btnChangeClicked(MouseEvent e) {
        switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Schedule" -> {
                schedule = tbSchedule.getSelectionModel().getSelectedItem();
                fxmlLoad("edit_schedule");
            }
            case "Teachers" -> {
                teacher = tbTeacher.getSelectionModel().getSelectedItem();
                fxmlLoad("edit_teacher");
            }
            case "Students" -> {
                student = tbStudent.getSelectionModel().getSelectedItem();
                fxmlLoad("edit_student");
            }
            case "Events" -> {
                event = tbEvent.getSelectionModel().getSelectedItem();
                fxmlLoad("edit_event");
            }
            case "Users" -> {
                user = tbUser.getSelectionModel().getSelectedItem();
                fxmlLoad("edit_user");
            }

        }

    }

    @FXML
    private void btnAddClicked(MouseEvent e) {
        switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Schedule" -> {
                fxmlLoad("add_schedule");
            }
            case "Teachers" -> {
                fxmlLoad("add_teacher");
            }
            case "Students" -> {
                fxmlLoad("add_student");
            }
            case "Events" -> {
                fxmlLoad("add_event");
            }
            case "Users" -> {
                fxmlLoad("add_user");
            }
        }

    }
    @FXML
    private void btnMenuClicked(MouseEvent e) {
        switch (mainTabPane.getSelectionModel().getSelectedItem().getText()) {
            case "Schedule" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(true);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(true);
                btnEdit.setVisible(true);
            }
            case "Teachers" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(true);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(true);
                btnEdit.setVisible(true);
            }
            case "Students" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(true);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(true);
                btnEdit.setVisible(true);
            }
            case "Events" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(true);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(true);
                btnEdit.setVisible(true);
            }
            case "Users" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(true);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(true);
                btnEdit.setVisible(true);
            }
            case "Logs" -> {
                btnCopy.setVisible(true);
                btnAdd.setVisible(false);
                btnRefresh.setVisible(true);
                btnDelete.setVisible(false);
                btnEdit.setVisible(false);
            }
            case "Queries" -> {
                btnCopy.setVisible(false);
                btnAdd.setVisible(false);
                btnRefresh.setVisible(false);
                btnDelete.setVisible(false);
                btnEdit.setVisible(false);
            }
        }
    }

    public static Schedule getSchedule() {
        return schedule;
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    public static Event getEvent() {
        return event;
    }

    public static Student getStudent() {
        return student;
    }

    public static User getUser() {
        return user;
    }

    //<editor-fold defaultstate="collapsed" desc="query">
    @FXML
    void q10Find(MouseEvent event) {
        try {
            var ol = db.selectQ10();
            q10Amount.setCellValueFactory(new PropertyValueFactory<>("count"));
            q10Class.setCellValueFactory(new PropertyValueFactory<>("number"));
            q10Table.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q10Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q10",null, db.selectQ10(), null,null,null, null);
            db.insertLog(new Log(null,user,"Q10 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void q11Find(MouseEvent event) {
        try {
            var ol = db.selectQ11(q11Subj.getValue().getDescription(),q11Class.getValue().getNumber());
            q11Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q11Average.setCellValueFactory(new PropertyValueFactory<>("avg"));
            q11Table.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q12Find(MouseEvent event) {
        try {
            var ol = db.selectQ12();
            q12Label.setText("The busiest teacher: " + ol.get(0).getName());
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q13Find(MouseEvent event) {
        try {
            var ol = db.selectQ13();
            q13Average.setCellValueFactory(new PropertyValueFactory<>("avg"));
            q13Class.setCellValueFactory(new PropertyValueFactory<>("number"));
            q13Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q13Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q13Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q13",null, null, db.selectQ13(),null,null, null);
            db.insertLog(new Log(null,user,"Q13 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void q14Find(MouseEvent event) {
        try {
            var ol = db.selectQ14();
            q14Category.setCellValueFactory(new PropertyValueFactory<>("max"));
            q14Subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            q14Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q14Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q14Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q14",null, null, null, db.selectQ14(),null, null);
            db.insertLog(new Log(null,user,"Q14 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void q15Selected(ActionEvent event) {
        try {
            var ol = db.selectQ15(q15Date.getValue());
            q15Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q15Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q16Find(MouseEvent event) {
        try {
            var ol = db.selectQ16();
            q16Class.setCellValueFactory(new PropertyValueFactory<>("number"));
            q16Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q17Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q17",null, null, null, null, db.selectQ17(q17Class.getValue().getNumber()), null);
            db.insertLog(new Log(null,user,"Q17 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void q17Selected(ActionEvent event) {
        try {
            var ol = db.selectQ17(q17Class.getValue().getNumber());
            q17Avg.setCellValueFactory(new PropertyValueFactory<>("avg"));
            q17Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q17Perf.setCellValueFactory(new PropertyValueFactory<>("perf"));
            q17Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q18Find(MouseEvent event) {
        try {
            var ol = db.selectQ18();
            q18Amount.setCellValueFactory(new PropertyValueFactory<>("count"));
            q18Exp.setCellValueFactory(new PropertyValueFactory<>("exp"));
            q18Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q18Table.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q18Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q18",null, null, null, null, null, db.selectQ18());
            db.insertLog(new Log(null,user,"Q18 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void q20Update(MouseEvent event) {
        try {
            db.updateQ20(q20Student.getValue().getName(),q20Date.getValue(),
                    q20Subj.getValue().getDescription(),Double.parseDouble(q20Grade.getText()));
            
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }
    @FXML
    void q19Update(MouseEvent event) {
        try {
            db.updateQ19(q19Teacher.getValue().getName(), q19Speciality.getValue().getDescription());
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    
    }

    @FXML
    void q1Selected(ActionEvent event) {
        try {
            var ol = db.selectQ1(q1Teacher.getValue());
            q1Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q1PPhone.setCellValueFactory(new PropertyValueFactory<>("phone_parent"));
            q1Phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            q1Student.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q2Selected(ActionEvent event) {
        try {
            var ol = db.selectQ2(q2ComboDay.getValue());
            q2Class.setCellValueFactory(new PropertyValueFactory<>("cl"));
            q2Day.setCellValueFactory(new PropertyValueFactory<>("day"));
            q2End.setCellValueFactory(new PropertyValueFactory<>("time_end"));
            q2Start.setCellValueFactory(new PropertyValueFactory<>("time_start"));
            q2Subj.setCellValueFactory(new PropertyValueFactory<>("subject"));
            q2Teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            q2Schedule.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q3Find(MouseEvent event) {
        try {
            var ol = db.selectQ3(q3Letter.getText());
            q3CLNum.setCellValueFactory(new PropertyValueFactory<>("number"));
            q3Teacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            q3Class.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        } 
    }

    @FXML
    void q4Find(MouseEvent event) {
        try {
            var ol = db.selectQ4(q4Word.getText());
            q4Desc.setCellValueFactory(new PropertyValueFactory<>("description"));
            q4Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            q4Time.setCellValueFactory(new PropertyValueFactory<>("time"));
            q4Event.setItems(ol);
        } catch (SQLException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q5Find(MouseEvent event) {
        try {
            var ol = db.selectQ5(q5Date1.getValue(),q5Date2.getValue());
            q5Desc.setCellValueFactory(new PropertyValueFactory<>("description"));
            q5Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            q5Time.setCellValueFactory(new PropertyValueFactory<>("time"));
            q5Event.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q6Find(MouseEvent event) {
        try {
            var ol = db.selectQ6(q6Date1.getValue(),q6Date2.getValue());
            q6Name.setCellValueFactory(new PropertyValueFactory<>("name"));
            q6Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            q6TSubj.setCellValueFactory(new PropertyValueFactory<>("description"));
            q6Event.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q7Find(MouseEvent event) {
        try {
            var ol = db.selectQ7(q7Date1.getValue(),q7Date2.getValue());
            q7Label.setText("Amount = " + ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q8Find(MouseEvent event) {
        try {
            
            var ol = db.selectQ8(q8Spec.getValue().getDescription(),q8Category.getValue());
            q8Label.setText("Amount = " + ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q9Find(MouseEvent event) {
        try {
            var ol = db.selectQ9(q9Subject.getText());
            q9Avg.setCellValueFactory(new PropertyValueFactory<>("avg"));
            q9Class.setCellValueFactory(new PropertyValueFactory<>("number"));
            q9Subj.setItems(ol);
        } catch (Exception ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    @FXML
    void q9Print(MouseEvent event) {
        try {
            PDFProvider.genPDF("Q9",db.selectQ9(q9Subject.getText()), null, null, null, null, null);
            db.insertLog(new Log(null,user,"Q19 printed",LocalTime.now(),LocalDate.now()));
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void initQueries() {
        try {
            var olTeachers = db.selectTeacher();
            var olSubjects = db.selectSubject();
            var olStudents = db.selectStudent();
            var olClasses = db.selectClass();
            var olDays = db.selectDay();
            var olCategorys = FXCollections.observableArrayList(1, 2, 3, 4);
            var olSpecialitys = db.selectSpeciality();
            q11Class.setItems(olClasses);
            q11Subj.setItems(olSubjects);
            q17Class.setItems(olClasses);
            q2ComboDay.setItems(olDays);
            q8Category.setItems(olCategorys);
            q8Spec.setItems(olSpecialitys);
            q19Teacher.setItems(olTeachers);
            q1Teacher.setItems(olTeachers);
            q20Student.setItems(olStudents);
            q20Subj.setItems(olSubjects);
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    private void selectedQ19(ActionEvent e) {
        try {
            if(q19Teacher.getValue()== null)return;
            var ol = db.selectSpecialityTeacher(q19Teacher.getValue().getId());
            q19Speciality.setItems(ol);
        } catch (SQLException ex) {
            Logger.getLogger(MenuAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //</editor-fold>
    private void fxmlLoad(String fxmlName) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlName + ".fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            a.show();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="declaring variables">
    DBProvider db;
    private static Schedule schedule;
    private static Teacher teacher;
    private static Event event;
    private static Student student;
    private static User user;
    @FXML
    private HBox hbox;
    @FXML
    private TabPane mainTabPane;
    private ObservableList<Schedule> olSchedule;
    private ObservableList<Teacher> olTeacher;
    private ObservableList<Student> olStudent;
    private ObservableList<Event> olEvent;
    private ObservableList<Log> olLog;
    private ObservableList<User> olUser;

    @FXML
    private TextField tfSchedule, tfTeacher, tfStudent, tfEvent, tfLog, tfUser;
    @FXML
    private Label lUser;
    @FXML
    private TableView<Schedule> tbSchedule;
    @FXML
    private TableView<Teacher> tbTeacher;
    @FXML
    private TableView<Student> tbStudent;
    @FXML
    private TableView<Event> tbEvent;
    @FXML
    private TableView<Log> tbLog;
    @FXML
    private TableView<User> tbUser;
    @FXML
    private TableColumn<Schedule, String> scheduleClass;
    @FXML
    private TableColumn<Schedule, LocalTime> scheduleStart;
    @FXML
    private TableColumn<Schedule, LocalTime> scheduleEnd;
    @FXML
    private TableColumn<Schedule, String> scheduleTeacher;
    @FXML
    private TableColumn<Schedule, String> scheduleDay;
    @FXML
    private TableColumn<Schedule, String> scheduleSubject;
    @FXML
    private TableColumn<Teacher, String> teacherName;
    @FXML
    private TableColumn<Teacher, String> teacherPhone;
    @FXML
    private TableColumn<Student, String> studentName;
    @FXML
    private TableColumn<Student, String> studentPhone;
    @FXML
    private TableColumn<Student, String> studentParentPhone;
    @FXML
    private TableColumn<Student, String> studentClass;
    @FXML
    private TableColumn<Event, String> eventDescription;
    @FXML
    private TableColumn<Event, LocalTime> eventTime;
    @FXML
    private TableColumn<Event, LocalDate> eventDate;
    @FXML
    private TableColumn<Log, String> logUser;
    @FXML
    private TableColumn<Log, String> logPrivilege;
    @FXML
    private TableColumn<Log, String> logAction;
    @FXML
    private TableColumn<Log, LocalTime> logTime;
    @FXML
    private TableColumn<Log, LocalDate> logDate;
    @FXML
    private TableColumn<User, String> userLogin;
    @FXML
    private TableColumn<User, String> userPrivilege;
    @FXML
    private TableColumn<User, String> userName;
    @FXML
    private TableColumn<User, String> userPassword;
    //////////////////////
    @FXML
    private TableColumn<ClassAvgStud, Integer> q10Amount;

    @FXML
    private TableColumn<ClassAvgStud, String> q10Class;

    @FXML
    private TableView<ClassAvgStud> q10Table;
    @FXML
    private TableColumn<StudentAvg, Double> q11Average;

    @FXML
    private TableColumn<StudentAvg, String> q11Name;

    @FXML
    private TableView<StudentAvg> q11Table;

    @FXML
    private ComboBox<Class> q11Class;

    @FXML
    private Label q11Label;

    @FXML
    private ComboBox<Subject> q11Subj;

    @FXML
    private Label q12Label;

    @FXML
    private TableColumn<ClassNameAvg, Double> q13Average;

    @FXML
    private TableColumn<ClassNameAvg, String> q13Class;

    @FXML
    private TableColumn<ClassNameAvg, String> q13Name;

    @FXML
    private TableView<ClassNameAvg> q13Table;

    @FXML
    private TableColumn<SubjTeacher, Integer> q14Category;

    @FXML
    private TableColumn<SubjTeacher, String> q14Name;

    @FXML
    private TableColumn<SubjTeacher, String> q14Subject;

    @FXML
    private TableView<SubjTeacher> q14Table;

    @FXML
    private DatePicker q15Date;

    @FXML
    private TableColumn<Student, String> q15Name;

    @FXML
    private TableView<Student> q15Table;

    @FXML
    private TableColumn<Class, String> q16Class;

    @FXML
    private TableView<Class> q16Table;

    @FXML
    private TableColumn<StudAvgPerf, Double> q17Avg;

    @FXML
    private ComboBox<Class> q17Class;

    @FXML
    private TableColumn<StudAvgPerf, String> q17Name;

    @FXML
    private TableColumn<StudAvgPerf, String> q17Perf;

    @FXML
    private TableView<StudAvgPerf> q17Table;

    @FXML
    private TableColumn<TeacherCountAttest, Integer> q18Amount;

    @FXML
    private TableColumn<TeacherCountAttest, String> q18Exp;

    @FXML
    private TableColumn<TeacherCountAttest, String> q18Name;

    @FXML
    private TableView<TeacherCountAttest> q18Table;

    @FXML
    private ComboBox<Speciality> q19Speciality;

    @FXML
    private SearchableComboBox<Teacher> q19Teacher;

    @FXML
    private TableColumn<Student, String> q1Name;

    @FXML
    private TableColumn<Student, String> q1PPhone;

    @FXML
    private TableColumn<Student, String> q1Phone;

    @FXML
    private TableView<Student> q1Student;

    @FXML
    private SearchableComboBox<Teacher> q1Teacher;

    @FXML
    private DatePicker q20Date;

    @FXML
    private TextField q20Grade;

    @FXML
    private SearchableComboBox<Student> q20Student;
    @FXML
    private SearchableComboBox<Subject> q20Subj;

    @FXML
    private TableColumn<Schedule, String> q2Class;

    @FXML
    private TableColumn<Schedule, Integer> q2Day;

    @FXML
    private TableColumn<Schedule, LocalTime> q2End;

    @FXML
    private TableView<Schedule> q2Schedule;

    @FXML
    private TableColumn<Schedule, LocalTime> q2Start;

    @FXML
    private TableColumn<Schedule, Integer> q2Subj;

    @FXML
    private TableColumn<Schedule, Integer> q2Teacher;

    @FXML
    private TableColumn<Class, String> q3CLNum;

    @FXML
    private TableView<Class> q3Class;

    @FXML
    private TextField q3Letter;

    @FXML
    private TableColumn<Class, String> q3Teacher;

    @FXML
    private TableColumn<Event, LocalDate> q4Date;

    @FXML
    private TableColumn<Event, String> q4Desc;

    @FXML
    private TableView<Event> q4Event;

    @FXML
    private TableColumn<Event, LocalTime> q4Time;

    @FXML
    private TextField q4Word;

    @FXML
    private TableColumn<Event, LocalDate> q5Date;

    @FXML
    private DatePicker q5Date1;

    @FXML
    private DatePicker q5Date2;

    @FXML
    private TableColumn<Event, String> q5Desc;

    @FXML
    private TableView<Event> q5Event;

    @FXML
    private TableColumn<Event, LocalTime> q5Time;

    @FXML
    private TableColumn<TeacherAttes, LocalDate> q6Date;

    @FXML
    private DatePicker q6Date1;

    @FXML
    private DatePicker q6Date2;

    @FXML
    private TableView<TeacherAttes> q6Event;

    @FXML
    private TableColumn<TeacherAttes, String> q6Name;

    @FXML
    private TableColumn<TeacherAttes, String> q6TSubj;

    @FXML
    private DatePicker q7Date1;

    @FXML
    private DatePicker q7Date2;

    @FXML
    private Label q7Label;

    @FXML
    private ComboBox<Integer> q8Category;

    @FXML
    private Label q8Label;
    @FXML
    private ComboBox<Day> q2ComboDay;
    @FXML
    private ComboBox<Speciality> q8Spec;

    @FXML
    private TableColumn<ClassAvgGrade, Double> q9Avg;

    @FXML
    private TableColumn<ClassAvgGrade, String> q9Class;

    @FXML
    private TableView<ClassAvgGrade> q9Subj;

    @FXML
    private TextField q9Subject;
    @FXML
    private Button btnAdd;

    @FXML
    private Button btnCopy;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnRefresh;
    //</editor-fold>
}
