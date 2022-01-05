package pl.isa.carservice.repo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;
import pl.isa.carservice.config.FilePaths;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.exception.CarNotFoundException;
import pl.isa.carservice.util.FileActions;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FixedCarRepo implements CarRepository {

    private List<Car> fixedCarList;
    private FileActions<Car> carFileActions;
    private FilePaths filePaths;

    @Autowired
    public FixedCarRepo(FileActions<Car> carFileActions, FilePaths filePaths) {
        this.carFileActions = carFileActions;
        this.filePaths = filePaths;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void getCarListFromBase() {
        List<ArrayList<Car>> cars = carFileActions.readListOfObjectListsFromDir(filePaths.getFixedCarsDirPath(), Car.class);
        fixedCarList = cars.stream()
                .flatMap(l -> l.stream())
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> returnCarList() {
        return getFixedCarList();
    }

    @Override
    public void saveCarListToBase() {
        Set<LocalDate> dateSet = generateUniqueCarFixDates();
        dateSet.forEach(date -> {
            List<Car> cars = findCarsFromListByFixDate(date);
            carFileActions.writeObjectListToFile(filePaths.getFixedCarsDirPath() + date.toString() + ".json" , cars);
        });
    }

    private Set<LocalDate> generateUniqueCarFixDates() {
        Set<LocalDate> dateSet = new HashSet<>();
        this.fixedCarList.forEach(c -> dateSet.add(c.getDateOfFix()));
        return dateSet;
    }

    @Override
    public Car findCarFromListByRegNumb(String registrationNumber) {
        return this.fixedCarList.stream()
                .filter(c -> c.getRegistrationNumber().equalsIgnoreCase(registrationNumber))
                .findFirst()
                .orElseThrow(() -> {
                    throw new CarNotFoundException(registrationNumber);
                });
    }

    public List<Car> findCarsFromListByFixDate(LocalDate fixDate) {
        return this.fixedCarList.stream()
                .filter(c -> Period.between(c.getDateOfFix(), fixDate).getDays() == 0)
                .collect(Collectors.<Car>toList());
    }

    @Override
    public void deleteCarFromList(Car car) {
        this.fixedCarList.remove(car);
    }

    @Override
    public void addCarToList(Car car) {
        this.fixedCarList.add(car);
    }

    @Override
    public boolean contains(Car car) {
        return fixedCarList.contains(car);
    }
}
