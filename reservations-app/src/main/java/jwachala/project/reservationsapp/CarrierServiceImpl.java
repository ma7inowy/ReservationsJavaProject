package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// BAZA DANYCH Z WSZYSTKIMI OFERTAMI PRZEWOZOW
@Component
@Data
public class CarrierServiceImpl implements CarrierService {

    private List<CarrierModel> carrierList;

    @Autowired
    private CarrierRepository carrierRepository;

    @Autowired
    private CarrierOrderService carrierOrderService;

    @Autowired
    BankAccountService bankAccountService;

    //testy
    public CarrierServiceImpl(CarrierRepository carrierRepository, CarrierOrderService carrierOrderService, BankAccountService bankAccountService) {
        this.carrierRepository = carrierRepository;
        this.carrierOrderService = carrierOrderService;
        this.bankAccountService = bankAccountService;
    }

    public CarrierServiceImpl() {
        this.carrierList = new ArrayList<>();
        carrierList.add(new CarrierModel("City1", "destCity1", LocalDate.now().plusDays(8), "Company0", 10));
        carrierList.add(new CarrierModel("City2", "destCity2", LocalDate.now().plusDays(1), "Company1", 20));
        carrierList.add(new CarrierModel("City2", "destCity4", LocalDate.now().minusDays(3), "Company2", 30));
        carrierList.add(new CarrierModel("City3", "destCity4", LocalDate.now().minusDays(10), "Company3", 40));
        carrierList.add(new CarrierModel("City5", "destCity5", LocalDate.now().plusDays(1), "Company3", 50));
    }
    @PostConstruct
    public void addCarriersToRepository(){
        carrierRepository.save(new CarrierModel("City1", "destCity1", LocalDate.now().plusDays(8), "Company0", 10));
        carrierRepository.save(new CarrierModel("City2", "destCity2", LocalDate.now().plusDays(8), "Company1", 20));
        carrierRepository.save(new CarrierModel("City2", "destCity4", LocalDate.now().minusDays(3), "Company2", 30));
        carrierRepository.save(new CarrierModel("City3", "destCity4", LocalDate.now().minusDays(10), "Company3", 40));
        carrierRepository.save(new CarrierModel("City5", "destCity5", LocalDate.now().plusDays(1), "Company3", 50));
    }

    @Override
    public List<CarrierModel> getCarriersbyStartCity(String city) {

//        List<CarrierModel> carrierListbyCity = new ArrayList<>();
//        for (var carrier : carrierList) {
//            if (carrier.getStartCity().toLowerCase().equals(city.toLowerCase())) {
//                carrierListbyCity.add(carrier);
//            }
//        }
//        return carrierListbyCity;
        var lista = carrierRepository.findByStartCity(city);
        return lista;
    }

    @Override
    public List<CarrierModel> getCarriersbyStartCityAndDestination(String startCity, String finishCity) {
//        List<CarrierModel> carrierListbyCitystartfinish = new ArrayList<>();
//        for (var carrier : carrierList) {
//            if (carrier.getStartCity().toLowerCase().equals(startCity.toLowerCase()) && carrier.getDestinationCity().toLowerCase().equals(finishCity)) {
//                carrierListbyCitystartfinish.add(carrier);
//            }
//        }
//        return carrierListbyCitystartfinish;
        var lista = carrierRepository.findByStartCityAndDestinationCity(startCity, finishCity);
        return lista;
    }


    @Override
    public List<CarrierModel> getCarriersbyCompanyName(String companyName) {
//        List<CarrierModel> carrierListbyCompanyName = new ArrayList<>();
//        for (var carrier : carrierList) {
//            if (carrier.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
//                carrierListbyCompanyName.add(carrier);
//            }
//        }
//        return carrierListbyCompanyName;
        var lista = carrierRepository.findByCompanyName(companyName);
        return lista;

    }

    @Override
    public boolean availabilityMinusOne(String id) {
//        for (CarrierModel cM : carrierList) {
//            if (cM.getId().equals(id)) {
//                if (cM.getAvailability() > 0) {
//                    cM.setAvailability(cM.getAvailability() - 1);
//                    return true;
//                }
//            }
//        }
//        return false;

        if (carrierRepository.findById(id).isPresent()) {
            var cM = carrierRepository.findById(id).get();
            if (cM.getAvailability() > 0) {
                cM.setAvailability(cM.getAvailability() - 1);
                carrierRepository.save(cM);
                return true;
            } else return false;
        }
        return false;

    }

    @Override
    public CarrierModel getCarrierById(String id) {
//        for (var carrier : carrierList) {
//            if (carrier.getId().equals(id))
//                return carrier;
//        }
//        return null;
        if (carrierRepository.findById(id).isPresent()) {
            var carrier = carrierRepository.findById(id).get();
            return carrier;
        }
        return null;

    }

    // DELETE JAKO ODWOLANIE PRZEWOZU Z JAKICHS POWODOW
    @Override
    public boolean cancelCarrier(String id) {
//        List<CarrierOrderModel> coList = carrierOrderService.getCarrierOrdersByCarrierId(id);
////        if (coList == null) return false;
////
////        for (CarrierOrderModel coModel : coList) {
////            // jesli oplacone to oddaj gotowke 100%
////            if (coModel.isPaid()) {
////                BankAccountModel baModel = bankAccountService.getBankAccountByEmail(coModel.getEmail());
////                bankAccountService.addMoneyToAccount(baModel.getEmail(), getCarrierById(id).getPrice());
////            }
////        }
////        carrierList.remove(getCarrierById(id));
////        carrierOrderService.removeAllOrders(coList);
////        return true;

        List<CarrierOrderModel> coList = carrierOrderService.getCarrierOrdersByCarrierId(id);
        if (coList == null) return false;

        for (CarrierOrderModel coModel : coList) {
            // jesli oplacone to oddaj gotowke 100%
            if (coModel.isPaid()) {
                BankAccountModel baModel = bankAccountService.getBankAccountByEmail(coModel.getEmail());
                bankAccountService.addMoneyToAccount(baModel.getEmail(), getCarrierById(id).getPrice());
            }
        }
        carrierRepository.delete(getCarrierById(id));
        carrierOrderService.removeAllOrders(coList);
        return true;

    }

    @Override
    public Iterable<CarrierModel> getAllCarriers() {
//        return carrierList;
        return carrierRepository.findAll();
    }

    @Override
    public void addCarrier(CarrierModel carrierModel) {
//        carrierList.add(carrierModel);
        carrierRepository.save(carrierModel);
    }

    @Override
    public CarrierRepository getCarrierRepository(){
        return carrierRepository;
    }

    @Override
    public void deleteCarrier(CarrierModel carrierModel){
        carrierRepository.delete(carrierModel);
    }



}
