package jwachala.project.reservationsapp;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("travelerapi")
public class TravelerController {

    private CarrierRepostiory carrierRepostiory;

    public TravelerController() {
        carrierRepostiory = new CarrierRepostiory();
    }

    @GetMapping("carriers")
    public List<Carrier> getCarriers() {
        return carrierRepostiory.getCarrierList();
    }

    @GetMapping("/carriers/{city}")
    public List<Carrier> getCarriersByCity(@PathVariable(value = "city") String city) {
        return carrierRepostiory.getCarriersbyCity(city);
    }

}
