package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.entity.CarName;
import pl.isa.carservice.repo.CarRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ActiveCarService implements ActiveCarServiceInterface {

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
     *
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

    public List<Car> searchCarsByParam(String param) {
        if (param.isBlank()) {
            return carRepo.returnCarList();
        } else {
            String[] params = param.split(" ");
            AtomicReference<List<Car>> cars = new AtomicReference<>(carRepo.returnCarList());
            Arrays.asList(params).forEach(p -> cars.set(cars.get().stream().filter(carPredicate(p)).collect(Collectors.<Car>toList())));
            return cars.get();
        }
    }

    @Override
    public List<Car> searchCarsByAllParams(String regNumb, CarName name, LocalDate acceptedDate, Integer manufactureYear) {
        List<List<Car>> listOfCarLists = Arrays.asList(searchCarsByName(name), searchCarsByRegNumb(regNumb), searchCarsByAcceptedDate(acceptedDate), searchCarsByManufactureYear(manufactureYear));
        return listOfCarLists.stream().flatMap(Collection::stream).distinct().collect(Collectors.<Car>toList());
    }

    private List<Car> searchCarsByName(CarName name) {
        if (name == null) {
            return new ArrayList<>();
        } else {
            var cars = carRepo.returnCarList();
            return cars.stream().filter(c -> c.getName().getNameOfCar().toLowerCase().contains(name.getNameOfCar().toLowerCase())).collect(Collectors.<Car>toList());
        }
    }

    private List<Car> searchCarsByRegNumb(String regNumb) {
        if (regNumb.isBlank()) {
            return new ArrayList<>();
        } else {
            var cars = carRepo.returnCarList();
            return cars.stream().filter(c -> c.getRegistrationNumber().toLowerCase().contains(regNumb.toLowerCase())).collect(Collectors.<Car>toList());
        }
    }

    private List<Car> searchCarsByAcceptedDate(LocalDate acceptedDate) {
        if (acceptedDate == null) {
            return new ArrayList<>();
        } else {
            var cars = carRepo.returnCarList();
            return cars.stream().filter(c -> Period.between(c.getCarAcceptedDate(), acceptedDate).getDays() == 0).collect(Collectors.<Car>toList());
        }
    }

    private List<Car> searchCarsByManufactureYear(Integer manufactureYear) {
        if (manufactureYear == null) {
            return new ArrayList<>();
        } else {
            var cars = carRepo.returnCarList();
            return cars.stream().filter(c -> c.getManufactureYear().equals(manufactureYear)).collect(Collectors.<Car>toList());
        }
    }

    private Predicate<Car> carPredicate(String param) {
        return car -> car.toString().toLowerCase().replaceAll(" ", "").contains(param.toLowerCase().replaceAll(" ", ""));
    }
}