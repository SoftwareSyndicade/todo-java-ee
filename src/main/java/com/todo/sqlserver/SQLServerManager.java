package com.todo.sqlserver;

import com.todo.datamodels.TodoFolder;
import com.todo.properties.MSSqlServerProps;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                    }});
                }
            }
        }
        return folders;
    }


}
