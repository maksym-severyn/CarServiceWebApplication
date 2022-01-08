package pl.isa.carservice.service;

import pl.isa.carservice.entity.Car;
import pl.isa.carservice.entity.CarName;

import java.time.LocalDate;
import java.util.List;

public interface ActiveCarServiceInterface extends CarService {
    List<Car> searchCarsByParam(String param);
    List<Car> searchCarsByAllParams(String regNumb, CarName name, LocalDate acceptedDate, Integer manufactureYear);
}
