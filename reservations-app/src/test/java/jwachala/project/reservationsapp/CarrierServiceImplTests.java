package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

public class CarrierServiceImplTests {
    @Test
    public void shouldGetCarriersByStartCity() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setStartCity("startcity1");
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getCarriersbyStartCity("startcity1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarriersByStartCityAndDestination() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setStartCity("startcity1");
        cM.setDestinationCity("destinationcity1");
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getCarriersbyStartCityAndDestination("startcity1", "destinationcity1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarriersByCompanyName() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getCarriersbyCompanyName("Company1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldMakeAvailabilityMinusOne() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setId("123");
        cM.setAvailability(10);
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.availabilityMinusOne("123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(cM.getAvailability()).isEqualTo(9);
    }

    @Test
    public void shouldNotMakeAvailabilityMinusOne() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setId("123");
        cM.setAvailability(0);
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.availabilityMinusOne("123");
        Assertions.assertThat(actual).isEqualTo(false);
        Assertions.assertThat(cM.getAvailability()).isEqualTo(0);
    }

    @Test
    public void shouldGetCarrierById() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getCarrierById("123");
        var expected = cM;
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierById() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getCarrierById("1234");
        Assertions.assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldNotDeleteCarrier() {
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);
        var bankServiceProvider = Mockito.mock(BankAccountService.class);

        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        given.add(cM);

        var coList = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setCarrierId("123");
        coList.add(coM);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrdersByCarrierId("123")).thenReturn(null);
        var sut = new CarrierServiceImpl(given, carrierOrderServiceProvider, bankServiceProvider);
        var actual = sut.cancelCarrier("123");
        Assertions.assertThat(actual).isEqualTo(false);
    }

//    @DisplayName("Cokolwiek")
    @Test
    public void shouldDeleteCarrierWhenIsPaid() {
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);
        var bankServiceProvider = Mockito.mock(BankAccountService.class);
        // carrier
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        given.add(cM);
        //carrierorder
        var coList = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setCarrierId("123");
        coM.setEmail("email");
        coM.setPaid(true);
        coList.add(coM);
        //bank
        var baM = new BankAccountModel("email");

        Mockito.when(bankServiceProvider.getBankAccountByEmail("email")).thenReturn(baM);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrdersByCarrierId("123")).thenReturn(coList);
        Mockito.when(bankServiceProvider.addMoneyToAccount("email",10)).thenReturn(true);
        var sut = new CarrierServiceImpl(given, carrierOrderServiceProvider, bankServiceProvider);
        var actual = sut.cancelCarrier("123");
        Assertions.assertThat(given).isEmpty();
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    public void shouldGetAllCarriersIterable() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        given.add(cM);
        var sut = new CarrierServiceImpl(given, null, null);
        var actual = sut.getAllCarriers();
        var expected = cM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldAddCarrier() {
        var given = new ArrayList<CarrierModel>();
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        var sut = new CarrierServiceImpl(given, null, null);
        sut.addCarrier(cM);
        var expected = cM;
        Assertions.assertThat(given).containsExactly(expected);
    }
}
