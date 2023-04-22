import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {
    final String user = "postgres";
    final String password = "357951";
    final String url = "jdbc:postgresql://localhost:5432/postgres";
    final Connection connection;

    public EmployeeDAOImpl() throws SQLException {
        this.connection = DriverManager.getConnection(url, user, password);
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee> employeesList = new ArrayList<>();
        try (PreparedStatement statement =
                     connection.prepareStatement("SELECT * FROM employee " +
                             "LEFT JOIN city ON employee.city_id = city.city_id")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt("id");
                String fNameOfEmployee = resultSet.getString("first_name");
                String lNameOfEmployee = resultSet.getString("last_name");
                String genderOfEmployee = resultSet.getString("gender");
                int ageOfEmployee = resultSet.getInt("age");
                City city = new City
                        (resultSet.getInt("city_id"),
                                resultSet.getString("city_name"));
                employeesList.add(new Employee
                        (idOfEmployee,
                                fNameOfEmployee,
                                lNameOfEmployee,
                                genderOfEmployee,
                                ageOfEmployee,
                                city));
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД!");
            e.printStackTrace();
        }
        return employeesList;
    }

    @Override
    public Employee getEmployeeById(int id) {
        Employee employee = new Employee();
        try (PreparedStatement statement = connection.prepareStatement
                ("SELECT * FROM employee " +
                        "LEFT JOIN city ON employee.city_id = city.city_id WHERE id=?")) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int idOfEmployee = resultSet.getInt(1);
                String fNameOfEmployee = resultSet.getString(2);
                String lNameOfEmployee = resultSet.getString(3);
                String genderOfEmployee = resultSet.getString(4);
                int ageOfEmployee = resultSet.getInt(5);
                City cityOfEmployee = new City
                        (resultSet.getInt("city_id"),
                                resultSet.getString("city_name"));
                employee = new Employee
                        (idOfEmployee,
                                fNameOfEmployee,
                                lNameOfEmployee,
                                genderOfEmployee,
                                ageOfEmployee,
                                cityOfEmployee);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД!");
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public void createEmployee(Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement
                ("INSERT INTO employee(id,first_name, last_name, gender, age,city_id) " +
                        "VALUES (?,?,?,?,?,?)")) {
            statement.setInt(1, employee.getId());
            statement.setString(2, employee.getFirstName());
            statement.setString(3, employee.getLastName());
            statement.setString(4, employee.getGender());
            statement.setInt(5, employee.getAge());
            statement.setInt(6, employee.getCity().getCityId());
            statement.executeUpdate();
            System.out.println("Добавлен " + employee);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД!");
            e.printStackTrace();
        }
    }

    @Override
    public void updateEmployee(int id, Employee employee) {
        try (PreparedStatement statement = connection.prepareStatement
                ("UPDATE employee SET first_name=?, last_name=?, gender=?, age=?,city_id=? WHERE id=?")) {
            statement.setString(1, employee.getFirstName());
            statement.setString(2, employee.getLastName());
            statement.setString(3, employee.getGender());
            statement.setInt(4, employee.getAge());
            statement.setInt(5, employee.getCity().getCityId());
            statement.setInt(6, employee.getId());
            statement.executeUpdate();
            System.out.println("Данные сотрудника с id=" + id + " изменены.\n" + employee);
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД!");
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEmployee(int id) {
        try (PreparedStatement statement = connection.prepareStatement
                ("DELETE FROM employee WHERE id=(?)")) {
            statement.setInt(1, id);
            statement.executeUpdate();
            System.out.println("Сотрудник с id=" + id + " удален.");
        } catch (SQLException e) {
            System.out.println("Ошибка при подключении к БД!");
            e.printStackTrace();
        }
    }
}
