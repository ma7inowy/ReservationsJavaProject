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
    @Autowired
    private CarrierRepostiory carrierRepostiory;
    @Autowired
    private CarrierOrderRepository carrierOrderRepository;
    @Autowired
    private BankAccountRepository bankAccountRepository;

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW
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
            cDTO.setAvailability(cM.getAvailability());
            cDTO.setId(cM.getId());
            cDTO.setRealized(cM.isRealized());
            dtoList.add(cDTO);
        }

        return dtoList;
    }

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW Z OKRESLONEGO MIASTA
    @GetMapping("/carriers/city/start/{startCity}")
    public List<CarrierDTO> getCarriersByCity(@PathVariable(value = "startCity") String startCity) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepostiory.getCarriersbyStartCity(startCity)) {
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

    //PLAN PODROZNY - WSZYSTKIE OFERTY PRZEJAZDOW DANEJ FIRMY
    @GetMapping("/carriers/company/{companyName}")
    public List<CarrierDTO> getCarriersByCompanyName(@PathVariable(value = "companyName") String companyName) {
        List<CarrierDTO> dtoList = new ArrayList<>();
        for (var cM : carrierRepostiory.getCarriersbyCompanyName(companyName)) {
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

    //UTWORZENIE OFERTY PRZEWOZU
    @PostMapping("order")
    public ResponseEntity<?> createOrder(@RequestBody CarrierOrderDTO dto) {
        var model = new CarrierOrderModel();
        model.setEmail(dto.getEmail());
        model.setOrderDate(dto.getOrderDate());
        model.setCarrierId(dto.getCarrierId());
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(model.getId()).toUri();

        if (carrierRepostiory.availabilityMinusOne(dto.getCarrierId())) {
            carrierOrderRepository.getCarrierOrderList().add(model);
            // dodanie pasażera na listę chętnych do skorzystania z usługi przewozu
            carrierRepostiory.getCarrierById(dto.getCarrierId()).getPassengers().add(model);
            return ResponseEntity.created(uri).build();
        } else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, No availability!");
        }

    }

    //OPLAC ZAREZERWOWANY BILET
    @PostMapping("order/email/{email}/pay")
    public ResponseEntity<?> payOrder(@PathVariable(value = "email") String email, @RequestBody String carrierId) {
        var account = bankAccountRepository.getBankAccountByEmail(email);
        var coModel = carrierOrderRepository.getCarrierOrderByEmailAndCarrierId(email, carrierId);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(coModel.getId()).toUri();

        // ZAMIAST 10ZL BEDZIE KONKRETNA KWOTA JAK DODAM POLE W CARRIER O CENIE
        if (account.getAccountBalance() >= 10) {
            carrierOrderRepository.makePayment(coModel.getId());
            account.setAccountBalance(account.getAccountBalance() - 10);
            return ResponseEntity.created(uri).build();
        } else return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, Too less money in your Bank!");
    }

    // get ktory pokaze nieoplacone bilety dla konkretnego uzytkownika
    @GetMapping("orders/notpaid/email/{email}")
    public List<CarrierOrderDTO> getNotPayedOrders(@PathVariable(value = "email") String email) {
        List<CarrierOrderDTO> dtoList = new ArrayList<>();

        for (CarrierOrderModel coModel : carrierOrderRepository.getCarrierOrderList()) {
            if (!coModel.isPaid() && coModel.getEmail().equals(email)) {
                CarrierOrderDTO coDTO = new CarrierOrderDTO();
                coDTO.setEmail(coModel.getEmail());
                coDTO.setOrderDate(coModel.getOrderDate());
                coDTO.setCarrierId(coModel.getCarrierId());
                coDTO.setPaid(coModel.isPaid()); // bedzie mozna to ukryc
                dtoList.add(coDTO);
            }
        }
        return dtoList;

    }
    // zwracac ma carrierid

    // sprawdzenie czy dany przewoz jest odwolany czy zaakceptowany

    //ZEBY PODROZNIK MOGL SPRAWDZIC KTORY JEST NA LISCIE CHETNYCH?
}
