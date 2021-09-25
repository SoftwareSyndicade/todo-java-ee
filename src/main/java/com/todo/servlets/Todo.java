package com.todo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.todo.sqlserver.SQLServerManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Todo", value = "/todo")
public class Todo extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int folderID = Integer.parseInt(request.getParameter("folder-id"));
        List<com.todo.datamodels.Todo> todos = new ArrayList<>();
        SQLServerManager manager = new SQLServerManager();
        try {
            todos = manager.fetchTodos(folderID);
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | SQLException e) {
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        out.println(mapper.writeValueAsString(todos));

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int folderID = Integer.parseInt(request.getParameter("folder-id"));
        com.todo.datamodels.Todo todo = new com.todo.datamodels.Todo(){{
           setNAME(request.getParameter("txt-todo-name"));
           setDESCRIPTION(request.getParameter("txt-todo-description"));
           setFOLDER_ID(folderID);
        }};

        SQLServerManager manager = new SQLServerManager();
        try {
            manager.saveTodo(todo);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
