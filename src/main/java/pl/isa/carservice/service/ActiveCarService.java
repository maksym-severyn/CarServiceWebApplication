package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.config.mappers.CarMapper;
import pl.isa.carservice.entity.CarName;
import pl.isa.carservice.entity.dto.CarDto;
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
    private final CarMapper carMapper;
    private final Comparator<CarDto> acceptedDateComparator;

    @Autowired
    public ActiveCarService(@Qualifier("activeCarRepo") CarRepository carRepo,
                            CarMapper carMapper, @Qualifier("acceptedDateComparator") Comparator<CarDto> acceptedDateComparator) {
        this.carRepo = carRepo;
        this.carMapper = carMapper;
        this.acceptedDateComparator = acceptedDateComparator;
    }

    @Override
    public List<CarDto> getAllCarsToDto() {
        return carRepo.returnCarList().stream().map(carMapper::toDto).collect(Collectors.<CarDto>toList());
    }

    /**
     * All fixed cars sorted by accepted date (ascending)
     *
     * @return list of all fixed cars sorted by accepted date (ascending)
     */
    @Override
    public List<CarDto> getAllCarsSorted() {
        List<CarDto> cars = getAllCarsToDto();
        return cars.stream().sorted(acceptedDateComparator.reversed()).collect(Collectors.<CarDto>toList());
    }

    public void addCarToList(CarDto dto) {
        carRepo.addCarToList(carMapper.toEntity(dto));
        carRepo.saveCarListToBase();
    }

    public String isCarAlreadyExists(CarDto dto) {
        if (carRepo.contains(carMapper.toEntity(dto))) {
            return "Car already exists in active list!";
        } else {
            return "";
        }
    }

    public List<CarDto> searchCarsByParam(String param) {
        if (param.isBlank()) {
            return getAllCarsToDto();
        } else {
            String[] params = param.split(" ");
            AtomicReference<List<CarDto>> carsDto = new AtomicReference<>(getAllCarsToDto());
            Arrays.asList(params).forEach(p -> carsDto.set(carsDto.get().stream().filter(carPredicate(p)).collect(Collectors.<CarDto>toList())));
            return carsDto.get();
        }
    }

    @Override
    public List<CarDto> searchCarsByAllParams(String regNumb, CarName name, String model, LocalDate acceptedDate, Integer manufactureYear) {
        List<List<CarDto>> listOfCarLists = Arrays.asList(searchCarsByName(name), searchCarsByModel(model), searchCarsByRegNumb(regNumb), searchCarsByAcceptedDate(acceptedDate), searchCarsByManufactureYear(manufactureYear));
        return listOfCarLists.stream().flatMap(Collection::stream).distinct().collect(Collectors.<CarDto>toList());
    }

    private List<CarDto> searchCarsByName(CarName name) {
        if (name == null) {
            return new ArrayList<>();
        } else {
            var cars = getAllCarsToDto();
            return cars.stream().filter(c -> c.getName().getNameOfCar().toLowerCase().contains(name.getNameOfCar().toLowerCase())).collect(Collectors.<CarDto>toList());
        }
    }

    private List<CarDto> searchCarsByModel(String model) {
        if (model == null) {
            return new ArrayList<>();
        } else {
            var cars = getAllCarsToDto();
            return cars.stream().filter(c -> c.getModel().toLowerCase().contains(model.toLowerCase())).collect(Collectors.<CarDto>toList());
        }
    }

    private List<CarDto> searchCarsByRegNumb(String regNumb) {
        if (regNumb.isBlank()) {
            return new ArrayList<>();
        } else {
            var cars = getAllCarsToDto();
            return cars.stream().filter(c -> c.getRegistrationNumber().toLowerCase().contains(regNumb.toLowerCase())).collect(Collectors.<CarDto>toList());
        }
    }

    private List<CarDto> searchCarsByAcceptedDate(LocalDate acceptedDate) {
        if (acceptedDate == null) {
            return new ArrayList<>();
        } else {
            var cars = getAllCarsToDto();
            return cars.stream().filter(c -> Period.between(c.getCarAcceptedDate(), acceptedDate).getDays() == 0).collect(Collectors.<CarDto>toList());
        }
    }

    private List<CarDto> searchCarsByManufactureYear(Integer manufactureYear) {
        if (manufactureYear == null) {
            return new ArrayList<>();
        } else {
            var cars = getAllCarsToDto();
            return cars.stream().filter(c -> c.getManufactureYear().equals(manufactureYear)).collect(Collectors.<CarDto>toList());
        }
    }

    private Predicate<CarDto> carPredicate(String param) {
        return car -> car.toString().toLowerCase().replaceAll(" ", "").contains(param.toLowerCase().replaceAll(" ", ""));
    }
}