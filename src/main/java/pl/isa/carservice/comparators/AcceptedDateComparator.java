package pl.isa.carservice.comparators;

import org.springframework.stereotype.Component;
import pl.isa.carservice.entity.dto.CarDto;

import java.time.Period;
import java.util.Comparator;

@Component
public class AcceptedDateComparator implements Comparator<CarDto> {
    @Override
    public int compare(CarDto c1, CarDto c2) {
        return Period.between(c1.getCarAcceptedDate(), c2.getCarAcceptedDate()).getDays();
    }
}