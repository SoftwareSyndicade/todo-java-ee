package com.todo.datamodels;

import java.time.ZonedDateTime;
import java.util.Objects;

public class Todo {
    private int ID;
    private String NAME;
    private String DESCRIPTION;
    private int FOLDER_ID;
    private ZonedDateTime CREATED_ON;
    private ZonedDateTime MODIFIED_ON;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public String getDESCRIPTION() {
        return DESCRIPTION;
    }

    public void setDESCRIPTION(String DESCRIPTION) {
        this.DESCRIPTION = DESCRIPTION;
    }

    public int getFOLDER_ID() {
        return FOLDER_ID;
    }

    public void setFOLDER_ID(int FOLDER_ID) {
        this.FOLDER_ID = FOLDER_ID;
    }

    public ZonedDateTime getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(ZonedDateTime CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    public ZonedDateTime getMODIFIED_ON() {
        return MODIFIED_ON;
    }

    public void setMODIFIED_ON(ZonedDateTime MODIFIED_ON) {
        this.MODIFIED_ON = MODIFIED_ON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return ID == todo.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
