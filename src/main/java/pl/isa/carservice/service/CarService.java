package pl.isa.carservice.service;

import pl.isa.carservice.entity.Car;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();
    List<Car> getAllCarsSorted();
}
