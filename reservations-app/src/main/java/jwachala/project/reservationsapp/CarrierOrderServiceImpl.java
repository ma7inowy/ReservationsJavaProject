package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


// BAZA DANYCH O ZAKUPIONYCH BILETACH/MIEJSCA U PRZEWOŹNIKA
@Service
public class CarrierOrderServiceImpl implements CarrierOrderService {

    //testy
    public CarrierOrderServiceImpl(List<CarrierOrderModel> carrierOrderList, CarrierRepository carrierRepository, BankAccountService bankAccountService) {
        this.carrierOrderList = carrierOrderList;
        this.carrierRepository = carrierRepository;
        this.bankAccountService = bankAccountService;
    }

    private List<CarrierOrderModel> carrierOrderList;

    private CarrierRepository carrierRepository;

    private BankAccountService bankAccountService;

    //spring
    @Autowired
    public CarrierOrderServiceImpl(CarrierRepository carrierRepository, BankAccountService bankAccountService) {
        this(new ArrayList<>(), carrierRepository, bankAccountService);

    }

    @PostConstruct
    public void init() {
        carrierOrderList.add(new CarrierOrderModel("jankowalski1@wp.pl", LocalDate.now().minusDays(11), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.get(0).setPaid(true);
        carrierOrderList.add(new CarrierOrderModel("jankowalski2@wp.pl", LocalDate.now().minusDays(2), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski3@wp.pl", LocalDate.now().plusDays(7), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.get(2).setPaid(true);
        carrierOrderList.add(new CarrierOrderModel("jankowalski4@wp.pl", LocalDate.now().plusDays(5), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski5@wp.pl", LocalDate.now().plusDays(3), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski6@wp.pl", LocalDate.now().plusDays(5), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.get(5).setPaid(true);
        carrierOrderList.add(new CarrierOrderModel("jankowalski7@wp.pl", LocalDate.now().plusDays(4), carrierRepository.getCarrierList().get(0).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski8@wp.pl", LocalDate.now().plusDays(3), carrierRepository.getCarrierList().get(1).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski9@wp.pl", LocalDate.now().plusDays(3), carrierRepository.getCarrierList().get(2).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski10@wp.pl", LocalDate.now().plusDays(3), carrierRepository.getCarrierList().get(3).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().plusDays(4), carrierRepository.getCarrierList().get(3).getId()));
        carrierOrderList.add(new CarrierOrderModel("jankowalski11@wp.pl", LocalDate.now().minusDays(14), carrierRepository.getCarrierList().get(2).getId()));
    }

    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCarrierId(String carrierId) {
        List<CarrierOrderModel> carrierOrdersByCarrierId = new ArrayList<>();
        for (var carrierOrder : carrierOrderList) {
            if (carrierOrder.getCarrierId().equals(carrierId)) {
                carrierOrdersByCarrierId.add(carrierOrder);
            }
        }
        return carrierOrdersByCarrierId;
    }

    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCompanyName(String companyName) {
        List<CarrierOrderModel> carrierOrderNewList = new ArrayList<>();
        for (var coModel : carrierOrderList) {
            for (var cModel : carrierRepository.getAllCarriers()) {
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
    @Override
    public List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(String companyName, String startCity) {
        List<CarrierOrderModel> carrierOrderByCompanyName = getCarrierOrdersByCompanyName(companyName);
        List<CarrierOrderModel> carrierOrderListByCnAndCity = new ArrayList<>();

        for (var coModel : carrierOrderByCompanyName) {
            for (var cModel : carrierRepository.getAllCarriers()) {
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

    @Override
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

    @Override
    public void sort(List<CarrierOrderModel> coList) {
        Collections.sort(coList, new CarrierOrderComparator());
    }

    @Override
    public void makePayment(String id) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getId().equals(id))
                co.setPaid(true);
        }
    }

    @Override
    public CarrierOrderModel getCarrierOrderById(String id) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getId().equals(id))
                return co;
        }
        return null;
    }

    @Override
    public CarrierOrderModel getCarrierOrderByEmailAndCarrierId(String email, String carrierId) {
        for (CarrierOrderModel co : carrierOrderList) {
            if (co.getEmail().equals(email) && co.getCarrierId().equals(carrierId))
                return co;
        }
        return null;
    }

    @Override
    public void refreshCarrierOrders() {
        var coList = new ArrayList<CarrierOrderModel>();

        for (CarrierOrderModel co : carrierOrderList) {
            if (!co.isPaid()) {
                // if order is still not paid and time remaining to carrier start is shorter than 5 days it needs to be removed
                if (LocalDate.now().isAfter(carrierRepository.getCarrierById(co.getCarrierId()).getDate().minusDays(5)))
                    coList.add(co);
            }
        }
        if (!coList.isEmpty()) {
            for (CarrierOrderModel co1 : coList) {
                carrierOrderList.remove(co1);
            }
        }
    }

    @Override
    public boolean deleteOrder(String email, String carrierID) {
        double carrierCost = carrierRepository.getCarrierById(carrierID).getPrice(); // na razie na sztywno wpisuje cene przejazdu
        if (getCarrierOrderByEmailAndCarrierId(email, carrierID) != null) {
            var coM = getCarrierOrderByEmailAndCarrierId(email, carrierID);
            BankAccountModel baM = bankAccountService.getBankAccountByEmail(coM.getEmail());
            if (!coM.isPaid()) {
                carrierOrderList.remove(coM);
                return true;
            } else {
                //jesli zostalo wiecej niz 7 dni do wyjazdu zwroc 90%
                if (LocalDate.now().isBefore(carrierRepository.getCarrierById(coM.getCarrierId()).getDate().minusDays(7))) {
                    baM.depositMoney(carrierCost * 0.9);
                    carrierOrderList.remove(coM);
                    return true;
                } else {
                    //jesli zostalo mniej niz 7 dni do wyjazdu zwroc 50%
                    baM.depositMoney(carrierCost * 0.5);
                    carrierOrderList.remove(coM);
                    return true;
                }
            }
        }
        return false;

    }

    @Override
    public void removeAllOrders(List<CarrierOrderModel> coList) {
        carrierOrderList.removeAll(coList);
    }

    @Override
    public void addOrder(CarrierOrderModel model) {
        carrierOrderList.add(model);
    }

    @Override
    public Iterable<CarrierOrderModel> unpaidOrders(String email) {

        var result = new ArrayList<CarrierOrderModel>();
        for (CarrierOrderModel coModel : carrierOrderList) {
            if (!coModel.isPaid() && coModel.getEmail().equals(email)) {
                result.add(coModel);
            }
        }
        return result;
    }

    @Override
    public Iterable<CarrierOrderModel> getCarrierOrderListIterable() {
        return carrierOrderList;
    }

    @Override
    public void removeCarrierOrder(CarrierOrderModel i) {
        carrierOrderList.remove(i);
    }
}