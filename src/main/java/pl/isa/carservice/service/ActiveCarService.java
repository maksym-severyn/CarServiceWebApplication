package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.comparators.AcceptedDateComparator;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.repo.CarRepository;

import java.util.Collections;
import java.util.List;

@Service
public class ActiveCarService implements CarService {

    private final CarRepository carRepo;
    private final AcceptedDateComparator acceptedDateComparator;

    @Autowired
    public ActiveCarService(@Qualifier("activeCarRepo") CarRepository carRepo, AcceptedDateComparator acceptedDateComparator) {
        this.carRepo = carRepo;
        this.acceptedDateComparator = acceptedDateComparator;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.returnCarList();
    }

    public List<Car> getAllCarsSortedByAcceptedDateAsc() {
        List<Car> cars = getAllCars();
        cars.sort(acceptedDateComparator.reversed());
        return cars;
    }
}
