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
    CarrierRepostiory carrierRepostiory; // nie wiem jak inaczej zeby robic getCarrierOrdersByCompanyName

    public CarrierOrderRepository() {
        this.carrierOrderList = new ArrayList<>();
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski1", LocalDate.now().plusDays(1), "1"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski2", LocalDate.now().plusDays(2), "2"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski3", LocalDate.now().plusDays(3), "3"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski4", LocalDate.now().plusDays(3), "3"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski5", LocalDate.now().plusDays(3), "3"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski6", LocalDate.now().plusDays(4), "4"));
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
    // czy tak jest okej? i czy w tej klasie moze byc logika czy bardziej w kontrolerze
    public List<CarrierOrderModel> getCarrierOrdersByCompanyName(String companyName){
        List<CarrierOrderModel> carrierOrderNewList = new ArrayList<>();
        for(var coModel : carrierOrderList){
            for(var cModel : carrierRepostiory.getCarrierList()) {
                if (coModel.getCarrierId().equals(cModel.getId())){
                    if(cModel.getCompanyName().equals(companyName)){
                        carrierOrderNewList.add(coModel);
                    }
                }
            }
        }
        return carrierOrderNewList;
    }

    // na te chwile nie wiem jak to bardziej zoptymalizować
    public List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(String companyName, String startCity){
        List<CarrierOrderModel> carrierOrderByCompanyName = getCarrierOrdersByCompanyName(companyName);
        List<CarrierOrderModel> carrierOrderListByCnAndCity = new ArrayList<>();

        for(var coModel : carrierOrderByCompanyName){
            for(var cModel : carrierRepostiory.getCarrierList()) {
                if (coModel.getCarrierId().equals(cModel.getId())){
                    if(cModel.getCompanyName().equals(companyName)){
                        if(cModel.getStartCity().equals(startCity)) {
                            carrierOrderListByCnAndCity.add(coModel);
                        }
                    }
                }
            }
        }
        return carrierOrderListByCnAndCity;
    }


}
