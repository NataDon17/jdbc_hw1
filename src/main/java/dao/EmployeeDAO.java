package dao;

import pojo.Employee;

import java.util.List;

public interface EmployeeDAO {
    void createEmployee(Employee employee);

    Employee getEmployeeById(int id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Employee employee);
}
