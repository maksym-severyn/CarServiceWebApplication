package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.repo.CarRepository;

import java.util.List;

@Service
public class FixedCarService implements CarService {

    private final CarRepository carRepo;

    @Autowired
    public FixedCarService(@Qualifier("fixedCarRepo") CarRepository carRepo) {
        this.carRepo = carRepo;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.returnCarList();
    }
}
