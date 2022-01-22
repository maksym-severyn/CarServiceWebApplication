package pl.isa.carservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.isa.carservice.entity.CarName;
import pl.isa.carservice.validators.datebeforeequalstoday.IsDateBeforeEqualsToday;
import pl.isa.carservice.validators.yearbeforeequalstoday.IsYearBeforeEqualsToday;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    @NotBlank
    @Size(max = 20)
    private String registrationNumber;
    @NotNull
    private CarName name;
    @NotNull
    @Size(max = 20)
    private String model;
    @Min(value = 1900, message = "Rocznik ma być minimum 1900")
    @IsYearBeforeEqualsToday
    private Integer manufactureYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @IsDateBeforeEqualsToday
    private LocalDate carAcceptedDate;
    private boolean isFixed;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFix;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CarDto car = (CarDto) o;
        return registrationNumber.replaceAll("\\s","").equalsIgnoreCase(car.registrationNumber.replaceAll("\\s",""));
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber.toLowerCase().replaceAll("\\s",""));
    }

    @Override
    public String toString() {
        return this.registrationNumber
                + ' ' + this.name.getNameOfCar()
                + ' ' + this.model
                + ' ' + this.manufactureYear.toString()
                + ' ' + this.carAcceptedDate;
    }
}