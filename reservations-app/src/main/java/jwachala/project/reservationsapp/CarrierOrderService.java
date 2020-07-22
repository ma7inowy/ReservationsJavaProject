package jwachala.project.reservationsapp;

import java.util.List;

public interface CarrierOrderService {
    List<CarrierOrderModel> getCarrierOrdersByCarrierId(String carrierId);

    List<CarrierOrderModel> getCarrierOrdersByCompanyName(String companyName);

    // na te chwile nie wiem jak to bardziej zoptymalizować
    List<CarrierOrderModel> getCarrierOrdersByCompanyNameAndCity(String companyName, String startCity);

    List<CarrierOrderModel> getCarrierOrdersByCarrierIdSorted(String carrierId);

    void sort(List<CarrierOrderModel> coList);

    void makePayment(String id);

    CarrierOrderModel getCarrierOrderById(String id);

    CarrierOrderModel getCarrierOrderByEmailAndCarrierId(String email, String carrierId);

    void refreshCarrierOrders();

    boolean deleteOrder(String email, String carrierID);

    void removeAllOrders(List<CarrierOrderModel> coList);

    void addOrder(CarrierOrderModel model);

    Iterable<CarrierOrderModel> unpaidOrders(String email);

    Iterable<CarrierOrderModel> getCarrierOrderListIterable();
}
