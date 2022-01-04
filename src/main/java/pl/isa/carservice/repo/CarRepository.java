package pl.isa.carservice.repo;

import pl.isa.carservice.entity.Car;

public interface CarRepository {
    void getCarListFromBase();
    void saveCarListToBase();
    Car findCarFromListByRegNumb(String registrationNumber);
    void deleteCarFromList(Car car);
    void addCarToList(Car car);
    boolean contains(Car car);
}