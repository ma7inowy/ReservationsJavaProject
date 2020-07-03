package jwachala.project.reservationsapp;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("carrierapi")
public class CarrierController {

    private CarrierRepostiory carrierRepostiory;
    private CarrierOrderRepository carrierOrderRepository;

    public CarrierController() {
        carrierRepostiory = new CarrierRepostiory();
        carrierOrderRepository = new CarrierOrderRepository();
    }

    //carriers operations
    @GetMapping("carriers")
    public List<Carrier> getCarriers() {
        return carrierRepostiory.getCarrierList();
    }

    @GetMapping("/carriers/{city}")
    public List<Carrier> getCarriersByCity(@PathVariable(value = "city") String city) {
        return carrierRepostiory.getCarriersbyCity(city);
    }

    @PostMapping("carriers")
    public ResponseEntity<?> createCarrier(@RequestBody Carrier carrier) {
        carrierRepostiory.getCarrierList().add(carrier);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carrier.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //carrierOrders operations
    @GetMapping("/carrierOrders")
    public List<CarrierOrder> getCarrierOrders() {
        return carrierOrderRepository.getCarrierOrderList();
    }



}
