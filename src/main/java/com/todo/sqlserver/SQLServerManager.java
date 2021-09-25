package com.todo.sqlserver;

import com.todo.datamodels.Todo;
import com.todo.datamodels.TodoFolder;
import com.todo.properties.MSSqlServerProps;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.time.Duration;
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

                        Instant modifiedON = rs.getTimestamp("CREATED_ON").toInstant();

                        setID(rs.getInt("ID"));
                        setNAME(rs.getString("NAME"));
                        setDESCRIPTION(rs.getString("DESCRIPTION"));
                        setCREATED_ON(rs.getTimestamp("CREATED_ON").toInstant().atZone(TimeZone.getDefault().toZoneId()));
                        setUPDATE_DAYS(Duration.between(modifiedON, Instant.now()).toDays());
                        setMODIFIED_ON(modifiedON.atZone(TimeZone.getDefault().toZoneId()));
                    }});
                }
            }
        }
        return folders;
    }

    public TodoFolder fetchTodoFolder(int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        TodoFolder folder = null;
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_TODO_FOLDER)){

                ps.setInt(++INDEX, folderID);
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()){

                        folder = new TodoFolder(){{
                            setID(rs.getInt("ID"));
                            setNAME(rs.getString("NAME"));
                            setDESCRIPTION(rs.getString("DESCRIPTION"));
                            setCREATED_ON(rs.getTimestamp("CREATED_ON").toInstant().atZone(TimeZone.getDefault().toZoneId()));
                        }};
                    }
                }
            }
        }
        return folder;
    }

    public boolean saveTodoFolder(TodoFolder folder) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        boolean isSaved = false;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        int INDEX = 0;
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_TODO_FOLDERS)){

                Timestamp timestamp = Timestamp.from(Instant.now());

                ps.setString(++INDEX,folder.getNAME());
                ps.setString(++INDEX, folder.getDESCRIPTION());
                ps.setTimestamp(++INDEX, timestamp);
                ps.setTimestamp(++INDEX, timestamp);

                isSaved = ps.executeUpdate() > 0;
            }
        }

        return isSaved;
    }

    public boolean updateTodoFolderDate(int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        boolean isUpdated = false;

        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)) {
            try (PreparedStatement ps = conn.prepareStatement(SQLQueries.UPDATE_TODO_FOLDERS_DATE)) {
                ps.setTimestamp(++INDEX, Timestamp.from(Instant.now()));
                ps.setInt(++INDEX ,folderID);

                isUpdated = ps.executeUpdate() > 0;
            }
        }
        return isUpdated;
    }

    public boolean deleteTodoFolder(int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException {
        boolean idDeleted = false;
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.DELETE_TODO_FOLDERS)){
                ps.setInt(++INDEX, folderID);

                idDeleted = ps.executeUpdate() > 0;
            }
        }
        return idDeleted;
    }

    public List<Todo> fetchTodos(int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        List<Todo> todos = new ArrayList<>();
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.FETCH_TODOS)){
                ps.setInt(++INDEX, folderID);

                try(ResultSet rs = ps.executeQuery()) {
                    while (rs.next()){
                        todos.add(new Todo(){{
                            setID(rs.getInt("ID"));
                            setNAME(rs.getString("NAME"));
                            setDESCRIPTION(rs.getString("DESCRIPTION"));
                            setFOLDER_ID(rs.getInt("FOLDER_ID"));
                            setIS_COMPLETE(rs.getBoolean("IS_COMPLETE"));
                            setCREATED_ON(rs.getTimestamp("CREATED_ON").toInstant().atZone(TimeZone.getDefault().toZoneId()));
                            setMODIFIED_ON(rs.getTimestamp("MODIFIED_ON").toInstant().atZone(TimeZone.getDefault().toZoneId()));
                        }});
                    }
                }
            }
        }

        return todos;
    }

    public boolean saveTodo(Todo todo) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        boolean isSaved = false;
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.INSERT_TODO)){
                Timestamp timestamp = Timestamp.from(Instant.now());

                ps.setString(++INDEX,todo.getNAME());
                ps.setString(++INDEX, todo.getDESCRIPTION());
                ps.setTimestamp(++INDEX, timestamp);
                ps.setTimestamp(++INDEX, timestamp);
                ps.setInt(++INDEX, todo.getFOLDER_ID());

                isSaved = ps.executeUpdate() > 0;
            }
        }
        return isSaved;
    }

    public boolean deleteTodo(int todoID, int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        boolean isDeleted = false;
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)){
            try(PreparedStatement ps = conn.prepareStatement(SQLQueries.DELETE_TODO)){
                ps.setInt(++INDEX, todoID);
                ps.setInt(++INDEX, folderID);

                isDeleted = ps.executeUpdate() > 0;
            }
        }
        return isDeleted;
    }

    public boolean completeTodo(int todoID, int folderID) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, SQLException{
        boolean isComplete = false;
        int INDEX = 0;
        Class.forName(MSSqlServerProps.DB_DRIVER).getDeclaredConstructor().newInstance();
        try(Connection conn = DriverManager.getConnection(MSSqlServerProps.CONNECTION_STRING)) {
            try (PreparedStatement ps = conn.prepareStatement(SQLQueries.COMPLETE_TODO)) {
                ps.setBoolean(++INDEX,true);
                ps.setInt(++INDEX, todoID);
                ps.setInt(++INDEX, folderID);

                isComplete = ps.executeUpdate() > 0;
            }
        }
        return isComplete;
    }

}
