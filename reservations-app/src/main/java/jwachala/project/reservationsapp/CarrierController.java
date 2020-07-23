package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

// API PRZEWOZNIKA
@RestController
@RequestMapping("api/carrier")
public class CarrierController {

    private final CarrierRepository carrierRepository;
    private final CarrierOrderService carrierOrderService;
    private final CarrierHistoryService carrierHistoryService;

    @Autowired
    public CarrierController(CarrierRepository carrierRepository, CarrierOrderService carrierOrderService, CarrierHistoryService carrierHistoryService) {
        this.carrierRepository = carrierRepository;
        this.carrierOrderService = carrierOrderService;
        this.carrierHistoryService = carrierHistoryService;
    }

    //carriers operations
    // WSZYSTKIE OFERTY PRZEWOZOW
    @GetMapping("carriers")
    public List<CarrierDTO> getCarriers() {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (CarrierModel cM : carrierRepository.getAllCarriers()) {
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
        for (CarrierModel cM : carrierRepository.getCarriersbyStartCity(startCity)) {
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
        carrierRepository.getCarrierList().add(model);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    // USTAWIENIE PRZEWOZU PRZEZ PRZEWOZNIKA JAKO ZREALIZOWANY
    @PutMapping("/carrier/id/{carrierId}/realized")
    public ResponseEntity<?> isRealized(@PathVariable(value = "carrierId") String carrierId){
        CarrierModel cM = carrierRepository.getCarrierById(carrierId);
        cM.realizedTrue();

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

    //carrierOrders operations

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

    // DO ZALADOWANIA PRZYKLADOWYCH ZAMOWIEN/BILETOW (korzystam z CarrierRepo wiec musialem w ten sposob bo inaczej error)
//    @GetMapping("/load")
//    public void loadDataToOrderList() {
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().minusDays(11), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().get(0).setPaid(true);
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski2@wp.pl", LocalDate.now().minusDays(2), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski3@wp.pl", LocalDate.now().plusDays(7), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().get(2).setPaid(true);
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski4@wp.pl", LocalDate.now().plusDays(5), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski5@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski6@wp.pl", LocalDate.now().plusDays(5), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().get(5).setPaid(true);
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski7@wp.pl", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(0).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski8@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(1).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski9@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(2).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski10@wp.pl", LocalDate.now().plusDays(3), carrierRepostiory.getCarrierList().get(3).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().plusDays(4), carrierRepostiory.getCarrierList().get(3).getId()));
//        carrierOrderService.getCarrierOrderList().add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().minusDays(14), carrierRepostiory.getCarrierList().get(2).getId()));
//    }

    //history

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

    //history

   //  ODSWIEZANIE HISTORII (TRAFIAJA TAM TE PRZEWOZY KTORYCH DATA < LocalDate.now() LUB KTORE MAJA POLE realized = true)
    @GetMapping("history/refresh")
    public List<CarrierOrderModel> refreshHistory() {
        return carrierHistoryService.refreshHistory();
    }

    @GetMapping("history")
    public Iterable<CarrierModel> getCarrierHistoryService() {
        return carrierHistoryService.getCarrierHistoryList();
    }

    // ODSWIEZA LISTE ZLECEN, NP JESLI NIE OPLACONE 5 DNI PRZED WYJAZDEM TO ANULOWANE
    @GetMapping("orders/refresh")
    public String refreshOrders() {
        carrierOrderService.refreshCarrierOrders();
        return "refreshed!";
    }

    // anulowanie przewozu - oddanie kasy albo jakiejs czesci
    @DeleteMapping("carriers/id/{carrierId}/delete")
    public ResponseEntity<?> deleteCarrier(@PathVariable(value = "carrierId") String carrierId){
        if(carrierRepository.deleteCarrier(carrierId))
        return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();

    }

}
