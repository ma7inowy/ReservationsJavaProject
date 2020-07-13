package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// BAZA DANYCH O ZAKUPIONYCH BILETACH/MIEJSCA U PRZEWOŹNIKA
@Component
@Data
public class CarrierOrderRepository {

    List<CarrierOrderModel> carrierOrderList;

    @Autowired
    CarrierRepostiory carrierRepostiory;

    @Autowired
    BankAccountRepository bankAccountRepository;

    public CarrierOrderRepository() {
        this.carrierOrderList = new ArrayList<>();
    }

    public List<CarrierOrderModel> getCarrierOrdersByCarrierId(String carrierId) {
        List<CarrierOrderModel> carrierOrdersByCarrierId = new ArrayList<>();
        for (var carrierOrder : carrierOrderList) {
            if (carrierOrder.getCarrierId().equals(carrierId)) {
                carrierOrdersByCarrierId.add(carrierOrder);
            }
        }
        return carrierOrdersByCarrierId;
    }

    public List<CarrierOrderModel> getCarrierOrdersByCompanyName(String companyName) {
        List<CarrierOrderModel> carrierOrderNewList = new ArrayList<>();
        for (var coModel : carrierOrderList) {
            for (var cModel : carrierRepostiory.getCarrierList()) {
                if (coModel.getCarrierId().equals(cModel.getId())) {
                    if (cModel.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
                        carrierOrderNewList.add(coModel);
                    }
                }
            }
        }
        return carrierOrderNewList;
    }

    // na te chwile nie wiem jak to bardziej zoptymalizować
    public List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(String companyName, String startCity) {
        List<CarrierOrderModel> carrierOrderByCompanyName = getCarrierOrdersByCompanyName(companyName);
        List<CarrierOrderModel> carrierOrderListByCnAndCity = new ArrayList<>();

        for (var coModel : carrierOrderByCompanyName) {
            for (var cModel : carrierRepostiory.getCarrierList()) {
                if (coModel.getCarrierId().equals(cModel.getId())) {
                    if (cModel.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
                        if (cModel.getStartCity().toLowerCase().equals(startCity.toLowerCase())) {
                            carrierOrderListByCnAndCity.add(coModel);
                        }
                    }
                }
            }
        }
        return carrierOrderListByCnAndCity;
    }

    public List<CarrierOrderModel> getCarrierOrdersByCarrierIdSorted(String carrierId) {
        List<CarrierOrderModel> carrierOrdersByCarrierId = new ArrayList<>();
        for (var carrierOrder : carrierOrderList) {
            if (carrierOrder.getCarrierId().equals(carrierId)) {
                carrierOrdersByCarrierId.add(carrierOrder);
            }
        }

        sort(carrierOrdersByCarrierId);
        return carrierOrdersByCarrierId;
    }

    public void sort(List<CarrierOrderModel> coList) {
        Collections.sort(coList, new CarrierOrderComparator());
    }

    public void makePayment(String id) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getId().equals(id))
                co.setPaid(true);
        }
    }

    public CarrierOrderModel getCarrierOrderById(String id) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getId().equals(id))
                return co;
        }
        return null;
    }

    public CarrierOrderModel getCarrierOrderByEmailAndCarrierId(String email, String carrierId) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getEmail().equals(email) && co.getCarrierId().equals(carrierId))
                return co;
        }
        return null;
    }

    public void refreshCarrierOrders() {
        List<CarrierOrderModel> coList = new ArrayList<>();

        for (CarrierOrderModel co : carrierOrderList) {
            if (!co.isPaid()) {
                if (co.getOrderDate().isAfter(carrierRepostiory.getCarrierById(co.getCarrierId()).getDate().minusDays(7)))
                    coList.add(co);
            }
        }
        for (CarrierOrderModel co1 : coList) {
            carrierOrderList.remove(co1);
        }
    }

    public boolean deleteOrder(String email, String carrierID){
        double carrierCost = 10; // na razie na sztywno wpisuje cene przejazdu
        if(getCarrierOrderByEmailAndCarrierId(email, carrierID) != null){
            var coM = getCarrierOrderByEmailAndCarrierId(email, carrierID);
            BankAccountModel baM = bankAccountRepository.getBankAccountByEmail(coM.getEmail());
            if(!coM.isPaid()) {
                carrierOrderList.remove(coM);
                return true;
            }
            else {
                //jesli zostalo wiecej niz 7 dni do wyjazdu zwroc 90%
                if(LocalDate.now().isBefore(carrierRepostiory.getCarrierById(coM.getCarrierId()).getDate().minusDays(7))){
                    baM.depositMoney(carrierCost * 0.9);
                    carrierOrderList.remove(coM);
                    return true;
                } else{
                    //jesli zostalo mniej niz 7 dni do wyjazdu zwroc 50%
                    baM.depositMoney(carrierCost * 0.5);
                    carrierOrderList.remove(coM);
                    return true;
                }
            }
        }
        return false;

    }
}