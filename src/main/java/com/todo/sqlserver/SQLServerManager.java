package com.todo.sqlserver;

import com.todo.datamodels.TodoFolder;
import com.todo.properties.MSSqlServerProps;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class SQLServerManager {

    public List<TodoFolder> fetchTodoFolders() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        List<TodoFolder> folders = new ArrayList<>();
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_TODO_FOLDERS); ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    folders.add(new TodoFolder(){{
                        setID(rs.getInt("ID"));
                        setNAME(rs.getString("NAME"));
                        setDESCRIPTION(rs.getString("DESCRIPTION"));
                        setCREATED_ON(rs.getTimestamp("CREATED_ON").toInstant().atZone(TimeZone.getDefault().toZoneId()));
                    }});
                }
            }
        }
        return folders;
    }

    public boolean saveTodoFolder(TodoFolder folder) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        boolean isSaved = false;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        int INDEX = 0;
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_TODO_FOLDERS)){
                ps.setString(++INDEX,folder.getNAME());
                ps.setString(++INDEX, folder.getDESCRIPTION());
                ps.setTimestamp(++INDEX, Timestamp.from(Instant.now()));

                isSaved = ps.executeUpdate() > 0;
            }
        }

        return isSaved;
    }


}
