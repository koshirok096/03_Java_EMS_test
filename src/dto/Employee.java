/*
 * @author kubilaycakmak
 * @date Nov 02, 2022
 * @version 1.0
 */
 
package dto;

public class Employee {
    
    private int id;
    private String name;
    private String department;
    private int dayAbsent;
    private int salary;

    public Employee(){}

    public Employee(int id, String name, String department, int dayAbsent, int salary){
        this.id = id;
        this.name = name;
        this.department = department;
        this.dayAbsent = dayAbsent;
        this.salary = salary;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDepartment(){
        return department;
    }

    public void setDepartment(String department){
        this.department = department;
    }

    public int getDayAbsent() {
        return dayAbsent;
    }

    public void setDayAbsent(int dayAbsent) {
        this.dayAbsent = dayAbsent;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", dayAbsent=" + dayAbsent
                + ", salary=" + salary + "]";
    }

}
