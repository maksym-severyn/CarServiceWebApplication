package pl.isa.carservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FilePaths {

    @Value("${spring.path.active-cars:src/main/resources/active/active-cars.json}")
    private String activeCarsPath;

    @Value("${spring.path.dir.fixed-cars:src/main/resources/cars/fixed/}")
    private String fixedCarsDirPath;
}
