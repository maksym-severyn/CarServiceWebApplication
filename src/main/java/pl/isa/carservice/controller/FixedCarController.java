package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.service.CarService;
import pl.isa.carservice.service.MovableFixingService;

@Controller
@RequestMapping("cars")
public class FixedCarController {

    private final CarService fixedCarService;
    private final MovableFixingService fixingService;

    public FixedCarController(@Qualifier("fixedCarService") CarService fixedCarService,
                              @Qualifier("fixingService") MovableFixingService fixingService) {
        this.fixedCarService = fixedCarService;
        this.fixingService = fixingService;
    }

    @GetMapping("fixed")
    public ModelAndView getAllActiveSortedByFixDate() {
        ModelAndView mav = new ModelAndView("fixed-cars");
        mav.addObject("fixedCars", fixedCarService.getAllCarsSorted());
        mav.addObject("activePage", "cars-fixed");
        return mav;
    }

    @PutMapping("fix/{nr}")
    public ModelAndView fixCar(@PathVariable(name = "nr") String registrationNumber) {
        ModelAndView mav = new ModelAndView("fixing-car-success");
        mav.addObject("activePage", "cars-fixed");
        fixingService.fixCar(registrationNumber);
        return mav;
    }
}