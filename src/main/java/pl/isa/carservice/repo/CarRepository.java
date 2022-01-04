package pl.isa.carservice.repo;

import pl.isa.carservice.entity.Car;

import java.util.List;

public interface CarRepository {
    void getCarListFromBase();
    List<Car> returnCarList();
    void saveCarListToBase();
    Car findCarFromListByRegNumb(String registrationNumber);
    void deleteCarFromList(Car car);
    void addCarToList(Car car);
    boolean contains(Car car);
}