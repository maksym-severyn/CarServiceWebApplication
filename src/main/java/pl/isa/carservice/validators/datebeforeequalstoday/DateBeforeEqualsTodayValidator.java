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
        if (localDateField == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        return (localDateField.isBefore(now) || localDateField.isEqual(now));
    }
}