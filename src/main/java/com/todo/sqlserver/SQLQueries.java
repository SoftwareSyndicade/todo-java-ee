package com.todo.sqlserver;

public class SQLQueries {
    public static final String FETCH_TODO_FOLDERS = "SELECT [ID],[NAME],[DESCRIPTION],[CREATED_ON] FROM TODO_FOLDERS";
    public static final String INSERT_TODO_FOLDERS = "INSERT INTO TODO_FOLDERS(NAME, DESCRIPTION, CREATED_ON) VALUES(?,?,?)";
    public static final String DELETE_TODO_FOLDERS = "DELETE FROM TODO_FOLDERS WHERE ID = ?";
}
