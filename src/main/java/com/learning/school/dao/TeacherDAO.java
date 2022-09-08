package com.learning.school.dao;

import com.learning.school.dao.mapper.RowMapperFactory;
import com.learning.school.entity.Teacher;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TeacherDAO extends AbstractDAO<Teacher>{
    public TeacherDAO() {
        super(RowMapperFactory.getInstance().getTeacherRowMapper(), "teacher");
    }

    private final String INSERT_TEACHER = "INSERT INTO teacher (name,phone) VALUES (?,?)";
    @Override
    public void save(Teacher teacher) {
        executeUpdate(INSERT_TEACHER, teacher.getName(), teacher.getPhone());
    }

    private final String UPDATE_TEACHER = "UPDATE teacher SET name = ? , phone = ? WHERE id = ?";
    @Override
    public void update(Teacher teacher, Object... params) {
        executeUpdate(UPDATE_TEACHER, teacher.getName(), teacher.getPhone(), teacher.getId());
    }
}
