package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/traveler")
public class TravelerController {
    @Autowired
    private CarrierRepostiory carrierRepostiory;
    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    @GetMapping("carriers")
    public List<CarrierDTO> getCarriers() {
        // wg mnie podroznik powinien widziec id przewozu zeby moc stworzyc zlecenie
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepostiory.getCarrierList()) {
            var cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    @GetMapping("/carriers/{startCity}")
    public List<CarrierDTO> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepostiory.getCarriersbyStartCity(startCity)) {
            var cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    @GetMapping("/carriers/name/{companyName}")
    public List<CarrierDTO> getCarriersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepostiory.getCarriersbyCompanyName(companyName)) {
            var cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrderDTO dto) {
        var model = new CarrierOrderModel();
        model.setName(dto.getName());
        model.setSurname(dto.getSurname());
        model.setOrderDate(dto.getOrderDate());
        model.setCarrierId(dto.getCarrierId());
        carrierOrderRepository.getCarrierOrderList().add(model);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    // sprawdzenie czy dany przewoz jest odwolany czy zaakceptowany
}
