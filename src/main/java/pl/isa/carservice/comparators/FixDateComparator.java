package pl.isa.carservice.comparators;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.entity.dto.CarDto;

import java.time.Period;
import java.util.Comparator;

@Component
public class FixDateComparator implements Comparator<CarDto> {
    @Override
    public int compare(CarDto car, CarDto t1) {
        return Period.between(car.getDateOfFix(), t1.getDateOfFix()).getDays();
    }
}
