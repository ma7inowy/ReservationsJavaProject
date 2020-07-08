package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CarrierOrderRepository {

    List<CarrierOrderModel> carrierOrderList;

    @Autowired
    CarrierRepostiory carrierRepostiory;

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

}
