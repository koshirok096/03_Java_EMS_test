/*
 * @author kubilaycakmak
 * @date Nov 02, 2022
 * @version 1.0
 */
 
package exception;

public class EmployeeNotFoundException extends Exception {
    
    private int id;

    public EmployeeNotFoundException(int id){
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
        return "EmployeeNotFoundException [id = " + id + "]";
    }

    
}
