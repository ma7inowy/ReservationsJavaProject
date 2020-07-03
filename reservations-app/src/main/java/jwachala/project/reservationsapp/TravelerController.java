package jwachala.project.reservationsapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("travelerapi")
public class TravelerController {

    private CarrierRepostiory carrierRepostiory;
    private CarrierOrderRepository carrierOrderRepository;

    public TravelerController() {
        carrierRepostiory = new CarrierRepostiory();
        carrierOrderRepository = new CarrierOrderRepository();
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

    // for making orders by travelers
    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrder carrierOrder){
        carrierOrderRepository.getCarrierOrderList().add(carrierOrder);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carrierOrder.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
