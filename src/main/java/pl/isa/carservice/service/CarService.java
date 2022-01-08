package pl.isa.carservice.service;

import pl.isa.carservice.entity.dto.CarDto;

import java.util.List;

public interface CarService {
    List<CarDto> getAllCarsToDto();
    List<CarDto> getAllCarsSorted();
}
