package dao;

import pojo.City;
import pojo.Employee;

import javax.persistence.EntityManager;
import java.util.List;

public class CityDAOImpl implements CityDAO {

    @Override
    public void createCity(City city) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        em.persist(city);
        em.getTransaction().commit();
        em.close();
        System.out.println("Добавлен " + city);
    }

    @Override
    public City getCityById(int id) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        City city = em.find(City.class, id);
        em.getTransaction().commit();
        em.close();
        return city;
    }

    @Override
    public List<City> getAllCity() {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        List<City> cities = em.createNativeQuery("SELECT * FROM  city", City.class).getResultList();
        em.getTransaction().commit();
        em.close();
        return cities;
    }

    @Override
    public City updateCity(City city) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        City original = em.find(City.class, city.getCityId());
        if (original != null) {
            original.setCityName(city.getCityName());
            for (Employee employee : original.getEmployeeList()) {
                em.remove(employee);
            }
            original.getEmployeeList().clear();
            for (Employee employee : city.getEmployeeList()) {
                employee.setCity(original);
                original.getEmployeeList().add(employee);
                em.persist(employee);
            }
            em.merge(original);
            em.getTransaction().commit();
            em.close();
        }
        return original;
    }

    @Override
    public void deleteCity(City city) {
        EntityManager em = CreateEntityManager.getEntityManager();
        em.getTransaction().begin();
        City city1 = em.find(City.class, city.getCityId());
        em.remove(city1);
        em.getTransaction().commit();
        em.close();
    }
}
