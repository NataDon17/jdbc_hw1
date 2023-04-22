import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        final String user = "postgres";
        final String password = "357951";
        final String url = "jdbc:postgresql://localhost:5432/postgres";
        System.out.println("***** Задание 1 *****");
        try (final Connection connection =
                     DriverManager.getConnection(url, user, password);
             PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee WHERE id=32")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                System.out.println("ID сотрудника: " + idOfEmployee);
                String fNameOfEmployee = resultSet.getString("first_name");
                String lNameOfEmployee = resultSet.getString("last_name");
                String genderOfEmployee = resultSet.getString("gender");
                int cityOfEmployee = resultSet.getInt("city_id");
                System.out.println("Имя: " + fNameOfEmployee);
                System.out.println("Фамилия: " + lNameOfEmployee);
                System.out.println("Пол: " + genderOfEmployee);
                System.out.println("Город: " + cityOfEmployee);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к базе данных!");
            e.printStackTrace();
        }

        System.out.println("***** Задание 2 *****");
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        employeeDAO.createEmployee(new Employee(
                1,
                "Eva",
                "Green",
                "female",
                22,
                new City(5, "Chicago")));
        System.out.println(employeeDAO.getEmployeeById(33));
        List<Employee> employeesList = employeeDAO.getAllEmployees();
        System.out.println(employeesList.toString());
        employeeDAO.updateEmployee(1, new Employee(
                1,
                "Eva",
                "Red",
                "female",
                32,
                new City(6, "Washington")));
        employeeDAO.deleteEmployee(1);
    }
}
