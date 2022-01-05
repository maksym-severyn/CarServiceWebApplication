package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.service.ActiveCarService;
import pl.isa.carservice.service.CarService;

@Controller
public class ActiveCarController {

    private final CarService activeCarService;

    public ActiveCarController(@Qualifier("activeCarService") CarService activeCarService) {
        this.activeCarService = activeCarService;
    }

    @GetMapping()
    public ModelAndView getAllActiveSortedByAcceptedDate() {
        ModelAndView mav = new ModelAndView("active-cars");
        mav.addObject("activeCars", ((ActiveCarService) activeCarService).getAllCarsSortedByAcceptedDateAsc());
        mav.addObject("activePage", "cars-to-fix");
        return mav;
    }

}
