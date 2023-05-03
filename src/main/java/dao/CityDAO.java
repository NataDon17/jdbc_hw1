package dao;

import pojo.City;

import java.util.List;

public interface CityDAO {
    void createCity(City city);

    City getCityById(int id);

    List<City> getAllCity();

    City updateCity(City city);

    void deleteCity(City city);
}
