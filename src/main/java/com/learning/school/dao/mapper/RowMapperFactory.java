package com.learning.school.dao.mapper;

import com.learning.school.dao.mapper.impl.RowMapperStudent;
import com.learning.school.dao.mapper.impl.RowMapperTeacher;
import com.learning.school.entity.Student;
import com.learning.school.entity.Teacher;

public class RowMapperFactory {
    private static RowMapperFactory INSTANCE = new RowMapperFactory();

    private final RowMapper<Student> studentRowMapper = new RowMapperStudent();
    private final RowMapper<Teacher> teacherRowMapper = new RowMapperTeacher();
    public static RowMapperFactory getInstance() {
        return INSTANCE;
    }

    public RowMapper<Student> getStudentRowMapper() {
        return studentRowMapper;
    }

    public RowMapper<Teacher> getTeacherRowMapper() {
        return teacherRowMapper;
    }
}
