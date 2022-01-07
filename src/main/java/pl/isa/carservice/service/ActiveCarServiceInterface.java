package pl.isa.carservice.service;

import pl.isa.carservice.entity.Car;

import java.util.List;

public interface ActiveCarServiceInterface extends CarService {
    List<Car> searchCarsByParam(String param);
}
