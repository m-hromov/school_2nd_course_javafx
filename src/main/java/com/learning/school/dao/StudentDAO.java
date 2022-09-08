package com.learning.school.dao;

import com.learning.school.dao.mapper.RowMapperFactory;
import com.learning.school.entity.Student;

public class StudentDAO extends AbstractDAO<Student> {

    private final String INSERT_STUDENT = "INSERT INTO student (name,phone,phone_parent,number_class) VALUES (?,?,?,?)";

    public StudentDAO() {
        super(RowMapperFactory.getInstance().getStudentRowMapper(), "student");
    }

    @Override
    public void save(Student student) {
        executeUpdate(INSERT_STUDENT, student.getName(), student.getPhone(),
                student.getPhone_parent(), student.getCl().getNumber());
    }

    private final String UPDATE_STUDENT = "UPDATE student SET name = ? , phone = ?, phone_parent = ?," +
            " number_class = ? WHERE id = ?";
    @Override
    public void update(Student student, Object... params) {
        executeUpdate(UPDATE_STUDENT, student.getName(), student.getPhone(), student.getPhone_parent(),
                student.getCl().getNumber(), student.getId());
    }
}
