## EMPLOYEE MANAGEMENT SYSTEM

### Keywords;

`DTO` is an abbreviation for `Data Transfer Object`, so it is used to transfer the data between classes and modules of your application.

DTO should only contain private fields for your data, getters, setters, and constructors.
DTO is not recommended to add business logic methods to such classes, but it is OK to add some util methods.

`DAO` is an abbreviation for `Data Access Object`, so it should encapsulate the logic for retrieving, saving and updating data in your data storage (a database, a file-system, whatever).

``` REFERANCE FROM ``` https://stackoverflow.com/a/14366441

util: Utility

### Java Code;

First create DTO package and create Employee class in it.

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package dto;

public class Employee {
    
    private int id;
    private String name;
    private String department;
    private int daysAbsent;

    public Employee(){}
    
    public Employee(int id, String name, String department, int daysAbsent) {
        super();
        this.id = id;
        this.name = name;
        this.department = department;
        this.daysAbsent = daysAbsent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getDaysAbsent() {
        return daysAbsent;
    }

    public void setDaysAbsent(int daysAbsent) {
        this.daysAbsent = daysAbsent;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", daysAbsent=" + daysAbsent + "]";
    }

}

```
Then, we are preparing the DB modal for our project. We will create a table named `employee_table` in our database. The table will have 4 columns: `id`, `name`, `department`, `daysAbsent`.

```sql

CREATE DATABASE ems;

CREATE TABLE employee_table (id INT AUTO_INCREMENT, name varchar(45), department varchar(45), daysAbsent INT, primary key(id));

```

EmployeeDAO.java

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package dao;

import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public interface EmployeeDAO {

    public static final String URL = "jdbc:mysql://localhost:3306/ems"; 
    public static final String USERNAME = "root";
    public static final String PASSWORD = "toortoor";
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // second, after service layer
    public List<Employee> findAllEmployees() throws EmployeeNotFoundException;
    
    // first
    public abstract void addEmployee(Employee employee);
    public abstract void deleteEmployee(int id);
    public abstract void updateEmployee(Employee employee, int id);
    public abstract Employee findEmployee(int id) throws EmployeeNotFoundException;

}

```

Exception package

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package exception;

public class EmployeeNotFoundException extends Exception {

    private int id;
    
    public EmployeeNotFoundException(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "EmployeeNotFoundException [id=" + id + "]";
    }
    
}

```

EmployeeDAOImpl.java

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public class EmployeeDAOMysqlimpl implements EmployeeDAO {

    private Connection connnection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    // performing CRUD operations 
    // Create, Read, Update, Delete 
    // CRUD operations are performed using SQL statements

    private static final String INSERT_EMPLOYEE = "INSERT INTO employee_table (name, department, daysAbsent) VALUES(?, ?, ?)";
    private static final String DELETE_EMPLOYEE = "DELETE FROM employee_table WHERE id = ?";
    // private static final String UPDATE_EMPLOYEE = "UPDATE employee_table SET name = ?, department = ?, daysAbsent = ? WHERE id = ?"; ASSIGNMENT
    private static final String FIND_EMPLOYEE = "SELECT * FROM employee_table WHERE id = ?";
    private static final String FIND_ALL_EMPLOYEES = "SELECT * FROM employee_table";

    public EmployeeDAOMysqlimpl() {
        try {
            connnection = DriverManager.getConnection(EmployeeDAO.URL, EmployeeDAO.USERNAME, EmployeeDAO.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Unable to connect to the database");
            e.printStackTrace();
        }
    }


    @Override
    public void addEmployee(Employee employee) {

        int rowAffected = 0;

        try {
            preparedStatement = connnection.prepareStatement(INSERT_EMPLOYEE);
            preparedStatement.setString(1, employee.getName());
            preparedStatement.setString(2, employee.getDepartment());
            preparedStatement.setInt(3, employee.getDaysAbsent());

            rowAffected = preparedStatement.executeUpdate();

            System.out.println(rowAffected + " row(s) affected");

        } catch (SQLException e) {
            System.out.println("Unable to add the employee");
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                System.out.println("Unable to close the statement");
                e.printStackTrace();
            }
        }

        if(rowAffected > 0) {
            System.out.println("Employee added successfully");
        } 
        
    }

    @Override
    public void deleteEmployee(int id) {

        int rowAffected = 0;

        try {
            preparedStatement = connnection.prepareStatement(DELETE_EMPLOYEE);
            preparedStatement.setInt(1, id);

            rowAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Unable to delete the employee with id: " + id);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(rowAffected > 0) {
            System.out.println("Employee with id: " + id + " is deleted");
        }
    }

    @Override
    public void updateEmployee(Employee employee, int id) {

        // ASSIGNMENT
        // int rowAffected = 0; 

        // try {
        //     preparedStatement = connnection.prepareStatement(UPDATE_EMPLOYEE);
        //     preparedStatement.setString(1, employee.getName());
        //     preparedStatement.setString(2, employee.getDepartment());
        //     preparedStatement.setInt(3, employee.getDaysAbsent());
        //     preparedStatement.setInt(4, id);

        //     rowAffected = preparedStatement.executeUpdate();

        // } catch (SQLException e) {
        //     System.out.println("Unable to update the employee with id: " + id);
        //     e.printStackTrace();
        // } finally {
        //     try {
        //         preparedStatement.close();
        //     } catch (SQLException e) {
        //         e.printStackTrace();
        //     }
        // }

        // if(rowAffected > 0) {
        //     System.out.println("Employee with id: " + id + " is updated");
        // } 
        
    }

    @Override
    public Employee findEmployee(int id) throws EmployeeNotFoundException {
        
        Employee employee = null;

        try {
            preparedStatement = connnection.prepareStatement(FIND_EMPLOYEE);
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();

            if(!resultSet.next()) {
                throw new EmployeeNotFoundException(id);
            }

            employee = new Employee();
            employee.setId(resultSet.getInt("id"));
            employee.setName(resultSet.getString("name"));
            employee.setDepartment(resultSet.getString("department"));
            employee.setDaysAbsent(resultSet.getInt("daysAbsent"));

        } catch (SQLException e) {
            System.out.println("Unable to find the employee with id: " + id);
        } finally {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return employee;
    }


    // second, after service layer
    @Override
    public List<Employee> findAllEmployees() throws EmployeeNotFoundException {

        Employee sEmployee = null;
        List<Employee> employees = new LinkedList<>();
        
        try {
            preparedStatement = connnection.prepareStatement(FIND_ALL_EMPLOYEES);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                sEmployee = new Employee();
                sEmployee.setId(resultSet.getInt("id"));
                sEmployee.setName(resultSet.getString("name"));
                sEmployee.setDepartment(resultSet.getString("department"));
                sEmployee.setDaysAbsent(resultSet.getInt("daysAbsent"));
                employees.add(sEmployee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
                resultSet.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return employees;
    }
    
}
```

**EmployeeService.java**

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package service;

import java.util.List;

import dto.Employee;
import exception.EmployeeNotFoundException;

public interface EmployeeService {
    
    public List<Employee> findAllEmployees() throws EmployeeNotFoundException;

    public abstract void addEmployee(Employee employee);
    public abstract void deleteEmployee(int id);
    public abstract void updateEmployee(Employee employee, int id);
    public abstract Employee findEmployee(int id) throws EmployeeNotFoundException;
}

```

**EmployeeServiceImpl.java**

```java

/*
 * @author kubilaycakmak
 * @date Oct 31, 2022
 * @version 1.0
 */
 
package service;

import java.util.List;

import dao.EmployeeDAO;
import dao.EmployeeDAOMysqlimpl;
import dto.Employee;
import exception.EmployeeNotFoundException;

public class EmployeeServiceimpl implements EmployeeService {

    public EmployeeDAO employeeDAO = new EmployeeDAOMysqlimpl();

    @Override
    public void addEmployee(Employee employee) {
        employeeDAO.addEmployee(employee);
    }

    @Override
    public void deleteEmployee(int id) {
        employeeDAO.deleteEmployee(id);
    }

    @Override
    public void updateEmployee(Employee employee, int id) {
        employeeDAO.updateEmployee(employee, id);
    }

    @Override
    public Employee findEmployee(int id) throws EmployeeNotFoundException {
        return employeeDAO.findEmployee(id);
    }

    @Override
    public List<Employee> findAllEmployees() throws EmployeeNotFoundException {
        return employeeDAO.findAllEmployees();
    }

}

```

App.java

```java

import java.util.List;

import dto.Employee;
import service.EmployeeService;
import service.EmployeeServiceimpl;

public class App {
    public static void main(String[] args) throws Exception {

        EmployeeService employeeService = new EmployeeServiceimpl();

        Employee employee = new Employee();
        employee.setName("Kubilay");
        employee.setDepartment("WMAD Instructor");
        employee.setDaysAbsent(0);

        Employee employee2 = new Employee();
        employee2.setName("Kubilay");
        employee2.setDepartment("UI/IX Instructor");
        employee2.setDaysAbsent(2);

        // employeeService.addEmployee(employee);

        // employeeService.deleteEmployee(1);

        // employeeService.updateEmployee(employee2, 2); ASSIGNMENT

        List<Employee> employees = employeeService.findAllEmployees();

        for (Employee e : employees) {
            System.out.println("Employee ID: " + e.getId() + " | " + " Employee Name: " + e.getName() + " | " + " Employee Department: "
                    + e.getDepartment() + " | " + " Employee Days Absent: " + e.getDaysAbsent());
        }

        // try {
        //     System.out.println(employeeService.findEmployee(2));
        // } catch (EmployeeNotFoundException e) {
        //     e.printStackTrace();
        // }

        

    }
}

```