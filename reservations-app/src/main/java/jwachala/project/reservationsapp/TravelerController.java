package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// API PODROZNIKA
@RestController
@RequestMapping("api/traveler")
public class TravelerController {

    private final CarrierService carrierService;
    private final CarrierOrderService carrierOrderService;
    private final BankAccountService bankAccountService;
    private final ResourceLocationBuilder resourceLocationBuilder;

    @Autowired
    public TravelerController(CarrierService carrierService, CarrierOrderService carrierOrderService, BankAccountService bankAccountService, ResourceLocationBuilder resourceLocationBuilder) {
        this.carrierService = carrierService;
        this.carrierOrderService = carrierOrderService;
        this.bankAccountService = bankAccountService;
        this.resourceLocationBuilder = resourceLocationBuilder;
    }

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW
    @GetMapping("carriers")
    public List<CarrierDTO> getCarriers() {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierService.getAllCarriers()) {
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

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW Z OKRESLONEGO MIASTA
    @GetMapping("/carriers/city/start/{startCity}")
    public List<CarrierDTO> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierService.getCarriersbyStartCity(startCity)) {
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

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW Z OKRESLONEGO MIASTA DO OKRESLONEGO MIASTA
    @GetMapping("/carriers/city/start/{startCity}/destination/{destinationCity}")
    public List<CarrierDTO> getCarriersbyStartCityAndDestination(@PathVariable(value = "startCity") String startCity, @PathVariable(value = "destinationCity") String destinationCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierService.getCarriersbyStartCityAndDestination(startCity, destinationCity)) {
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


    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW DANEJ FIRMY
    @GetMapping("/carriers/company/{companyName}")
    public List<CarrierDTO> getCarriersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierService.getCarriersbyCompanyName(companyName)) {
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

    //UTWORZENIE ZAMOWIENIA PRZEJAZDU ( BILET )
    // NIETESTOWALNE !!! BO NIE DOSTANE SIE DO MODEL ID
    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrderDTO dto) {
        var model = new CarrierOrderModel();
        model.setEmail(dto.getEmail());
        model.setOrderDate(dto.getOrderDate());
        model.setCarrierId(dto.getCarrierId());
        var uri = resourceLocationBuilder.build(model.getId());

        // dodanie pasażera na listę chętnych do skorzystania z usługi przewozu jesli sa jeszcze wolne miejsca
        if (carrierOrderService.addOrder(model)) {
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, No availability!");
        }

    }

    //OPLAC ZAREZERWOWANY BILET
    @PostMapping("order/payment/email/{email}")
    public ResponseEntity<?> payOrder(@PathVariable(value = "email") String email, @RequestBody String carrierId) {
        var coModel = carrierOrderService.getCarrierOrderByEmailAndCarrierId(email, carrierId);
        var uri = resourceLocationBuilder.build(coModel.getId());
        // ZAMIAST 10ZL BEDZIE KONKRETNA KWOTA JAK DODAM POLE W CARRIER O CENIE
        if (carrierOrderService.payForOrder(email, carrierId)) {
            return ResponseEntity.created(uri).build();
        } else return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, Too less money on your bank account!");
    }

    // get ktory pokaze nieoplacone bilety dla konkretnego uzytkownika
    @GetMapping("orders/unpaid/email/{email}")
    public List<CarrierOrderTravelerDTO> getNotPayedOrders(@PathVariable(value = "email") String email) {
        List<CarrierOrderTravelerDTO> dtoList = new ArrayList<>();

        for (CarrierOrderModel coModel : carrierOrderService.unpaidOrders(email)) {
            CarrierOrderTravelerDTO coDTO = new CarrierOrderTravelerDTO();
            coDTO.setEmail(coModel.getEmail());
            coDTO.setOrderDate(coModel.getOrderDate());
            coDTO.setCarrierId(coModel.getCarrierId());
            dtoList.add(coDTO);
        }
        return dtoList;

    }

    // ZWRACA LISTE BILETOW/ZLECEN DLA DANEGO PRZEWOZU POSORTOWANE WG isPaid i date
    // ZEBY PODROZNIK MOGL SPRAWDZIC KTORY JEST NA LISCIE CHETNYCH, BEZ UJAWNIENIA POLA ISPAID
    @GetMapping("/orders/carrierid/{carrierID}")
    public List<CarrierOrderTravelerDTO> getCarrierOrdersByCarrierIdSorted(@PathVariable(value = "carrierID") String carrierID) {
        List<CarrierOrderTravelerDTO> dtoList = new ArrayList<>();
        List<CarrierOrderModel> coMListsorted = carrierOrderService.getCarrierOrdersByCarrierIdSorted(carrierID);
        for (CarrierOrderModel coModel : coMListsorted) {
            CarrierOrderTravelerDTO coDTO = new CarrierOrderTravelerDTO();
            coDTO.setCarrierId(coModel.getCarrierId());
            coDTO.setEmail(coModel.getEmail());
            coDTO.setOrderDate(coModel.getOrderDate());
            dtoList.add(coDTO);
        }

        return dtoList;
    }

    // anulowanie zamowienia oplaconego lub nieoplaconego
    @DeleteMapping("/order/carrierid/{carrierID}/email/{email}/delete")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "email") String email, @PathVariable(value = "carrierID") String carrierID) {
        if (carrierOrderService.deleteOrder(email, carrierID)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // dla testowania
    @GetMapping("add/orders")
    public void addOrders() {
        carrierOrderService.getCarrierOrderRepository().save(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().minusDays(5), carrierService.getCarrierRepository().findAll().get(0).getId()));
        carrierOrderService.getCarrierOrderRepository().save(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().minusDays(5), carrierService.getCarrierRepository().findAll().get(2).getId()));
        carrierOrderService.getCarrierOrderRepository().save(new CarrierOrderModel("jankowalski2@wp.pl", LocalDate.now().minusDays(10), carrierService.getCarrierRepository().findAll().get(2).getId()));
        carrierOrderService.getCarrierOrderRepository().save(new CarrierOrderModel("jankowalski3@wp.pl", LocalDate.now().minusDays(2), carrierService.getCarrierRepository().findAll().get(3).getId()));
        carrierOrderService.getCarrierOrderRepository().save(new CarrierOrderModel("jankowalski4@wp.pl", LocalDate.now().minusDays(5), carrierService.getCarrierRepository().findAll().get(0).getId()));
        var coM = carrierOrderService.getCarrierOrderRepository().findAll().get(3);
        coM.setPaid(true);
        carrierOrderService.getCarrierOrderRepository().save(coM);
    }

    @GetMapping("add/carriers")
    public void addCarriersToRepository() {
        carrierService.getCarrierRepository().save(new CarrierModel("City1", "destCity1", LocalDate.now().plusDays(8), "Company0", 10));
        carrierService.getCarrierRepository().save(new CarrierModel("City2", "destCity2", LocalDate.now().plusDays(8), "Company1", 20));
        carrierService.getCarrierRepository().save(new CarrierModel("City2", "destCity4", LocalDate.now().minusDays(3), "Company2", 30));
        carrierService.getCarrierRepository().save(new CarrierModel("City3", "destCity4", LocalDate.now().minusDays(10), "Company3", 40));
        carrierService.getCarrierRepository().save(new CarrierModel("City5", "destCity5", LocalDate.now().plusDays(1), "Company3", 50));
    }
}
