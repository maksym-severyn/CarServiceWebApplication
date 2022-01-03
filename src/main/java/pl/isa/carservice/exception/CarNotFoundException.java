package pl.isa.carservice.exception;

import pl.isa.carservice.entity.Car;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String registrationNumber) {
        super("Car could not found: " + registrationNumber);
    }
}
