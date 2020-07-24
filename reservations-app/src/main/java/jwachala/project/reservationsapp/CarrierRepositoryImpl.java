package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// BAZA DANYCH Z WSZYSTKIMI OFERTAMI PRZEWOZOW
@Component
@Data
public class CarrierRepositoryImpl implements CarrierRepository {

    private List<CarrierModel> carrierList;

    @Autowired
    private CarrierOrderService carrierOrderService;

    @Autowired
    BankAccountService bankAccountService;

    public CarrierRepositoryImpl() {
        this.carrierList = new ArrayList<>();
        carrierList.add(new CarrierModel("City1", "destCity1", LocalDate.now().plusDays(8), "Company0",10));
        carrierList.add(new CarrierModel("City2", "destCity2", LocalDate.now().plusDays(5), "Company1",20));
        carrierList.add(new CarrierModel("City2", "destCity4", LocalDate.now().minusDays(3), "Company2",30));
        carrierList.add(new CarrierModel("City3", "destCity4", LocalDate.now().minusDays(10), "Company3",40));
        carrierList.add(new CarrierModel("City5", "destCity5", LocalDate.now().plusDays(1), "Company3",50));

    }

    @Override
    public List<CarrierModel> getCarriersbyStartCity(String city) {
        List<CarrierModel> carrierListbyCity = new ArrayList<>();
        for (var carrier : carrierList) {
            if (carrier.getStartCity().toLowerCase().equals(city.toLowerCase())) {
                carrierListbyCity.add(carrier);
            }
        }
        return carrierListbyCity;
    }

    @Override
    public List<CarrierModel> getCarriersbyStartCityAndDestination(String startCity, String finishCity) {
        List<CarrierModel> carrierListbyCitystartfinish = new ArrayList<>();
        for (var carrier : carrierList) {
            if (carrier.getStartCity().toLowerCase().equals(startCity.toLowerCase()) && carrier.getDestinationCity().toLowerCase().equals(finishCity)) {
                carrierListbyCitystartfinish.add(carrier);
            }
        }
        return carrierListbyCitystartfinish;
    }



    @Override
    public List<CarrierModel> getCarriersbyCompanyName(String companyName) {
        List<CarrierModel> carrierListbyCompanyName = new ArrayList<>();
        for (var carrier : carrierList) {
            if (carrier.getCompanyName().toLowerCase().equals(companyName.toLowerCase())) {
                carrierListbyCompanyName.add(carrier);
            }
        }
        return carrierListbyCompanyName;
    }

    // dodaj zlecenie do Carrier
    @Override
    public boolean availabilityMinusOne(String id) {
        for (CarrierModel cM : carrierList) {
            if (cM.getId().equals(id)) {
                if (cM.getAvailability() > 0) {
                    cM.setAvailability(cM.getAvailability() - 1);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public CarrierModel getCarrierById(String id) {
        for (var carrier : carrierList) {
            if (carrier.getId().equals(id))
                return carrier;
        }
        return null;
    }

    @Override
    public boolean deleteCarrier(String id) {
        List<CarrierOrderModel> coList = carrierOrderService.getCarrierOrdersByCarrierId(id);
        if (coList == null) return false;

        for(CarrierOrderModel coModel : coList){
            // jesli oplacone to oddaj gotowke 100%
            if (coModel.isPaid()) {
                BankAccountModel baModel = bankAccountService.getBankAccountByEmail(coModel.getEmail());
                baModel.depositMoney(getCarrierById(id).getPrice());
            }
        }
        carrierList.remove(getCarrierById(id));
//        carrierOrderService.getCarrierOrderList().removeAll(coList);
        carrierOrderService.removeAllOrders(coList);
        return true;

    }

    @Override
    public CarrierOrderService getCarrierOrderService() {
        return null;
    }

    @Override
    public Iterable<CarrierModel> getAllCarriers() {
        return carrierList;
    }

    @Override
    public void addCarrier(CarrierModel carrierModel) {
        carrierList.add(carrierModel);
    }


}
