package pl.isa.carservice;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.service.ActiveCarService;
import pl.isa.carservice.service.CarService;

import java.util.List;

@RestController
public class ActiveCarAPI {

    private final CarService activeCarService;

    public ActiveCarAPI(@Qualifier("activeCarService") CarService activeCarService) {
        this.activeCarService = activeCarService;
    }

    @GetMapping()
    public List<Car> getAllActiveSortedByAcceptedDate() {
        return ((ActiveCarService) activeCarService).getAllCarsSortedByAcceptedDateAsc();
    }

}
