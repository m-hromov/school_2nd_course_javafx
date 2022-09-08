
package com.learning.school.dao;

import com.learning.school.entity.Student;
import com.learning.school.entity.Teacher;

/**
 *
 * @author hromov
 */
public class UserDAO {
    private Integer id;
    private String login;
    private String password;
    private String privilege;
    private Student student;
    private Teacher teacher;

    public UserDAO(Integer id, String login, String password, String privilege, Student student, Teacher teacher) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.privilege = privilege;
        this.student = student;
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    

    @Override
    public String toString() {
        return login;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPrivilege() {
        return privilege;
    }

    public void setPrivilege(String privilege) {
        this.privilege = privilege;
    }
}
