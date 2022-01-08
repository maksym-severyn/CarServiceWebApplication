package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.entity.Car;
import pl.isa.carservice.entity.CarName;
import pl.isa.carservice.entity.dto.CarDto;
import pl.isa.carservice.service.ActiveCarService;
import pl.isa.carservice.service.ActiveCarServiceInterface;

import java.time.LocalDate;

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
        model.addAttribute("newCar", new CarDto());
        model.addAttribute("activePage", "new-car");
        return "car-form";
    }

    @PostMapping("cars/new")
    public String addNewCar(@ModelAttribute CarDto carDto) {
        ((ActiveCarService) activeCarService).addCarToList(carDto);
        return "car-form-success";
    }

    @GetMapping("search")
    public ModelAndView searchActiveCarsByParam(@RequestParam(value = "param", required = false, defaultValue = "") String parameter) {
        ModelAndView mav = new ModelAndView("search-cars-results");
        mav.addObject("activeCars", activeCarService.searchCarsByParam(parameter));
        return mav;
    }

    @GetMapping("search/form/params")
    public ModelAndView searchActiveCarsByParams(@RequestParam(value = "regNumb", required = false) String regNumb,
                                                 @RequestParam(value = "name", required = false) CarName name,
                                                 @RequestParam(value = "acceptedDate", required = false) LocalDate acceptedDate,
                                                 @RequestParam(value = "manufactureYear", required = false) Integer manufactureYear) {
        ModelAndView mav = new ModelAndView("search-cars-results");
        mav.addObject("activeCars", activeCarService.searchCarsByAllParams(regNumb, name, acceptedDate, manufactureYear));
        return mav;
    }

    @GetMapping("search/form")
    public ModelAndView goToSearchActiveCarsByParams() {
        ModelAndView mav = new ModelAndView("car-form-search");
        mav.addObject("activePage", "searched-cars-to-fix");
        return mav;
    }
}
