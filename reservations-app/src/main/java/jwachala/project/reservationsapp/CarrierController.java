package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/carrier")
public class CarrierController {

    @Autowired
    private CarrierRepostiory carrierRepostiory;
    private CarrierOrderRepository carrierOrderRepository;

    public CarrierController() {
        carrierOrderRepository = new CarrierOrderRepository();
    }

    //carriers operations
    @GetMapping("carriers")
    public List<CarrierModel> getCarriers() {
        return carrierRepostiory.getCarrierList();
    }

    // musze zwracac liste z obiektami dto
    @GetMapping("/carriers/{city}")
    public List<CarrierModel> getCarriersByCity(@PathVariable(value = "city") String city) {
        return carrierRepostiory.getCarriersbyCity(city);
    }

    @PostMapping("carriers")
    public ResponseEntity<?> createCarrier(@RequestBody CarrierDTO dto) {
         var model = new CarrierModel();
         model.setCity(dto.getCity());
         model.setCity(dto.getCompanyName());
         model.setId(UUID.randomUUID().toString());

        carrierRepostiory.getCarrierList().add(model);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //carrierOrders operations
    @GetMapping("/carrierOrders")
    public List<CarrierOrder> getCarrierOrders() {
        return carrierOrderRepository.getCarrierOrderList();
    }

    //here getCarrierOrdersById

    //here getCarrierOrdersByCarrierId

    //ile os bedzie jechalo, anulowanie, akceptowanie


}
