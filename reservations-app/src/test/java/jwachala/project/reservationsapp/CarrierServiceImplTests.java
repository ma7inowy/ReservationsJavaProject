package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

@DataJpaTest
public class CarrierServiceImplTests {

    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    @Autowired
    private CarrierRepository carrierRepository;

    @Test
    public void shouldGetCarriersByStartCity() {
        var cM = new CarrierModel();
        cM.setStartCity("startcity1");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getCarriersbyStartCity("startcity1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarriersByStartCityAndDestination() {
        var cM = new CarrierModel();
        cM.setStartCity("startcity1");
        cM.setDestinationCity("destinationcity1");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getCarriersbyStartCityAndDestination("startcity1", "destinationcity1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarriersByCompanyName() {
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getCarriersbyCompanyName("Company1");
        var expected = cM;

        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldMakeAvailabilityMinusOne() {
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setId("123");
        cM.setAvailability(10);
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.availabilityMinusOne("123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(carrierRepository.findById("123").get().getAvailability()).isEqualTo(9);
    }

    @Test
    public void shouldNotMakeAvailabilityMinusOne() {
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setId("123");
        cM.setAvailability(0);
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.availabilityMinusOne("123");
        Assertions.assertThat(actual).isEqualTo(false);
        Assertions.assertThat(carrierRepository.findById("123").get().getAvailability()).isEqualTo(0);
    }

    @Test
    public void shouldGetCarrierById() {
        var cM = new CarrierModel();
        cM.setId("123");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getCarrierById("123");
        var expected = cM;
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierById() {
        var cM = new CarrierModel();
        cM.setId("123");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getCarrierById("1234");
        Assertions.assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldNotDeleteCarrier() {
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);
        var bankServiceProvider = Mockito.mock(BankAccountService.class);

        var cM = new CarrierModel();
        cM.setId("123");
        carrierRepository.save(cM);

        var coM = new CarrierOrderModel();
        coM.setCarrierId("123");
        carrierOrderRepository.save(coM);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrdersByCarrierId("123")).thenReturn(null);
        var sut = new CarrierServiceImpl(carrierRepository, carrierOrderServiceProvider, bankServiceProvider);
        var actual = sut.cancelCarrier("123");
        Assertions.assertThat(actual).isEqualTo(false);
    }

    //    @DisplayName("Cokolwiek")
    @Test
    public void shouldDeleteCarrierWhenIsPaid() {
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);
        var bankServiceProvider = Mockito.mock(BankAccountService.class);
        // carrier
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        carrierRepository.save(cM);
        //carrierorder
        var coM = new CarrierOrderModel();
        coM.setCarrierId("123");
        coM.setEmail("email");
        coM.setPaid(true);
        carrierOrderRepository.save(coM);
        //bank
        var baM = new BankAccountModel("email");

        Mockito.when(bankServiceProvider.getBankAccountByEmail("email")).thenReturn(baM);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrdersByCarrierId("123")).thenReturn(carrierOrderRepository.findAll());
        Mockito.when(bankServiceProvider.addMoneyToAccount("email", 10)).thenReturn(true);
        var sut = new CarrierServiceImpl(carrierRepository, carrierOrderServiceProvider, bankServiceProvider);
        var actual = sut.cancelCarrier("123");
        Assertions.assertThat(carrierRepository.findAll()).isEmpty();
        Assertions.assertThat(actual).isEqualTo(true);
    }

    @Test
    public void shouldGetAllCarriersIterable() {
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        var actual = sut.getAllCarriers();
        var expected = cM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldAddCarrier() {
        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        sut.addCarrier(cM);
        var expected = cM;
        Assertions.assertThat(carrierRepository.findAll()).containsExactly(expected);
    }

    @Test
    public void shouldDeleteCarrier(){
        var cM = new CarrierModel();
        cM.setId("123");
        carrierRepository.save(cM);
        var sut = new CarrierServiceImpl(carrierRepository, null, null);
        sut.deleteCarrier(cM);
        Assertions.assertThat(carrierRepository.findAll()).isEmpty();
    }
}
