package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


// SERWIS DLA BAZY DANYCH KTORA ZAWIERA ZAKUPIONE BILETY U PRZEWOŹNIKA
@Service
public class CarrierOrderServiceImpl implements CarrierOrderService {

    private CarrierService carrierService;

    private BankAccountService bankAccountService;

    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    //spring
    @Autowired
    public CarrierOrderServiceImpl(CarrierService carrierService, BankAccountService bankAccountService) {
        this.carrierService = carrierService;
        this.bankAccountService = bankAccountService;
    }

    //testy
    public CarrierOrderServiceImpl(CarrierService carrierService, BankAccountService bankAccountService, CarrierOrderRepository carrierOrderRepository) {
        this.carrierOrderRepository = carrierOrderRepository;
        this.carrierService = carrierService;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCarrierId(String carrierId) {
        return carrierOrderRepository.findByCarrierId(carrierId);

    }


    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCompanyName(String companyName) {
        return carrierOrderRepository.findCarrierOrdersByCompanyName(companyName);
    }

    // na te chwile nie wiem jak to bardziej zoptymalizować
    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(String companyName, String startCity) {
        return carrierOrderRepository.findCarrierOrdersByCompanyNameAndCity(companyName, startCity);
    }

    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCarrierIdSorted(String carrierId) {
        if (carrierOrderRepository.findByCarrierId(carrierId).isEmpty()) return null;
        else {
            var carrierOrdersByCarrierId = carrierOrderRepository.findByCarrierId(carrierId);
            sort(carrierOrdersByCarrierId);
            return carrierOrdersByCarrierId;
        }
    }

    @Override
    public void sort(List<CarrierOrderModel> coList) {
        Collections.sort(coList, new CarrierOrderComparator());
    }

    @Override
    public void makePayment(String id) {
        if (carrierOrderRepository.findById(id).isPresent()) {
            var coM = carrierOrderRepository.findById(id).get();
            coM.setPaid(true);
            carrierOrderRepository.save(coM);
        }
    }

    @Override
    public Optional<CarrierOrderModel> getCarrierOrderById(String id) {
        var coM = carrierOrderRepository.findById(id);
        return coM;
    }

    @Override
    public CarrierOrderModel getCarrierOrderByEmailAndCarrierId(String email, String carrierId) {
        if (carrierOrderRepository.findByEmailAndCarrierId(email, carrierId) != null) {
            return carrierOrderRepository.findByEmailAndCarrierId(email, carrierId);
        } else return null;

    }

    // SLUZY DO ODSWIEZANIA BILETOW/ZAMOWIEN
    @Override
    public void refreshCarrierOrders() {
        var coList = carrierOrderRepository.findByPaid(false);
        var carrierOrdersForDelete = new ArrayList<CarrierOrderModel>();
        //JESLI ZAMOWIENIE JEST NIEOPLACONE A ZOSTALO MNIEJ NIZ 5DNI TO NALEZY TAKIE ZAMOWIENIE USUNAC
        for (CarrierOrderModel co : coList) {
            if (LocalDate.now().isAfter(carrierService.getCarrierById(co.getCarrierId()).getDate().minusDays(5)))
                carrierOrdersForDelete.add(co);
        }

        if (!carrierOrdersForDelete.isEmpty()) {
            for (CarrierOrderModel co1 : carrierOrdersForDelete) {
                carrierOrderRepository.delete(co1);
            }
        }

    }

    // SLUZY DO ZREZYGNOWANIA PRZEZ KLIENTU Z ZAKUPIONEGO BILETU
    @Override
    public boolean deleteOrder(String email, String carrierID) {
        double carrierCost = carrierService.getCarrierById(carrierID).getPrice();
        if (getCarrierOrderByEmailAndCarrierId(email, carrierID) != null) {
            var coM = getCarrierOrderByEmailAndCarrierId(email, carrierID);
            BankAccountModel baM = bankAccountService.getBankAccountByEmail(coM.getEmail());
            if (!coM.isPaid()) {
                carrierOrderRepository.delete(coM);
                return true;
            } else {
                //jesli zostalo wiecej niz 7 dni do wyjazdu zwroc 90%
                if (LocalDate.now().isBefore(carrierService.getCarrierById(coM.getCarrierId()).getDate().minusDays(7))) {
                    if (bankAccountService.addMoneyToAccount(baM.getEmail(), carrierCost * 0.9)) {
                        carrierOrderRepository.delete(coM);
                        return true;
                    }
                } else {
                    //jesli zostalo mniej niz 7 dni do wyjazdu zwroc 50%
                    if (bankAccountService.addMoneyToAccount(baM.getEmail(), carrierCost * 0.5)) {
                        carrierOrderRepository.delete(coM);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void removeAllOrders(List<CarrierOrderModel> coList) {
        for (CarrierOrderModel coM : coList)
            carrierOrderRepository.delete(coM);
    }

    @Override
    public boolean addOrder(CarrierOrderModel model) {
        if (carrierService.availabilityMinusOne(model.getCarrierId())) {
            carrierOrderRepository.save(model);
            return true;
        }
        return false;
    }

    @Override
    public Iterable<CarrierOrderModel> unpaidOrders(String email) {
        return carrierOrderRepository.findUnpaidOrders(email);
    }

    @Override
    public Iterable<CarrierOrderModel> getCarrierOrderListIterable() {
        return carrierOrderRepository.findAll();
    }

    @Override
    public void removeCarrierOrder(CarrierOrderModel i) {
        carrierOrderRepository.delete(i);
    }

    @Override
    public boolean payForOrder(String email, String carrierId) {
        var account = bankAccountService.getBankAccountByEmail(email);
        var coModel = getCarrierOrderByEmailAndCarrierId(email, carrierId);
        var cModel = carrierService.getCarrierById(carrierId);

        if (account.getAccountBalance() >= cModel.getPrice()) {
            makePayment(coModel.getId());
            bankAccountService.chargeMoney(account, cModel.getPrice());
            return true;
        } else return false;
    }

    @Override
    public CarrierOrderRepository getCarrierOrderRepository() {
        return carrierOrderRepository;
    }

}