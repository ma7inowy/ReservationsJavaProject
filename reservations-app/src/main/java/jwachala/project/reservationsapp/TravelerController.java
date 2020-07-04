package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/traveler")
public class TravelerController {
    @Autowired
    private CarrierRepostiory carrierRepostiory;
    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    @GetMapping("carriers")
    public List<CarrierModel> getCarriers() {
        return carrierRepostiory.getCarrierList();
    }

    @GetMapping("/carriers/{city}")
    public List<CarrierModel> getCarriersByCity(@PathVariable(value = "city") String city) {
        return carrierRepostiory.getCarriersbyCity(city);
    }

    // better address for query needed
    @GetMapping("/carriers/name/{companyName}")
    public List<CarrierModel> getCarriersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        return carrierRepostiory.getCarriersbyCompanyName(companyName);
    }

    // for making orders by travelers
    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrderDTO dto){
        var model = new CarrierOrderModel();
        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setDate(dto.getDate());
        model.setCarrierId(dto.getCarrierId());
        model.setDestinationCity(dto.getDestinationCity());


        carrierOrderRepository.getCarrierOrderList().add(model);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    // sprawdzenie czy dany przewoz jest odwolany czy zaakceptowany

}
