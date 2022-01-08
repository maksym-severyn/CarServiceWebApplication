package pl.isa.carservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.isa.carservice.entity.CarName;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private String registrationNumber;
    private CarName name;
    private Integer manufactureYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate carAcceptedDate;
    private boolean isFixed;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfFix;

    public CarDto(String registrationNumber, CarName name, Integer manufactureYear, LocalDate carAcceptedDate) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.manufactureYear = manufactureYear;
        this.carAcceptedDate = carAcceptedDate;
        this.isFixed = false;
    }

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
                + ' ' + this.manufactureYear.toString()
                + ' ' + this.carAcceptedDate;
    }
}
