package com.learning.school.dao;

import com.learning.school.dao.mapper.RowMapper;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.Optional;

public abstract class AbstractDAO<T> extends SimpleQueryExecutor<T> {
    private final String tableName;

    public AbstractDAO(RowMapper<T> rowMapper, String tableName) {
        super(rowMapper);
        this.tableName = tableName;
    }

    public abstract void save(T obj);

    public Optional<T> get(int id) {
        String query = "SELECT * FROM " + tableName + " WHERE id = ?";
        return Optional.empty();
    }

    public ObservableList<T> getAll() {
        String query = "SELECT * FROM " + tableName;
        return executeQuery(query);
    }

    public void remove(int id) {
        String query = "DELETE FROM " + tableName + " WHERE id = ?";
        executeUpdate(query, id);
    }

    public abstract void update(T obj, Object... params);

}
