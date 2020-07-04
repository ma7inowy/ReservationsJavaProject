package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class CarrierOrderRepository {

    List<CarrierOrderModel> carrierOrderList;

    public CarrierOrderRepository() {
        this.carrierOrderList = new ArrayList<>();
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski1", LocalDate.now().plusDays(1), "1"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski2", LocalDate.now().plusDays(2), "2"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski3", LocalDate.now().plusDays(3), "3"));
        carrierOrderList.add(new CarrierOrderModel("Jan", "Kowalski4", LocalDate.now().plusDays(4), "4"));
    }

    public List<CarrierOrderModel> getCarrierOrdersByCarrierId(String carrierId) {
        List<CarrierOrderModel> carrierOrdersByCarrierId = new ArrayList<>();
        for (CarrierOrderModel carrierOrder : carrierOrderList) {
            if (carrierOrder.getCarrierId().equals(carrierId)) {
                carrierOrdersByCarrierId.add(carrierOrder);
            }
        }
        return carrierOrdersByCarrierId;
    }
}
