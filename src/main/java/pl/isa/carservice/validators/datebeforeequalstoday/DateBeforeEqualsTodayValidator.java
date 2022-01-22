package pl.isa.carservice.validators.datebeforeequalstoday;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DateBeforeEqualsTodayValidator implements ConstraintValidator<IsDateBeforeEqualsToday, LocalDate>{

    @Override
    public void initialize(IsDateBeforeEqualsToday date) {
    }

    @Override
    public boolean isValid(LocalDate localDateField, ConstraintValidatorContext cxt) {
        return localDateField != null && !localDateField.isAfter(LocalDate.now());
    }
}