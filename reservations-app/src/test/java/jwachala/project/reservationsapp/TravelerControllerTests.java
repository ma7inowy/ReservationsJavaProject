package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TravelerControllerTests {
    @Test
    public void shouldRetrieveAllCarriersFromProvider(){
        var carrierProvider = new MyCarrierRepository();

        var sut = new TravelerController(carrierProvider,null,null);
        var actual = sut.getCarriers();
        Assertions.assertThat(actual).isNotEmpty();
    }
}

class MyCarrierRepository implements CarrierRepository {



    @Override
    public List<CarrierModel> getCarriersbyStartCity(String city) {
        return null;
    }

    @Override
    public List<CarrierModel> getCarriersbyStartCityAndDestination(String startCity, String finishCity) {
        return null;
    }

    @Override
    public List<CarrierModel> getCarriersbyCompanyName(String companyName) {
        return null;
    }

    @Override
    public boolean availabilityMinusOne(String id) {
        return false;
    }

    @Override
    public CarrierModel getCarrierById(String id) {
        return null;
    }

    @Override
    public boolean deleteCarrier(String id) {
        return false;
    }

    @Override
    public List<CarrierModel> getCarrierList() {
        return null;
    }

    @Override
    public CarrierOrderService getCarrierOrderService() {
        return null;
    }

    @Override
    public BankAccountRepository getBankAccountRepository() {
        return null;
    }

    @Override
    public void setCarrierList(List<CarrierModel> carrierList) {

    }

    @Override
    public void setCarrierOrderService(CarrierOrderService carrierOrderService) {

    }

    @Override
    public void setBankAccountRepository(BankAccountRepository bankAccountRepository) {

    }

    @Override
    public List<CarrierModel> getAllCarriers() {
        var result = new ArrayList<CarrierModel>();
        result.add(new CarrierModel());
        return result;
    }
}
