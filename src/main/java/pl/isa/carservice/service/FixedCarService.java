package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.repo.CarRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class FixedCarService implements CarService {

    private final CarRepository carRepo;
    private final Comparator<Car> fixDateComparator;

    @Autowired
    public FixedCarService(@Qualifier("fixedCarRepo") CarRepository carRepo,
                           @Qualifier("fixDateComparator") Comparator<Car> fixDateComparator) {
        this.carRepo = carRepo;
        this.fixDateComparator = fixDateComparator;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.returnCarList();
    }

    public List<Car> getAllCarsSortedByFixDateDesc() {
        List<Car> cars = getAllCars();
        cars.sort(fixDateComparator);
        return cars;
    }
}
