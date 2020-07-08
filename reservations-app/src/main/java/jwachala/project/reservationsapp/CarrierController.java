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

    @Autowired
    private CarrierHistory carrierHistory;

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

    @GetMapping("/carriers/city/start/{startCity}")
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

    @PostMapping("carrier")
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
            coDTO.setEmail(coM.getEmail());
            coDTO.setOrderDate(coM.getOrderDate());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // PODGLAD ZLECEN DANEJ FIRMY
    @GetMapping("/orders/company/{companyName}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        var dtoList = new ArrayList<CarrierOrderDTO>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyName(companyName)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setEmail(coM.getEmail());
            coDTO.setOrderDate(coM.getOrderDate());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    @GetMapping("/orders/company/{companyName}/city/start/{startCity}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyNameAndCity(@PathVariable(value = "companyName") String companyName,
                                                                      @PathVariable(value = "startCity") String startCity) {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyNameAndCity(companyName, startCity)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setEmail(coM.getEmail());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    @GetMapping("/load")
    public void loadDataToOrderList() {
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().plusDays(1), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski2@wp.pl", LocalDate.now().plusDays(2), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski3@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(1).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski4@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(2).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski5@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(3).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski6@wp.pl", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(3).getId()));
    }

    //history
    @GetMapping("/carriers/company/{companyName}/history")
    public List<CarrierDTO> getHistoryByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierHistory.getHistoryCarriersbyCompanyName(companyName)) {
            var cDTO = new CarrierDTO();
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

    // na te chwile nie mam pomyslu jak inaczej odswiezac dane
    @GetMapping("historyrefresh")
    public List<CarrierModel> refreshHistory() {
        List<CarrierModel> cMList = carrierRepostiory.getCarrierList();
        List<CarrierModel> forRemoveList = new ArrayList<>();
        for (int i = 0; i< cMList.size();i++) {
            if (cMList.get(i).getDate().compareTo(LocalDate.now()) < 0) {
                carrierHistory.getCarrierHistoryList().add(cMList.get(i));
                forRemoveList.add(cMList.get(i));
            }
        }
        for(CarrierModel i : forRemoveList)
            carrierRepostiory.getCarrierList().remove(i);


        // anulowanie, akceptowanie
        return forRemoveList;
    }
}
