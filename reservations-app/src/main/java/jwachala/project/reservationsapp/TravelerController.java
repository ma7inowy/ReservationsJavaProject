package jwachala.project.reservationsapp;


import org.springframework.web.bind.annotation.*;

import java.net.URI;
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

    // better address for query needed
    @GetMapping("/carriers/name/{companyName}")
    public List<Carrier> getCarriersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        return carrierRepostiory.getCarriersbyCompanyName(companyName);
    }

}
