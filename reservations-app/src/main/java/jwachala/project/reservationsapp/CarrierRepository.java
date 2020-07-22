package jwachala.project.reservationsapp;

import java.util.List;

public interface CarrierRepository {
    List<CarrierModel> getCarriersbyStartCity(String city);

    List<CarrierModel> getCarriersbyStartCityAndDestination(String startCity, String finishCity);

    List<CarrierModel> getCarriersbyCompanyName(String companyName);

    // dodaj zlecenie do Carrier
    boolean availabilityMinusOne(String id);

    CarrierModel getCarrierById(String id);

    boolean deleteCarrier(String id);

    List<CarrierModel> getCarrierList();

    CarrierOrderService getCarrierOrderService();

    BankAccountRepository getBankAccountRepository();

//    void setCarrierList(List<CarrierModel> carrierList);
//
//    void setCarrierOrderService(CarrierOrderService carrierOrderService);
//
//    void setBankAccountRepository(BankAccountRepository bankAccountRepository);

    List<CarrierModel> getAllCarriers();
}
