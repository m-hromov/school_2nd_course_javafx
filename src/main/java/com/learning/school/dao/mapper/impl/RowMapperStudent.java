package com.learning.school.dao.mapper.impl;

import com.learning.school.dao.mapper.RowMapper;
import com.learning.school.entity.Class;
import com.learning.school.entity.Student;
import com.learning.school.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperStudent implements RowMapper<Student> {
    @Override
    public Student map(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id_teacher"));
        teacher.setName(rs.getString("name"));
        teacher.setPhone(rs.getString("phone"));

        Class cl = new Class();
        cl.setTeacher(teacher);
        cl.setNumber(rs.getString("number_class"));

        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setCl(cl);
        student.setName(rs.getString("name"));
        student.setPhone(rs.getString("phone"));
        student.setPhone_parent(rs.getString("phone_parent"));
        return student;
    }
}
