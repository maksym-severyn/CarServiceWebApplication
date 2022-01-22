package pl.isa.carservice.validators.yearbeforeequalstoday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class YearBeforeEqualsTodayValidator implements ConstraintValidator<IsYearBeforeEqualsToday, Integer>{

    @Override
    public void initialize(IsYearBeforeEqualsToday date) {
    }

    @Override
    public boolean isValid(Integer yearField, ConstraintValidatorContext cxt) {
        return yearField != null && yearField <= LocalDate.now().getYear();
    }
}