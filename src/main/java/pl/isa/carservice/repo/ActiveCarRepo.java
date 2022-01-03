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
public class ActiveCarRepo implements CarRepository {

    private List<Car> activeCarList;
    private FileActions<Car> carFileActions;
    private FilePaths filePaths;

    @Autowired
    public ActiveCarRepo(FileActions<Car> carFileActions, FilePaths filePaths) {
        this.carFileActions = carFileActions;
        this.filePaths = filePaths;
    }

    @Override
    @EventListener(ApplicationReadyEvent.class)
    public void getCarListFromBase() {
        this.activeCarList = carFileActions.readObjectListFromFile(filePaths.getActiveCarsPath(), Car.class);
    }

    @Override
    public void saveCarListToBase() {
        carFileActions.writeObjectListToFile(filePaths.getActiveCarsPath(), this.activeCarList);
    }

    @Override
    public Car findCarFromListByRegNumb(String registrationNumber) {
        return this.activeCarList.stream()
                .filter(c -> c.getRegistrationNumber().equalsIgnoreCase(registrationNumber))
                .findFirst()
                .orElseThrow(() -> {
                    throw new CarNotFoundException(registrationNumber);
                });
    }

    @Override
    public void deleteCarFromList(Car car) {
        this.activeCarList.remove(car);
    }

    @Override
    public void addCarToList(Car car) {
        this.activeCarList.add(car);
    }
}
