import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public List<Employee> getAllEmployees() {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        TypedQuery<Employee> query = em.createQuery("SELECT employee from Employee employee", Employee.class);
        List<Employee> employeeList = query.getResultList();
        em.getTransaction().commit();
        em.close();
        return employeeList;
    }

    @Override
    public Employee getEmployeeById(int id) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        Employee employee = em.find(Employee.class, id);
        em.getTransaction().commit();
        em.close();
        return employee;
    }

    @Override
    public void createEmployee(Employee employee) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        em.persist(employee);
        em.getTransaction().commit();
        em.close();
        System.out.println("Добавлен " + employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        em.find(Employee.class, employee.getId());
        employee.setFirstName(employee.getFirstName());
        employee.setLastName(employee.getLastName());
        employee.setGender(employee.getGender());
        employee.setAge(employee.getAge());
        employee.setCity(employee.getCity());
        em.merge(employee);
        em.getTransaction().commit();
        em.close();
        System.out.println("Данные сотрудника с id=" + employee.getId() + " изменены.\n" + employee);
    }

    @Override
    public void deleteEmployee(Employee employee) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        Employee employee1 = em.find(Employee.class, employee.getId());
        em.remove(employee1);
        em.getTransaction().commit();
        em.close();
        CreateEntityManager.emf.close();
        System.out.println("Сотрудник с id=" + employee.getId() + " удален.");
    }
}
