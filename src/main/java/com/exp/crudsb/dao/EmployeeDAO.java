package com.exp.crudsb.dao;


import com.exp.crudsb.entity.Employee;

import java.util.List;

public interface EmployeeDAO {

    public List<Employee> findAll();

    public Employee findById(int id);

    public void save(Employee employee);

    public void update(Employee employee);

    public void deleteById(int id);

}
