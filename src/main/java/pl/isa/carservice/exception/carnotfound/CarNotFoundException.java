package pl.isa.carservice.exception.carnotfound;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String registrationNumber) {
        super("Car could not found: " + registrationNumber);
    }
}