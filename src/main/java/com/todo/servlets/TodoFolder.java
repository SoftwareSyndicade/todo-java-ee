package com.todo.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
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

@WebServlet(name = "TodoFolder", value = "/folder")
public class TodoFolder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // load all folders.
        response.setContentType("text/html");
        SQLServerManager manager = new SQLServerManager();
        List<com.todo.datamodels.TodoFolder> folders = new ArrayList<>();
        try {
            folders = manager.fetchTodoFolders();
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

        PrintWriter out = response.getWriter();
        ObjectMapper mapper = new ObjectMapper();
        out.println(mapper.writeValueAsString(folders));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        com.todo.datamodels.TodoFolder folder = new com.todo.datamodels.TodoFolder();
        folder.setNAME(request.getParameter("txt-folder-name"));
        folder.setDESCRIPTION(request.getParameter("txt-folder-description"));
        boolean isSaved = false;
        SQLServerManager manager = new SQLServerManager();
        try {
            isSaved = manager.saveTodoFolder(folder);
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

        PrintWriter out = response.getWriter();
        out.println(isSaved);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
