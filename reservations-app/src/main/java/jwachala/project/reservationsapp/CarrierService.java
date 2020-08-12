package jwachala.project.reservationsapp;

import java.util.List;

public interface CarrierService {
    List<CarrierModel> getCarriersbyStartCity(String city);

    List<CarrierModel> getCarriersbyStartCityAndDestination(String startCity, String finishCity);

    List<CarrierModel> getCarriersbyCompanyName(String companyName);

    // dodaj zlecenie do Carrier
    boolean availabilityMinusOne(String id);

    CarrierModel getCarrierById(String id);

    boolean cancelCarrier(String id);

    Iterable<CarrierModel> getAllCarriers();

    void addCarrier(CarrierModel carrierModel);

    CarrierRepository getCarrierRepository();

    void deleteCarrier(CarrierModel carrierModel);
}
