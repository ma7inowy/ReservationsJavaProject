package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// API PRZEWOZNIKA
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

    // WSZYSTKIE OFERT PRZEWOZOW
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
            cDTO.setRealized(cM.isRealized());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    // WSZYSTKIE OFERT PRZEWOZOW WG MIASTA STARTOWEGO
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
            cDTO.setRealized(cM.isRealized());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    // DODANIE NOWEJ OFERTY PRZEWOZU
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

    // WSZYSTKIE ZAMOWIENIA/BILETY ZAKUPIONE PRZEZ PODROZNIKOW
    @GetMapping("/orders")
    public List<CarrierOrderDTO> getCarrierOrders() {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (CarrierOrderModel coM : carrierOrderRepository.getCarrierOrderList()) {
            CarrierOrderDTO coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setEmail(coM.getEmail());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setPaid(coM.isPaid());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // WSZYSTKIE ZAMOWIENIA/BILETY ZAKUPIONE PRZEZ PODROZNIKOW U DANEJ FIRMY
    @GetMapping("/orders/company/{companyName}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        var dtoList = new ArrayList<CarrierOrderDTO>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyName(companyName)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setEmail(coM.getEmail());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setPaid(coM.isPaid());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // WSZYSTKIE ZAMOWIENIA/BILETY ZAKUPIONE PRZEZ PODROZNIKOW U DANEJ FIRMY, Z KONKRETNEGO MIASTA STARTOWEGO
    @GetMapping("/orders/company/{companyName}/city/start/{startCity}")
    public List<CarrierOrderDTO> getCarrierOrdersByCompanyNameAndCity(@PathVariable(value = "companyName") String companyName,
                                                                      @PathVariable(value = "startCity") String startCity) {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (var coM : carrierOrderRepository.getCarrierOrdersByCompanyNameAndCity(companyName, startCity)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setEmail(coM.getEmail());
            coDTO.setPaid(coM.isPaid());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // DO ZALADOWANIA PRZYKLADOWYCH ZAMOWIEN/BILETOW (korzystam z CarrierRepo wiec musialem w ten sposob bo inaczej error)
    @GetMapping("/load")
    public void loadDataToOrderList() {
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().plusDays(1), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().get(0).setPaid(true);
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski2@wp.pl", LocalDate.now().minusDays(2), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski3@wp.pl", LocalDate.now().plusDays(7), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().get(2).setPaid(true);
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski4@wp.pl", LocalDate.now().plusDays(5), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski5@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski6@wp.pl", LocalDate.now().plusDays(5), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().get(5).setPaid(true);
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski7@wp.pl", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(0).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski8@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(1).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski9@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(2).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski10@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(3).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(3).getId()));
        carrierOrderRepository.getCarrierOrderList().add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().minusDays(14), carrierRepostiory.getCarrierList().get(2).getId()));
    }

    //history

    //HISTORIA WSZYSTKICH PRZEWOZOW DLA DANEJ FIRMY (TRAFIAJA TAM TE PRZEWOZY KTORYCH DATA < LocalDate.now() LUB KTORE MAJA POLE realized = true)
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
            cDTO.setRealized(cM.isRealized());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    // ZWRACA LISTE BILETOW/ZLECEN DLA DANEGO PRZEWOZU POSORTOWANE WG isPaid i date
    @GetMapping("/orders/{carrierID}")
    public List<CarrierOrderModel> getCarrierOrdersByCarrierIdSorted(@PathVariable(value = "carrierID") String carrierID) {
        return carrierOrderRepository.getCarrierOrdersByCarrierIdSorted(carrierID);
    }

    //history

    // ODSWIEZANIE HISTORII (TRAFIAJA TAM TE PRZEWOZY KTORYCH DATA < LocalDate.now() LUB KTORE MAJA POLE realized = true)
    @GetMapping("historyrefresh")
    public List<CarrierOrderModel> refreshHistory() {
        return carrierHistory.refreshHistory();
    }

    @GetMapping("history")
    public List<CarrierModel> getCarrierHistory(){
        return carrierHistory.getCarrierHistoryList();
    }

    // ODSWIEZA LISTE ZLECEN, NP JESLI NIE OPLACONE 1 TYDZ PRZED WYJAZDEM TO ANULOWANE
    @GetMapping("orders/refresh")
    public String refreshOrders(){
        carrierOrderRepository.refreshCarrierOrders();
        return "refreshed!";
    }
    // anulowanie, akceptowanie
}
