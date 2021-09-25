package com.todo.responsemodels;

import com.todo.datamodels.Todo;

import java.util.List;

public class FetchTodosResponse {
    private String folderName;
    private List<Todo> todos;

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }
}
