package service;

import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public interface EmployeeService {
    public List<Employee> findAllEmployees() throws EmployeeNotFoundException;
    public abstract void addEmployee(Employee e);
    public abstract void deleteEmployee(int id);
    public abstract Employee findEmployee(int id) throws EmployeeNotFoundException;
    public abstract void updateEmployee(Employee e) throws EmployeeNotFoundException;

}