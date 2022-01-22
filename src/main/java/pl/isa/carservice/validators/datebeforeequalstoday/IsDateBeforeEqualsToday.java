package pl.isa.carservice.validators.datebeforeequalstoday;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateBeforeEqualsTodayValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsDateBeforeEqualsToday {
    String message() default "Date must equals today or earlier";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}