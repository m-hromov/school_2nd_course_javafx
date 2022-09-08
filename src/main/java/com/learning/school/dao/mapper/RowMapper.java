package com.learning.school.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    public T map(ResultSet rs) throws SQLException;
}
