import dao.*;
import pojo.City;
import pojo.Employee;

import java.sql.*;
import java.util.List;

public class Application {
    public static void main(String[] args) throws SQLException {
        EmployeeDAO employeeDAO = new EmployeeDAOImpl();
        CityDAO cityDAO = new CityDAOImpl();

        City city1 = new City("Dallas", List.of());
        City city2 = new City("Houston", List.of());
        cityDAO.createCity(city1);
        cityDAO.createCity(city2);
        System.out.println(cityDAO.getAllCity().toString());
        Employee employee1 = new Employee(
                "Chris",
                "Rea",
                "male",
                58,
                city1);
        Employee employee2 = new Employee(
                "Lilly",
                "Benet",
                "female",
                30,
                city1);
        city1.setEmployeeList(List.of(employee1, employee2));
        City updateCity1 = cityDAO.updateCity(city1);
        System.out.println(updateCity1.getEmployeeList());

        Employee employeeNew = employeeDAO.updateEmployee(new Employee(
                employee2.getId(),
                employee2.getFirstName(),
                employee2.getLastName(),
                employee2.getGender(),
                employee2.getAge(),
                city2
        ));

        city2.setEmployeeList(List.of(employeeNew));
        System.out.println(city2.getEmployeeList());
        System.out.println(cityDAO.getCityById(updateCity1.getCityId()).getCityName());
        cityDAO.deleteCity(cityDAO.getCityById(53));

        CreateEntityManager.emf.close();
    }
}
