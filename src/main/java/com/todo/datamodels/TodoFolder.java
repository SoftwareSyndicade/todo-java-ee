package com.todo.datamodels;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Objects;

public class TodoFolder {
    private int ID;
    private String NAME;
    private String DESCRIPTION;
    private ZonedDateTime CREATED_ON;

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

    public ZonedDateTime getCREATED_ON() {
        return CREATED_ON;
    }

    public void setCREATED_ON(ZonedDateTime CREATED_ON) {
        this.CREATED_ON = CREATED_ON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoFolder that = (TodoFolder) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID);
    }
}
