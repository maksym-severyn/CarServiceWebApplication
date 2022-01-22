package pl.isa.carservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.isa.carservice.config.mappers.CarMapper;
import pl.isa.carservice.entity.dto.CarDto;
import pl.isa.carservice.repo.CarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FixedCarService implements CarService {

    private final CarRepository carRepo;
    private final CarMapper carMapper;
    private final Comparator<CarDto> fixDateComparator;

    @Autowired
    public FixedCarService(@Qualifier("fixedCarRepo") CarRepository carRepo,
                           CarMapper carMapper, @Qualifier("fixDateComparator") Comparator<CarDto> fixDateComparator) {
        this.carRepo = carRepo;
        this.carMapper = carMapper;
        this.fixDateComparator = fixDateComparator;
    }

    @Override
    public List<CarDto> getAllCarsToDto() {
        return carRepo.returnCarList().stream().map(carMapper::toDto).collect(Collectors.<CarDto>toList());
    }

    /**
     * All fixed cars sorted by fix date (descending)
     * @return list of all fixed cars sorted by fix date (descending)
     */
    @Override
    public List<CarDto> getAllCarsSorted() {
        List<CarDto> cars = getAllCarsToDto();
        cars.sort(fixDateComparator);
        return cars;
    }
}