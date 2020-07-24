package jwachala.project.reservationsapp;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

// API PODROZNIKA
@RestController
@RequestMapping("api/traveler")
public class TravelerController {

    private final CarrierRepository carrierRepository;
    private final CarrierOrderService carrierOrderService;
    private final BankAccountService bankAccountService;

    @Autowired
    public TravelerController(CarrierRepository carrierRepository, CarrierOrderService carrierOrderService, BankAccountService bankAccountService) {
        this.carrierRepository = carrierRepository;
        this.carrierOrderService = carrierOrderService;
        this.bankAccountService = bankAccountService;
    }

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW
    @GetMapping("carriers")
    public List<CarrierDTO> getCarriers() {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepository.getAllCarriers()) {
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
        for (var cM : carrierRepository.getCarriersbyStartCity(startCity)) {
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
        for (var cM : carrierRepository.getCarriersbyStartCityAndDestination(startCity, destinationCity)) {
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
        for (var cM : carrierRepository.getCarriersbyCompanyName(companyName)) {
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
    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrderDTO dto) {
        var model = new CarrierOrderModel();
        model.setEmail(dto.getEmail());
        model.setOrderDate(dto.getOrderDate());
        model.setCarrierId(dto.getCarrierId());
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        // dodanie pasażera na listę chętnych do skorzystania z usługi przewozu jesli sa jeszcze wolne miejsca
        if (carrierRepository.availabilityMinusOne(dto.getCarrierId())) {
//            carrierOrderService.getCarrierOrderList().add(model);
            carrierOrderService.addOrder(model);
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, No availability!");
        }

    }

    //OPLAC ZAREZERWOWANY BILET
    @PostMapping("order/payment/email/{email}")
    public ResponseEntity<?> payOrder(@PathVariable(value = "email") String email, @RequestBody String carrierId) {
        var account = bankAccountService.getBankAccountByEmail(email);
        var coModel = carrierOrderService.getCarrierOrderByEmailAndCarrierId(email, carrierId);
        var cModel = carrierRepository.getCarrierById(carrierId);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(coModel.getId()).toUri();

        // ZAMIAST 10ZL BEDZIE KONKRETNA KWOTA JAK DODAM POLE W CARRIER O CENIE
        if (account.getAccountBalance() >= cModel.getPrice()) {
            carrierOrderService.makePayment(coModel.getId());
            account.setAccountBalance(account.getAccountBalance() - cModel.getPrice());
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


    // sprawdzenie czy dany przewoz jest odwolany czy zaakceptowany
}
