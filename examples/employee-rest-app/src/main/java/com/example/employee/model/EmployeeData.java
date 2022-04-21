package com.example.employee.model;

import java.util.List;

public class EmployeeData {
    private List<Employee> data;

    public EmployeeData() {
    }

    public EmployeeData(List<Employee> data) {
        this.data = data;
    }

    public List<Employee> getData() {
        return data;
    }

    public void setData(List<Employee> employees) {
        data = employees;
    }
}
