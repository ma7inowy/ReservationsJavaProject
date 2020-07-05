package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

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
    @GetMapping("/carriers/{startCity}")
    public List<CarrierModel> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        return carrierRepostiory.getCarriersbyStartCity(startCity);
    }

    @PostMapping("carriers")
    public ResponseEntity<?> createCarrier(@RequestBody CarrierDTO dto) {
        var model = new CarrierModel();
        model.setStartCity(dto.getStartCity());
        model.setDestinationCity(dto.getDestinationCity());
        model.setDate(dto.getDate());
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

    //to get all orders by company Name
    @GetMapping("/orders/{companyName}")
    public List<CarrierOrderModel> getCarrierOrdersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        return carrierOrderRepository.getCarrierOrdersByCompanyName(companyName);
    }

    //to get all orders by company Name and specific City
    @GetMapping("/orders/{companyName}/{startCity}")
    public List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(@PathVariable(value = "companyName") String companyName,
                                                                        @PathVariable(value = "startCity") String startCity) {
        return carrierOrderRepository.getCarrierOrdersByCompanyNameAndCity(companyName, startCity);
    }


    //ile os bedzie jechalo, anulowanie, akceptowanie

    //how much people want to use this carrier


}
