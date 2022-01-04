package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.repo.CarRepository;

import java.time.LocalDate;

@Service
public class FixingCarService implements MovableService {

    private final CarRepository carRepositoryActive;
    private final CarRepository carRepositoryFixed;

    public FixingCarService(@Qualifier("activeCarRepo") CarRepository carRepositoryActive,
                            @Qualifier("fixedCarRepo") CarRepository carRepositoryFixed) {
        this.carRepositoryActive = carRepositoryActive;
        this.carRepositoryFixed = carRepositoryFixed;
    }

    public boolean fixCar(String registrationNumber) {
        Car fixed = carRepositoryActive.findCarFromListByRegNumb(registrationNumber);
        fixed.setFixed(true);
        fixed.setDateOfFix(LocalDate.now());
        return moveCarBetweenLists(fixed, carRepositoryActive, carRepositoryFixed);
    }

    private boolean moveCarBetweenLists(Car car, CarRepository source, CarRepository destination) {
        if (source.contains(car) && !(destination.contains(car))) {
            destination.addCarToList(car);
            source.deleteCarFromList(car);
            actualizeBases();
            return true;
        } else {
            return false;
        }
    }

    private void actualizeBases() {
        carRepositoryActive.saveCarListToBase();
        carRepositoryFixed.saveCarListToBase();
    }
}
