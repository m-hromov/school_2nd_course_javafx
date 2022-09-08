package com.learning.school.dao.mapper.impl;

import com.learning.school.dao.mapper.RowMapper;
import com.learning.school.entity.Teacher;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RowMapperTeacher implements RowMapper<Teacher> {
    @Override
    public Teacher map(ResultSet rs) throws SQLException {
        Teacher teacher = new Teacher();
        teacher.setId(rs.getInt("id"));
        teacher.setName(rs.getString("name"));
        teacher.setPhone(rs.getString("phone"));
        return teacher;
    }
}
