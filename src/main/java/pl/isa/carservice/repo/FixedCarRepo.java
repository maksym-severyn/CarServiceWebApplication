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

import java.util.List;

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
        this.fixedCarList = carFileActions.readObjectListFromDir(filePaths.getFixedCarsDirPath(), Car.class);
    }

    @Override
    public void saveCarListToBase() {
        carFileActions.writeObjectListToFile(filePaths.getFixedCarsDirPath(), this.fixedCarList);
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

    @Override
    public void deleteCarFromList(Car car) {
        this.fixedCarList.remove(car);
    }

    @Override
    public void addCarToList(Car car) {
        this.fixedCarList.add(car);
    }
}
