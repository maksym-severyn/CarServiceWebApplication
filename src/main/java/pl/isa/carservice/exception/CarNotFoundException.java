package pl.isa.carservice.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String registrationNumber) {
        super("Car could not found: " + registrationNumber);
    }
}
