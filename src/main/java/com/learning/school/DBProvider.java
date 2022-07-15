/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.learning.school;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBProvider {

    String username = "MarkedOne";
    String password = "GuysIcaughtAnOlive";
    String url = "jdbc:postgresql://localhost:5432/school_management";
    Connection connection;

    public DBProvider() {
        getConnection();
    }

    private void getConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            Logger.getLogger(DBProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Schedule> selectSchedule() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM schedule "
                + "JOIN teacher ON teacher.id = id_teacher "
                + "JOIN day ON day.id = id_day "
                + "JOIN class ON number = number_class "
                + "JOIN subject ON subject.id = id_subject");
        ResultSet rs = ps.executeQuery();
        ArrayList<Schedule> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Schedule(rs.getInt("id"), LocalTime.parse(rs.getString("time_start")),
                    LocalTime.parse(rs.getString("time_end")), new Day(rs.getInt("id_day"),
                    rs.getString("day")), new Subject(rs.getInt("id_subject"),
                    rs.getString("description"), rs.getString("number_audience")),
                    new Teacher(rs.getInt("id_teacher"), rs.getString("name"),
                            rs.getString("phone")), new Class(rs.getString("number_class"),
                            new Teacher(rs.getInt("id_teacher"), rs.getString("name"), rs.getString("phone")))));
        }
        return FXCollections.observableArrayList(ar);

    }

    public ObservableList<Schedule> selectSchedule(String number) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM schedule "
                + "JOIN teacher ON teacher.id = id_teacher "
                + "JOIN day ON day.id = id_day "
                + "JOIN class ON number = number_class "
                + "JOIN subject ON subject.id = id_subject "
                + "WHERE number = ?");
        ps.setString(1, number);
        ResultSet rs = ps.executeQuery();
        ArrayList<Schedule> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Schedule(rs.getInt("id"), LocalTime.parse(rs.getString("time_start")),
                    LocalTime.parse(rs.getString("time_end")), new Day(rs.getInt("id_day"),
                    rs.getString("day")), new Subject(rs.getInt("id_subject"),
                    rs.getString("description"), rs.getString("number_audience")),
                    new Teacher(rs.getInt("id_teacher"), rs.getString("name"),
                            rs.getString("phone")), new Class(rs.getString("number_class"),
                            new Teacher(rs.getInt("id_teacher"), rs.getString("name"), rs.getString("phone")))));
        }
        return FXCollections.observableArrayList(ar);

    }

    public ObservableList<Schedule> selectSchedule(int idTeacher) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM schedule "
                + "JOIN teacher ON teacher.id = id_teacher "
                + "JOIN day ON day.id = id_day "
                + "JOIN class ON number = number_class "
                + "JOIN subject ON subject.id = id_subject "
                + "WHERE teacher.id = ?");
        ps.setInt(1, idTeacher);
        ResultSet rs = ps.executeQuery();
        ArrayList<Schedule> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Schedule(rs.getInt("id"), LocalTime.parse(rs.getString("time_start")),
                    LocalTime.parse(rs.getString("time_end")), new Day(rs.getInt("id_day"),
                    rs.getString("day")), new Subject(rs.getInt("id_subject"),
                    rs.getString("description"), rs.getString("number_audience")),
                    new Teacher(rs.getInt("id_teacher"), rs.getString("name"),
                            rs.getString("phone")), new Class(rs.getString("number_class"),
                            new Teacher(rs.getInt("id_teacher"), rs.getString("name"), rs.getString("phone")))));
        }
        return FXCollections.observableArrayList(ar);

    }

    public ObservableList<Teacher> selectTeacher() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM teacher");
        ResultSet rs = ps.executeQuery();
        ArrayList<Teacher> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Teacher(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Student> selectStudent() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM student "
                + "JOIN class ON number = number_class "
                + "LEFT JOIN teacher ON teacher.id = id_teacher");
        ResultSet rs = ps.executeQuery();
        ArrayList<Student> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
                    rs.getString("phone_parent"), new Class(rs.getString("number_class"),
                    new Teacher(rs.getInt("id_teacher"), rs.getString("name"), rs.getString("phone")))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Organizator> selectOrganizator() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM organizator ");
        ResultSet rs = ps.executeQuery();
        ArrayList<Organizator> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Organizator(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Event> selectEvent() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM event");
        ResultSet rs = ps.executeQuery();
        ArrayList<Event> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Event(rs.getInt("id"), rs.getString("description"),
                    LocalTime.parse(rs.getString("time")), LocalDate.parse(rs.getString("date"))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Log> selectLog() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM log "
                + "JOIN \"user\" ON \"user\".id = id_user");
        ResultSet rs = ps.executeQuery();
        ArrayList<Log> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Log(rs.getInt("id"), new User(rs.getInt("id_user"), rs.getString("login"),
                    rs.getString("password"), rs.getString("privilege"), null, null), rs.getString("action"),
                    LocalTime.parse(rs.getString("time")), LocalDate.parse(rs.getString("date"))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Day> selectDay() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM day");
        ResultSet rs = ps.executeQuery();
        ArrayList<Day> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Day(rs.getInt("id"), rs.getString("day")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Subject> selectSubject() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM subject");
        ResultSet rs = ps.executeQuery();
        ArrayList<Subject> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Subject(rs.getInt("id"), rs.getString("description"), rs.getString("number_audience")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Class> selectClass() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM class ");
        ResultSet rs = ps.executeQuery();
        ArrayList<Class> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Class(rs.getString("number"), null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public Class selectClassTeacher(int id_teacher) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM class "
                + "JOIN teacher ON id_teacher = teacher.id "
                + "WHERE id_teacher = ?");
        ps.setInt(1, id_teacher);
        ResultSet rs = ps.executeQuery();
        Class cl = null;
        while (rs.next()) {
            cl = new Class(rs.getString("number"), null);
        }
        return cl;
    }

    public ObservableList<Attestation> selectAttestation(int id_teacher) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM attestation AS a "
                + "JOIN speciality ON speciality.id = id_speciality "
                + "JOIN teacher ON id_teacher = teacher.id "
                + "WHERE id_teacher = ?");
        ps.setInt(1, id_teacher);
        ResultSet rs = ps.executeQuery();
        ArrayList<Attestation> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Attestation(rs.getInt("id"), LocalDate.parse(rs.getString("date_passed")),
                    LocalDate.parse(rs.getString("date_next")), new Speciality(rs.getInt("id_speciality"), rs.getString("description")),
                    rs.getInt("category"), null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Class> selectCheckClass(int idEvent) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT number_class FROM class_event "
                + "WHERE id_event = ?");
        ps.setInt(1, idEvent);
        ResultSet rs = ps.executeQuery();
        ArrayList<Class> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Class(rs.getString("number_class"), null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Organizator> selectCheckOrg(int idEvent) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT id_organizator FROM organizator_event "
                + "WHERE id_event = ?");
        ps.setInt(1, idEvent);
        ResultSet rs = ps.executeQuery();
        ArrayList<Organizator> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Organizator(rs.getInt("id_organizator"), null, null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<User> selectUser() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT \"user\".id,login,password,privilege,id_student,id_teacher,name "
                + "FROM \"user\" JOIN student ON student.id = id_student UNION "
                + "SELECT \"user\".id,login,password,privilege,id_student,id_teacher,name "
                + "FROM \"user\" JOIN teacher ON teacher.id = id_teacher UNION "
                + "SELECT \"user\".id,login,password,privilege,id_student,id_teacher,NULL AS name "
                + "FROM \"user\" WHERE id_student ISNULL AND id_teacher ISNULL");
        ResultSet rs = ps.executeQuery();
        ArrayList<User> ar = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("id_student") == 0 && rs.getInt("id_teacher") > 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"),
                        null, new Teacher(rs.getInt("id_teacher"), rs.getString("name"), null)));
            }
            if (rs.getInt("id_student") > 0 && rs.getInt("id_teacher") == 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"),
                        new Student(rs.getInt("id_student"), rs.getString("name"), null, null, null), null));
            }
            if (rs.getInt("id_student") == 0 && rs.getInt("id_teacher") == 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"), null, null));
            }
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<User> auth(String login, String password) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM (SELECT \"user\".id,login,password,privilege,id_student,id_teacher,name,number_class "
                + "FROM \"user\" JOIN student ON student.id = id_student UNION "
                + "SELECT \"user\".id,login,password,privilege,id_student,id_teacher,name,NULL as number_class "
                + "FROM \"user\" JOIN teacher ON teacher.id = id_teacher UNION "
                + "SELECT \"user\".id,login,password,privilege,id_student,id_teacher,NULL AS name,NULL as number_class "
                + "FROM \"user\" WHERE id_student ISNULL AND id_teacher ISNULL) AS a WHERE login = ? AND password = ?");
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        ArrayList<User> ar = new ArrayList<>();
        while (rs.next()) {
            if (rs.getInt("id_student") == 0 && rs.getInt("id_teacher") > 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"),
                        null, new Teacher(rs.getInt("id_teacher"), rs.getString("name"), null)));
            }
            if (rs.getInt("id_student") > 0 && rs.getInt("id_teacher") == 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"),
                        new Student(rs.getInt("id_student"), rs.getString("name"), null, null, new Class(rs.getString("number_class"), null)), null));
            }
            if (rs.getInt("id_student") == 0 && rs.getInt("id_teacher") == 0) {
                ar.add(new User(rs.getInt("id"), rs.getString("login"), rs.getString("password"), rs.getString("privilege"), null, null));
            }
        }
        return FXCollections.observableArrayList(ar);
    }

    public void delete(String table, int id) throws SQLException {
        String sql = String.format("DELETE FROM \"%s\" WHERE id = ?", table);
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);
        ps.execute();
    }

    public void deleteClass(String number) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM class WHERE number = ?");
        ps.setString(1, number);
        ps.execute();
    }

    public void deleteClassEvent(String number, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM class_event WHERE number_class = ? AND id_event = ?");
        ps.setString(1, number);
        ps.setInt(2, id);
        ps.execute();
    }

    public void deleteOrgEvent(int idOrg, int idEvent) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM organizator_event WHERE id_organizator = ? AND id_event = ?");
        ps.setInt(1, idOrg);
        ps.setInt(2, idEvent);
        ps.execute();
    }

    void updateSchedule(Schedule schedule) throws SQLException {
        String sql = String.format("UPDATE schedule SET time_start = '%s' ,"
                + "time_end = '%s' ,id_day = ? ,id_subject = ? ,id_teacher = ? ,"
                + "number_class = ? WHERE id = ?", schedule.getTime_start().toString(), schedule.getTime_end().toString());
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, schedule.getDay().getId());
        ps.setInt(2, schedule.getSubject().getId());
        ps.setInt(3, schedule.getTeacher().getId());
        ps.setString(4, schedule.getCl().getNumber());
        ps.setInt(5, schedule.getId());
        ps.executeUpdate();
    }

    void updateAttestation(Attestation attestation) throws SQLException {
        String sql = String.format("UPDATE attestation SET date_passed = '%s' ,"
                + "date_next = '%s' ,category = ? WHERE id = ?", attestation.getDatePassed().toString(), attestation.getDateNext().toString());
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, attestation.getCategory());
        ps.setInt(2, attestation.getId());
        ps.executeUpdate();
    }

    void addSchedule(Schedule schedule) throws SQLException {
        String sql = String.format("INSERT INTO schedule (time_start,time_end,id_day,id_subject,id_teacher,number_class) VALUES ('%s','%s',?,?,?,?)", schedule.getTime_start().toString(), schedule.getTime_end().toString());
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, schedule.getDay().getId());
        ps.setInt(2, schedule.getSubject().getId());
        ps.setInt(3, schedule.getTeacher().getId());
        ps.setString(4, schedule.getCl().getNumber());
        ps.executeUpdate();
    }

    void insertAttestation(Attestation attestation) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO speciality (description) VALUES (?)");
        ps.setString(1, attestation.getSpeciality().getDescription());
        ps.executeUpdate();
        ps = connection.prepareStatement("SELECT * FROM speciality");
        ResultSet rs = ps.executeQuery();
        int i = 0;
        while (rs.next()) {
            i = rs.getInt("id");
        }
        ps = connection.prepareStatement(String.format("INSERT INTO attestation (date_passed,date_next, id_speciality, id_teacher, category) "
                + "VALUES ('%s','%s', ?, ?,?)",
                attestation.getDatePassed().toString(), attestation.getDateNext().toString()));
        ps.setInt(3, attestation.getCategory());
        ps.setInt(1, i);
        ps.setInt(2, attestation.getTeacher().getId());
        ps.executeUpdate();
    }

    void updateTeacher(Teacher teacher, Class cl) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE teacher SET name = ? ,"
                + "phone = ? WHERE id = ?");
        ps.setString(1, teacher.getName());
        ps.setString(2, teacher.getPhone());
        ps.setInt(3, teacher.getId());
        ps.executeUpdate();
        ps = connection.prepareStatement("UPDATE class SET id_teacher = ? WHERE number = ?");
        ps.setString(2, cl.getNumber());
        ps.setInt(1, teacher.getId());
        ps.executeUpdate();
    }

    void updateEvent(Event event) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format("UPDATE event SET description = ? ,"
                + "date = '%s', time = '%s' WHERE id = ?", event.getDate().toString(), event.getTime().toString()));
        ps.setString(1, event.getDescription());
        ps.setInt(2, event.getId());
        ps.executeUpdate();
    }

    void insertTeacher(Teacher teacher) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO teacher (name,phone) VALUES (?,?)");
        ps.setString(1, teacher.getName());
        ps.setString(2, teacher.getPhone());
        ps.executeUpdate();
    }

    void insertJournal(Journal journal) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format("INSERT INTO lesson (date,id_schedule) VALUES ('%s',?) RETURNING id", journal.getLesson().getDate().toString()));

        ps.setInt(1, journal.getLesson().getSchedule().getId());

        ResultSet rs = ps.executeQuery();
        rs.next();
        int i = rs.getInt(1);
        ps = connection.prepareStatement("INSERT INTO journal (id_student,id_lesson,grade) VALUES (?,?,?) RETURNING id");

        ps.setInt(1, journal.getStudent().getId());
        ps.setInt(2, i);
        ps.setDouble(3, journal.getGrade());

        ps.execute();
    }

    void insertClassEvent(String number, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO class_event VALUES (?,?)");
        ps.setString(1, number);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    void insertOrgEvent(int idOrg, int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO organizator_event VALUES (?,?)");
        ps.setInt(1, idOrg);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    void updateStudent(Student student) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE student SET name = ? ,"
                + "phone = ?, phone_parent = ?, number_class = ? WHERE id = ?");
        ps.setString(1, student.getName());
        ps.setString(2, student.getPhone());
        ps.setString(3, student.getPhone_parent());
        ps.setString(4, student.getCl().getNumber());
        ps.setInt(5, student.getId());
        ps.executeUpdate();
    }

    void updateOrg(Organizator org) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE organizator SET name = ? ,"
                + "phone = ? WHERE id = ?");
        ps.setString(1, org.getName());
        ps.setString(2, org.getPhone());
        ps.setInt(3, org.getId());
        ps.executeUpdate();
    }

    void updateUser(User user) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE \"user\" SET login = ? ,"
                + "password = ? WHERE id = ?");
        ps.setString(1, user.getLogin());
        ps.setString(2, user.getPassword());
        ps.setInt(3, user.getId());
        ps.executeUpdate();
    }

    void updateJournal(Journal journal) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE journal SET grade = ? WHERE id = ?");
        ps.setDouble(1, journal.getGrade());
        ps.setInt(2, journal.getId());
        ps.executeUpdate();
    }

    void insertClass(Class cl) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO class (number) VALUES (?)");
        ps.setString(1, cl.getNumber());
        ps.executeUpdate();
    }

    void insertOrg(Organizator org) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO organizator (name,phone) VALUES (?,?)");
        ps.setString(1, org.getName());
        ps.setString(2, org.getPhone());
        ps.executeUpdate();
    }

    void insertStudent(Student student) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO student (name,phone,phone_parent,number_class)"
                + " VALUES (?,?,?,?)");
        ps.setString(1, student.getName());
        ps.setString(2, student.getPhone());
        ps.setString(3, student.getPhone_parent());
        ps.setString(4, student.getCl().getNumber());
        ps.executeUpdate();
    }

    void insertEvent(Event event, ObservableList<Organizator> olOrg, ObservableList<Class> olClass) throws SQLException {
        var sql = "INSERT INTO event (description,date,time) VALUES (?,'%s','%s') RETURNING id";
        var ps = connection.prepareStatement(String.format(sql, event.getDate().toString(), event.getTime().toString()));
        ps.setString(1, event.getDescription());
        ResultSet rs = ps.executeQuery();
        int id = 0;
        while (rs.next()) {
            id = rs.getInt(1);
        }
        for (var i : olOrg) {
            sql = "INSERT INTO organizator_event VALUES (?,?)";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, i.getId());
            ps.setInt(2, id);
        }
        for (var i : olClass) {
            sql = "INSERT INTO class_event VALUES (?,?)";
            ps = connection.prepareStatement(sql);
            ps.setString(1, i.getNumber());
            ps.setInt(2, id);
        }
    }

    public ObservableList<Student> selectQ1(Teacher teacher) throws SQLException {
        if (teacher == null) {
            return null;
        }
        PreparedStatement ps = connection.prepareStatement(Queries.Q1);
        ps.setString(1, teacher.getName());
        ResultSet rs = ps.executeQuery();
        ArrayList<Student> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("phone"),
                    rs.getString("phone_parent"), null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Schedule> selectQ2(Day day) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q2);
        ps.setString(1, day.getDay());
        ResultSet rs = ps.executeQuery();
        ArrayList<Schedule> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Schedule(rs.getInt("id"), LocalTime.parse(rs.getString("time_start")), LocalTime.parse(rs.getString("time_end")),
                    new Day(rs.getInt("id_day"), null),
                    new Subject(rs.getInt("id_subject"), null, null),
                    new Teacher(rs.getInt("id_teacher"), null, null), new Class(rs.getString("number_class"), null)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Class> selectQ3(String s) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q3 + s + "'");
        ResultSet rs = ps.executeQuery();
        ArrayList<Class> ar = new ArrayList<>();
        while (rs.next()) {
            if (rs.getString("name") == null) {
                ar.add(new Class(rs.getString("number"), null));
            } else {
                ar.add(new Class(rs.getString("number"), new Teacher(null, rs.getString("name"), null)));
            }
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Event> selectQ4(String s) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q4 + s + "%'");
        ResultSet rs = ps.executeQuery();
        ArrayList<Event> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Event(rs.getInt("id"), rs.getString("description"),
                    LocalTime.parse(rs.getString("time")), LocalDate.parse(rs.getString("date"))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Event> selectQ5(LocalDate d1, LocalDate d2) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format(Queries.Q5, d1.toString(), d2.toString()));
        ResultSet rs = ps.executeQuery();
        ArrayList<Event> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Event(rs.getInt("id"), rs.getString("description"),
                    LocalTime.parse(rs.getString("time")), LocalDate.parse(rs.getString("date"))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<TeacherAttes> selectQ6(LocalDate d1, LocalDate d2) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format(Queries.Q6, d1.toString(), d2.toString()));
        ResultSet rs = ps.executeQuery();
        ArrayList<TeacherAttes> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new TeacherAttes(rs.getString("name"), rs.getString("description"), LocalDate.parse(rs.getString("date_next"))));
        }
        return FXCollections.observableArrayList(ar);
    }

    public int selectQ7(LocalDate d1, LocalDate d2) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format(Queries.Q7, d1.toString(), d2.toString()));
        ResultSet rs = ps.executeQuery();
        rs.next();

        return rs.getInt(1);
    }

    public int selectQ8(String speciality, int ctegory) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q8);
        ps.setString(1, speciality);
        ps.setInt(2, ctegory);
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    public ObservableList<ClassAvgGrade> selectQ9(String subject) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q9);
        ps.setString(1, subject);
        ResultSet rs = ps.executeQuery();
        ArrayList<ClassAvgGrade> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new ClassAvgGrade(rs.getString(1), rs.getDouble(2)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<ClassAvgStud> selectQ10() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q10);
        ResultSet rs = ps.executeQuery();
        ArrayList<ClassAvgStud> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new ClassAvgStud(rs.getString(1), rs.getInt(2)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<StudentAvg> selectQ11(String subj, String cl) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q11);
        ps.setString(1, subj);
        ps.setString(2, cl);
        ps.setString(3, subj);
        ps.setString(4, cl);
        ResultSet rs = ps.executeQuery();
        ArrayList<StudentAvg> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new StudentAvg(rs.getString(1), rs.getDouble(2)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<TeacherBusy> selectQ12() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q12);
        ResultSet rs = ps.executeQuery();
        ArrayList<TeacherBusy> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new TeacherBusy(rs.getString(1), rs.getInt(2)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<ClassNameAvg> selectQ13() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q13);
        ResultSet rs = ps.executeQuery();
        ArrayList<ClassNameAvg> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new ClassNameAvg(rs.getString(1), rs.getString(2), rs.getDouble(3)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<SubjTeacher> selectQ14() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q14);
        ResultSet rs = ps.executeQuery();
        ArrayList<SubjTeacher> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new SubjTeacher(rs.getString(1), rs.getString(2), rs.getInt(3)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Student> selectQ15(LocalDate d) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format(Queries.Q15, d.toString()));
        ResultSet rs = ps.executeQuery();
        ArrayList<Student> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Student(null, rs.getString("name"), null, null, null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Speciality> selectSpeciality() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM speciality");
        ResultSet rs = ps.executeQuery();
        ArrayList<Speciality> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Speciality(null, rs.getString("description")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Speciality> selectSpecialityTeacher(int id) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM speciality JOIN attestation ON id_speciality = speciality.id JOIN teacher ON teacher.id = id_teacher WHERE id_teacher = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        ArrayList<Speciality> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Speciality(null, rs.getString("description")));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Class> selectQ16() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q16);
        ResultSet rs = ps.executeQuery();
        ArrayList<Class> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Class(rs.getString("number"), null));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<StudAvgPerf> selectQ17(String num) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q17);
        ps.setString(1, num);
        ps.setString(2, num);
        ps.setString(3, num);
        ResultSet rs = ps.executeQuery();
        ArrayList<StudAvgPerf> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new StudAvgPerf(rs.getString(1), rs.getDouble(2), rs.getString(3)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<TeacherCountAttest> selectQ18() throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q18);
        ResultSet rs = ps.executeQuery();
        ArrayList<TeacherCountAttest> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new TeacherCountAttest(rs.getString(1), rs.getInt(2), rs.getString(3)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public void updateQ19(String teacher, String subj) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(Queries.Q19);
        ps.setString(1, subj);
        ps.setString(2, teacher);
        ps.executeUpdate();
    }

    public void updateQ20(String name, LocalDate d, String desc, Double grade) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(String.format(Queries.Q20, d.toString()));
        ps.setDouble(1, grade);
        ps.setString(2, name);
        ps.setString(3, desc);
        ps.executeUpdate();
    }

    void insertLog(Log log) {
        try {
            PreparedStatement ps = connection.prepareStatement(String.format("INSERT INTO log (id_user,action,date,time)"
                    + " VALUES (?,?,'%s','%s')", log.getDate().toString(), log.getTime().toString()));
            ps.setInt(1, log.getUser().getId());
            ps.setString(2, log.getAction());
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DBProvider.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ObservableList<Journal> selectJournal(int idStud) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT journal.id,lesson.id,description,number_audience,date,grade FROM journal JOIN lesson ON lesson.id = id_lesson JOIN schedule ON schedule.id = id_schedule JOIN student ON student.id = id_student JOIN subject ON subject.id = id_subject WHERE id_student = ?");
        ps.setInt(1, idStud);
        ResultSet rs = ps.executeQuery();
        ArrayList<Journal> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Journal(rs.getInt(1), null, new Lesson(rs.getInt(2), new Schedule(null, null, null, null, new Subject(null, rs.getString(3), rs.getString(4)), null, null), LocalDate.parse(rs.getString(5))), rs.getDouble(6)));
        }
        return FXCollections.observableArrayList(ar);
    }

    public ObservableList<Journal> selectJournalT(int idTeach) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT journal.id,lesson.id,description,number_audience,date,grade,student.id,student.name FROM journal JOIN lesson ON lesson.id = id_lesson JOIN schedule ON schedule.id = id_schedule JOIN teacher ON teacher.id = id_teacher JOIN student ON student.id = id_student JOIN subject ON subject.id = id_subject WHERE id_teacher = ?");
        ps.setInt(1, idTeach);
        ResultSet rs = ps.executeQuery();
        ArrayList<Journal> ar = new ArrayList<>();
        while (rs.next()) {
            ar.add(new Journal(rs.getInt(1), new Student(rs.getInt(7), rs.getString(8), null, null, null), new Lesson(rs.getInt(2), new Schedule(null, null, null, null, new Subject(null, rs.getString(3), rs.getString(4)), null, null), LocalDate.parse(rs.getString(5))), rs.getDouble(6)));
        }
        return FXCollections.observableArrayList(ar);
    }

    void insertUser(User user) throws SQLException {
        PreparedStatement ps;
        switch (user.getPrivilege()) {
            case "admin" -> {
                ps = connection.prepareStatement("INSERT INTO \"user\" (login,password,privilege)"
                        + " VALUES (?,?,?)");
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getPrivilege());
                ps.executeUpdate();
            }
            case "student" -> {
                ps = connection.prepareStatement("INSERT INTO \"user\" (login,password,privilege,id_student)"
                        + " VALUES (?,?,?,?)");
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getPrivilege());
                ps.setInt(4, user.getStudent().getId());
                ps.executeUpdate();
            }

            case "teacher" -> {
                ps = connection.prepareStatement("INSERT INTO \"user\" (login,password,privilege,id_teacher)"
                        + " VALUES (?,?,?,?)");
                ps.setString(1, user.getLogin());
                ps.setString(2, user.getPassword());
                ps.setString(3, user.getPrivilege());
                ps.setInt(4, user.getTeacher().getId());
                ps.executeUpdate();
            }
        }
    }
}
