import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        List<Employee> employeesList = employeeDAO.getAllEmployees();
        System.out.println(employeesList.toString());
        System.out.println(employeeDAO.getEmployeeById(33));
        employeeDAO.createEmployee(new Employee(
                "Eva",
                "Green",
                "female",
                22,
                new City(5, "Chicago")));
        employeeDAO.updateEmployee(new Employee
                (40,
                        "Eva",
                        "Red",
                        "female",
                        32,
                        new City(6, "Washington")));
        employeeDAO.deleteEmployee(employeeDAO.getEmployeeById(40));
    }
}
