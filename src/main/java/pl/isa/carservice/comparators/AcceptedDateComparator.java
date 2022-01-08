package pl.isa.carservice.comparators;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.dto.CarDto;

import java.time.Period;
import java.util.Comparator;

@Component
public class AcceptedDateComparator implements Comparator<CarDto> {
    @Override
    public int compare(CarDto car, CarDto t1) {
        return Period.between(car.getCarAcceptedDate(), t1.getCarAcceptedDate()).getDays();
    }
}
