package pl.isa.carservice.entity;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    private String registrationNumber;
    private String name;
    private Integer manufactureYear;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate carAcceptedDate;
    private boolean isFixed;
    private LocalDate dateOfFix;

    public Car(String registrationNumber, String name, Integer manufactureYear, LocalDate carAcceptedDate) {
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
        Car car = (Car) o;
        return registrationNumber.replaceAll("\\s","").equalsIgnoreCase(car.registrationNumber.replaceAll("\\s",""));
    }

    @Override
    public int hashCode() {
        return Objects.hash(registrationNumber.toLowerCase().replaceAll("\\s",""));
    }
}
