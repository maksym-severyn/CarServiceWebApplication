package pl.isa.carservice.config.mappers;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.entity.dto.CarDto;

@Component
public class CarMapper {
    public CarDto toDto(Car entity) {
        CarDto dto = new CarDto();
        dto.setRegistrationNumber(entity.getRegistrationNumber());
        dto.setName(entity.getName());
        dto.setModel(entity.getModel());
        dto.setManufactureYear(entity.getManufactureYear());
        dto.setCarAcceptedDate(entity.getCarAcceptedDate());
        dto.setFixed(entity.isFixed());
        dto.setDateOfFix(entity.getDateOfFix());
        return dto;
    }

    public Car toEntity(CarDto dto) {
        Car entity = new Car();
        entity.setRegistrationNumber(dto.getRegistrationNumber());
        entity.setName(dto.getName());
        entity.setModel(dto.getModel());
        entity.setManufactureYear(dto.getManufactureYear());
        entity.setCarAcceptedDate(dto.getCarAcceptedDate());
        entity.setFixed(dto.isFixed());
        entity.setDateOfFix(dto.getDateOfFix());
        return entity;
    }
}