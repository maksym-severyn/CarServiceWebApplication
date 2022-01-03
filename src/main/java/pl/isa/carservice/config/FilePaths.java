package pl.isa.carservice.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class FilePaths {

    @Value("${spring.path.active-cars}")
    private String activeCarsPath;

    @Value("${spring.path.dir.fixed-cars}")
    private String fixedCarsDirPath;
}