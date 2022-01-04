package pl.isa.carservice.service;

import pl.isa.carservice.entity.Car;

import java.util.List;

public interface CarService extends MovableService {
    List<Car> getAllCars();
}
