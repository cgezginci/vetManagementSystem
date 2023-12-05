package dev.patika.vetManagementSystem.core.result;

import lombok.Data;

import java.util.List;


public class ListResult<T> extends Result{
    private List<T> data;

    public ListResult(boolean status, String message, String code , List<T> data) {
        super(status, message, code);
        this.data = data;

    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
