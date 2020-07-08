package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<CarrierDTO> getCarriers() {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (CarrierModel cM : carrierRepostiory.getCarrierList()) {
            CarrierDTO cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    @GetMapping("/carriers/{startCity}")
    public List<CarrierDTO> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (CarrierModel cM : carrierRepostiory.getCarriersbyStartCity(startCity)) {
            CarrierDTO cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    @PostMapping("carriers")
    public ResponseEntity<?> createCarrier(@RequestBody CarrierDTO dto) {
        var model = new CarrierModel();
        model.setStartCity(dto.getStartCity());
        model.setDestinationCity(dto.getDestinationCity());
        model.setDate(dto.getDate());
        model.setCompanyName(dto.getCompanyName());
        model.setAvailability(dto.getAvailability());
        carrierRepostiory.getCarrierList().add(model);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    //carrierOrders operations
    @GetMapping("/orders")
    public List<CarrierOrderDTO> getCarrierOrders() {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (CarrierOrderModel coM : carrierOrderRepository.getCarrierOrderList()) {
            CarrierOrderDTO coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setName(coM.getName());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setSurname(coM.getSurname());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // PODGLAD ZLECEN DANEJ FIRMY
    @GetMapping("/orders/{companyName}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        var dtoList = new ArrayList<CarrierOrderDTO>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyName(companyName)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setName(coM.getName());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setSurname(coM.getSurname());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    @GetMapping("/orders/{companyName}/{startCity}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyNameAndCity(@PathVariable(value = "companyName") String companyName,
                                                                      @PathVariable(value = "startCity") String startCity) {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyNameAndCity(companyName, startCity)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setName(coM.getName());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setSurname(coM.getSurname());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    @GetMapping("/load")
    public void loadDataToOrderList() {
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski1", LocalDate.now().plusDays(1), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski2", LocalDate.now().plusDays(2), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski3", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(1).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski4", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(2).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski5", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(3).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("Jan", "Kowalski6", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(3).getId()));
    }


    // anulowanie, akceptowanie
}
