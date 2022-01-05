package pl.isa.carservice.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import pl.isa.carservice.entity.Car;
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

}
