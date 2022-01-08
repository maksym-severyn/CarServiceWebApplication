package pl.isa.carservice.service;

import pl.isa.carservice.entity.CarName;
import pl.isa.carservice.entity.dto.CarDto;

import java.time.LocalDate;
import java.util.List;

public interface ActiveCarServiceInterface extends CarService {
    List<CarDto> searchCarsByParam(String param);
    List<CarDto> searchCarsByAllParams(String regNumb, CarName name, String model, LocalDate acceptedDate, Integer manufactureYear);
}