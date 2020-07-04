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
    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

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
         model.setCompanyName(dto.getCompanyName());
//       model.setId(UUID.randomUUID().toString()); // czy moze id w konstr

        carrierRepostiory.getCarrierList().add(model);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //carrierOrders operations
    @GetMapping("/orders")
    public List<CarrierOrderModel> getCarrierOrders() {
        return carrierOrderRepository.getCarrierOrderList();
    }

    //here getCarrierOrdersByCarrierId
    @GetMapping("/orders/{carrierId}")
    public List<CarrierOrderModel> getCarrierOrdersByCarrierId(@PathVariable(value = "carrierId") String carrierId){
        return carrierOrderRepository.getCarrierOrdersByCarrierId(carrierId);
    }

    //ile os bedzie jechalo, anulowanie, akceptowanie


}
