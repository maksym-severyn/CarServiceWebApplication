package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.service.CarService;
import pl.isa.carservice.service.FixedCarService;

@Controller
@RequestMapping("cars")
public class FixedCarController {

    private final CarService fixedCarService;

    public FixedCarController(@Qualifier("fixedCarService") CarService fixedCarService) {
        this.fixedCarService = fixedCarService;
    }


    @GetMapping("fixed")
    public ModelAndView getAllActiveSortedByFixDate() {
        ModelAndView mav = new ModelAndView("fixed-cars");
        mav.addObject("fixedCars", ((FixedCarService) fixedCarService).getAllCarsSortedByFixDateDesc());
        mav.addObject("activePage", "cars-fixed");
        return mav;
    }
}
