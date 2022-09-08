package com.learning.school.dao;

import com.learning.school.dao.connection.DBProvider;
import com.learning.school.dao.mapper.RowMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleQueryExecutor<T> {

    private final RowMapper<T> rowMapper;

    public SimpleQueryExecutor(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    protected ObservableList<T> executeQuery(String query, Object... params){
        ObservableList<T> list = FXCollections.observableArrayList();
        try(PreparedStatement ps = prepareStatement(query,params)){
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rowMapper.map(rs));
            }
        }catch(SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"wrong query");
        }
        return list;
    }

    protected void executeUpdate(String query, Object... params){
        try(PreparedStatement ps = prepareStatement(query,params)){
            ps.executeUpdate();
        }catch(SQLException e) {
            Logger.getGlobal().log(Level.SEVERE,"wrong query");
        }
    }

    protected PreparedStatement prepareStatement(String query, Object... params) throws SQLException{
        Connection connection = DBProvider.getInstance().getConnection();
        PreparedStatement ps = connection.prepareStatement(query);
        for (int i = 1; i <= params.length; i++) {
            ps.setObject(i,params[i-1]);
        }
        return ps;
    }
}
