package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.exception.carnotfound.CarNotFoundException;
import pl.isa.carservice.repo.CarRepository;

import java.time.LocalDate;

@Service
public class FixingService implements MovableFixingService {

    private final CarRepository carRepositoryActive;
    private final CarRepository carRepositoryFixed;

    public FixingService(@Qualifier("activeCarRepo") CarRepository carRepositoryActive,
                         @Qualifier("fixedCarRepo") CarRepository carRepositoryFixed) {
        this.carRepositoryActive = carRepositoryActive;
        this.carRepositoryFixed = carRepositoryFixed;
    }

    @Override
    public void fixCar(String registrationNumber) {
        Car fixed = carRepositoryActive.findCarFromListByRegNumb(registrationNumber);
        fixed.setFixed(true);
        fixed.setDateOfFix(LocalDate.now());
        moveCarBetweenLists(fixed, carRepositoryActive, carRepositoryFixed);
        actualizeBases();
    }

    private void moveCarBetweenLists(Car car, CarRepository source, CarRepository destination) {
        if (source.contains(car) && !(destination.contains(car))) {
            destination.addCarToList(car);
            source.deleteCarFromList(car);
        } else {
            throw new CarNotFoundException(car.getRegistrationNumber());
        }
    }

    private void actualizeBases() {
        carRepositoryActive.saveCarListToBase();
        carRepositoryFixed.saveCarListToBase();
    }
}