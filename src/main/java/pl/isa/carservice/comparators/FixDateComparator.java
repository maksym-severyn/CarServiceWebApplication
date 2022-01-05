package pl.isa.carservice.comparators;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.Car;

import java.time.Period;
import java.util.Comparator;

@Component
public class FixDateComparator implements Comparator<Car> {
    @Override
    public int compare(Car car, Car t1) {
        return Period.between(car.getDateOfFix(), t1.getDateOfFix()).getDays();
    }
}
