package com.todo.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.base.Splitter;
import com.todo.sqlserver.SQLServerManager;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "TodoFolder", value = "/folder")
public class TodoFolder extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        // load all folders.
        response.setContentType("text/html");
        SQLServerManager manager = new SQLServerManager();
        List<com.todo.datamodels.TodoFolder> folders = new ArrayList<>();
        try {
            folders = manager.fetchTodoFolders();

            PrintWriter out = response.getWriter();
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

            var test = mapper.writeValueAsString(folders);
            out.println(mapper.writeValueAsString(folders));

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
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        resp.setContentType("text/html");
        boolean isDeleted = false;
        var id = getParameters(req).get("folder-id");
        int folderID = Integer.parseInt(id);
        SQLServerManager manager = new SQLServerManager();
        try {
            isDeleted = manager.deleteTodoFolder(folderID);
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

        PrintWriter out = resp.getWriter();
        out.println(isDeleted);
    }

    private Map<String, String> getParameters(HttpServletRequest request)
    {
        Map<String, String> parametersMap = new HashMap<>();
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(request.getInputStream());
            BufferedReader br = new BufferedReader(reader);
            String data = br.readLine();

            parametersMap = Splitter.on('&')
                    .trimResults()
                    .withKeyValueSeparator(
                            Splitter.on('=')
                                    .limit(2)
                                    .trimResults())
                    .split(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parametersMap;
    }
}
