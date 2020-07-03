package jwachala.project.reservationsapp;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class CarrierOrderRepository {

    List<CarrierOrder> carrierOrderList;

    public CarrierOrderRepository() {
        this.carrierOrderList = new ArrayList<>();
        carrierOrderList.add(new CarrierOrder("Jan", "Kowalski1", LocalDate.now().plusDays(1), "1", "City1"));
        carrierOrderList.add(new CarrierOrder("Jan", "Kowalski2", LocalDate.now().plusDays(2), "2", "City2"));
        carrierOrderList.add(new CarrierOrder("Jan", "Kowalski3", LocalDate.now().plusDays(3), "3", "City3"));
        carrierOrderList.add(new CarrierOrder("Jan", "Kowalski4", LocalDate.now().plusDays(4), "4", "City4"));
    }

    public List<CarrierOrder> getCarrierOrdersById(String carrierId) {
        List<CarrierOrder> carrierOrdersById = new ArrayList<>();
        for (CarrierOrder carrierOrder : carrierOrderList) {
            if (carrierOrder.getId().equals(carrierId)) {
                carrierOrdersById.add(carrierOrder);
            }
        }
        return carrierOrdersById;
    }
}
