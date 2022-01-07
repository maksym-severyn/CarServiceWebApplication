package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.service.ActiveCarService;
import pl.isa.carservice.service.ActiveCarServiceInterface;

@Controller
public class ActiveCarController {

    private final ActiveCarServiceInterface activeCarService;

    public ActiveCarController(@Qualifier("activeCarService") ActiveCarServiceInterface activeCarService) {
        this.activeCarService = activeCarService;
    }

    @GetMapping()
    public ModelAndView getAllActiveSortedByAcceptedDate() {
        ModelAndView mav = new ModelAndView("active-cars");
        mav.addObject("activeCars", activeCarService.getAllCarsSorted());
        mav.addObject("activePage", "cars-to-fix");
        return mav;
    }

    @GetMapping("cars/new")
    public String fillNewCar(Model model) {
        model.addAttribute("newCar", new Car());
        model.addAttribute("activePage", "new-car");
        return "car-form";
    }

    @PostMapping("cars/new")
    public String addNewCar(@ModelAttribute Car car) {
        ((ActiveCarService) activeCarService).addCarToList(car);
        return "car-form-success";
    }

    @GetMapping("search")
    public ModelAndView searchActiveCarsByParam(@RequestParam(value = "param", required = false, defaultValue = "") String parameter) {
        ModelAndView mav = new ModelAndView("search-cars-results");
        mav.addObject("activeCars", activeCarService.searchCarsByParam(parameter));
        mav.addObject("activePage", "found-cars");
        return mav;
    }

}
