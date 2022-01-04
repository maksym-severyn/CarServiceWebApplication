package pl.isa.carservice.comparators;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.Car;

import java.time.Period;
import java.util.Comparator;

@Component
public class AcceptedDateComparator implements Comparator<Car> {
    @Override
    public int compare(Car car, Car t1) {
        return Period.between(car.getCarAcceptedDate(), t1.getCarAcceptedDate()).getDays();
    }
}
