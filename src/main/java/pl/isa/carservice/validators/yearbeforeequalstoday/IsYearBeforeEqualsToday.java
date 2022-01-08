package pl.isa.carservice.validators.yearbeforeequalstoday;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearBeforeEqualsTodayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsYearBeforeEqualsToday {
    String message() default "Date must equals today or earlier";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
