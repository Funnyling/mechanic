package dao;

import model.Employee;

import java.util.List;

public interface EmployeeDao {
    List<Employee> findAll();

    List<Employee> findByFullName(String fullName);
}
