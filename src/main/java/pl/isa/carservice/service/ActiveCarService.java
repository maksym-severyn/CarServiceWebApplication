package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.repo.CarRepository;

import java.util.Comparator;
import java.util.List;

@Service
public class ActiveCarService implements CarService {

    private final CarRepository carRepo;
    private final Comparator<Car> acceptedDateComparator;

    @Autowired
    public ActiveCarService(@Qualifier("activeCarRepo") CarRepository carRepo,
                            @Qualifier("acceptedDateComparator") Comparator<Car> acceptedDateComparator) {
        this.carRepo = carRepo;
        this.acceptedDateComparator = acceptedDateComparator;
    }

    @Override
    public List<Car> getAllCars() {
        return carRepo.returnCarList();
    }

    /**
     * All fixed cars sorted by accepted date (ascending)
     * @return list of all fixed cars sorted by accepted date (ascending)
     */
    @Override
    public List<Car> getAllCarsSorted() {
        List<Car> cars = getAllCars();
        cars.sort(acceptedDateComparator.reversed());
        return cars;
    }

    public void addCarToList(Car car) {
        carRepo.addCarToList(car);
        carRepo.saveCarListToBase();
    }
}
