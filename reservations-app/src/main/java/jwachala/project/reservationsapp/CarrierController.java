package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

// API PRZEWOZNIKA
@RestController
@RequestMapping("api/carrier")
public class CarrierController {

    private final CarrierService carrierService;
    private final CarrierOrderService carrierOrderService;
    private final CarrierHistoryService carrierHistoryService;
    private final ResourceLocationBuilder resourceLocationBuilder;

    @Autowired
    public CarrierController(CarrierService carrierService, CarrierOrderService carrierOrderService, CarrierHistoryService carrierHistoryService, ResourceLocationBuilder resourceLocationBuilder) {
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
        this.carrierHistoryService = carrierHistoryService;
        this.resourceLocationBuilder = resourceLocationBuilder;
    }

    // ---OPERACJE PRZEWOZNIKA---
    @GetMapping("carriers")
    public List<CarrierDTO> getCarriers() {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (CarrierModel cM : carrierService.getAllCarriers()) {
            CarrierDTO cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            cDTO.setRealized(cM.isRealized());
            cDTO.setPrice(cM.getPrice());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    // WSZYSTKIE OFERT PRZEWOZOW WG MIASTA STARTOWEGO
    @GetMapping("/carriers/city/start/{startCity}")
    public List<CarrierDTO> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (CarrierModel cM : carrierService.getCarriersbyStartCity(startCity)) {
            CarrierDTO cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            cDTO.setRealized(cM.isRealized());
            cDTO.setPrice(cM.getPrice());
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
        model.setPrice(dto.getPrice());
        carrierService.addCarrier(model);
        var uri = resourceLocationBuilder.build(model.getId());

        return ResponseEntity.created(uri).build();
    }

    // USTAWIENIE PRZEWOZU PRZEZ PRZEWOZNIKA JAKO ZREALIZOWANY
    @PutMapping("/carrier/id/{carrierId}/realized")
    public ResponseEntity<?> isRealized(@PathVariable(value = "carrierId") String carrierId) {
        CarrierModel cM = carrierService.getCarrierById(carrierId);
        cM.setRealized(true);

        CarrierDTO cDTO = new CarrierDTO();
        cDTO.setRealized(cM.isRealized());
        cDTO.setId(cM.getId());
        cDTO.setAvailability(cM.getAvailability());
        cDTO.setStartCity(cM.getStartCity());
        cDTO.setDestinationCity(cM.getDestinationCity());
        cDTO.setDate(cM.getDate());
        cDTO.setCompanyName(cM.getCompanyName());
        cDTO.setPrice(cM.getPrice());

        return ResponseEntity.ok(cDTO);
    }

    // ---OPERACJE NA BILETACH/ZAMOWIENIACH---

    // WSZYSTKIE ZAMOWIENIA/BILETY ZAKUPIONE PRZEZ PODROZNIKOW
    @GetMapping("/orders")
    public List<CarrierOrderDTO> getCarrierOrders() {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();
        for (CarrierOrderModel coM : carrierOrderService.getCarrierOrderListIterable()) {
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
        for (var coM : carrierOrderService.getCarrierOrdersByCompanyName(companyName)) {
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
        for (var coM : carrierOrderService.getCarrierOrdersByCompanyNameAndCity(companyName, startCity)) {
            var coDTO = new CarrierOrderDTO();
            coDTO.setCarrierId(coM.getCarrierId());
            coDTO.setOrderDate(coM.getOrderDate());
            coDTO.setEmail(coM.getEmail());
            coDTO.setPaid(coM.isPaid());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // ---HISTORIA PRZEWOZOW---

    //HISTORIA WSZYSTKICH PRZEWOZOW DLA DANEJ FIRMY (TRAFIAJA TAM TE PRZEWOZY KTORYCH DATA < LocalDate.now() LUB KTORE MAJA POLE realized = true)
    @GetMapping("/carriers/company/{companyName}/history")
    public List<CarrierDTO> getHistoryByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierHistoryService.getHistoryCarriersbyCompanyName(companyName)) {
            var cDTO = new CarrierDTO();
            cDTO.setCompanyName(cM.getCompanyName());
            cDTO.setDate(cM.getDate());
            cDTO.setStartCity(cM.getStartCity());
            cDTO.setDestinationCity(cM.getDestinationCity());
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            cDTO.setRealized(cM.isRealized());
            cDTO.setPrice(cM.getPrice());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    // ZWRACA LISTE BILETOW/ZLECEN DLA DANEGO PRZEWOZU POSORTOWANE WG isPaid i date
    @GetMapping("/carrierid/{carrierID}/orders")
    public List<CarrierOrderModel> getCarrierOrdersByCarrierIdSorted(@PathVariable(value = "carrierID") String carrierID) {
        return carrierOrderService.getCarrierOrdersByCarrierIdSorted(carrierID);
    }

    //  ODSWIEZANIE HISTORII (TRAFIAJA TAM TE PRZEWOZY KTORYCH DATA < LocalDate.now() LUB KTORE MAJA POLE realized = true)
    @GetMapping("history/refresh")
    public List<CarrierModel> refreshHistory() {
        return carrierHistoryService.refreshHistory();
    }

    // ODSWIEZA LISTE ZLECEN, NP JESLI NIE OPLACONE 5 DNI PRZED WYJAZDEM TO ANULOWANE
    @GetMapping("orders/refresh")
    public String refreshOrders() {
        carrierOrderService.refreshCarrierOrders();
        return "refreshed!";
    }

    // ANULOWANIE PRZEWOZU (PRZEZ FIRME) - ODDANIE KASY KLIENTOM JESLI OPLACICLI PRZEJAZD
    @DeleteMapping("carriers/id/{carrierId}/delete")
    public ResponseEntity<?> deleteCarrier(@PathVariable(value = "carrierId") String carrierId) {
        if (carrierService.cancelCarrier(carrierId))
            return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

}
