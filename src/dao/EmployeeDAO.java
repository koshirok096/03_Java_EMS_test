/*
 * @author kubilaycakmak
 * @date Nov 02, 2022
 * @version 1.0
 */
 
package dao;

import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public interface EmployeeDAO {

    // Credentials
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/employee_database";
    public static final String USER = "root";
    public static final String PASSWORD = "Asch0snYVtQr";

    public List<Employee> findAllEmployees() throws EmployeeNotFoundException;
    public abstract void addEmployee(Employee e);
    public abstract void deleteEmployee(int id);
    public abstract Employee findEmployee(int id) throws EmployeeNotFoundException;

    public abstract void updateEmployee(Employee e);
    // public abstract void updateEmployee(int id);
}
