package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;

public class CarrierOrderServiceImplTests {


    @Test
    public void shouldGetCarrierOrdersByCarrierId(){
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        var model2 = new CarrierOrderModel();
        model.setCarrierId("123");
        model2.setCarrierId("1234");
        given.add(model);
        given.add(model2);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrdersByCarrierId("123");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(model.getCarrierId());
        expected.setId(model.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCompanyName(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        var coM2 = new CarrierOrderModel();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        var cMList = new ArrayList<CarrierModel>();

        cMList.add(cM);
        coM.setCarrierId(cM.getId());
        coM2.setCarrierId("123");
        given.add(coM);
        given.add(coM2);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(cMList);

        var sut = new CarrierOrderServiceImpl(given, carrierProvider, null);
        var actual = sut.getCarrierOrdersByCompanyName("Company1");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(coM.getCarrierId());
        expected.setId(coM.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCompanyNameAndCity(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        var coM2 = new CarrierOrderModel();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setStartCity("City1");
        var cMList = new ArrayList<CarrierModel>();

        cMList.add(cM);
        coM.setCarrierId(cM.getId());
        coM2.setCarrierId("123");
        given.add(coM);
        given.add(coM2);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(cMList);

        var sut = new CarrierOrderServiceImpl(given, carrierProvider, null);
        var actual = sut.getCarrierOrdersByCompanyNameAndCity("Company1","City1");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(coM.getCarrierId());
        expected.setId(coM.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCarrierIdSorted(){
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        var model2 = new CarrierOrderModel();
        var model3 = new CarrierOrderModel();
        var model4 = new CarrierOrderModel();

        model.setCarrierId("123");
        model.setPaid(true);
        model.setOrderDate(LocalDate.now().minusDays(1));

        model2.setCarrierId("123");
        model2.setPaid(true);
        model2.setOrderDate(LocalDate.now());

        model3.setCarrierId("123");
        model3.setPaid(false);
        model3.setOrderDate(LocalDate.now().minusDays(3));

        model4.setCarrierId("123");
        model4.setPaid(false);
        model4.setOrderDate(LocalDate.now().minusDays(1));

        given.add(model2);
        given.add(model);
        given.add(model4);
        given.add(model3);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrdersByCarrierIdSorted("123");

        var expected = new ArrayList<CarrierOrderModel>();
        expected.add(model);
        expected.add(model2);
        expected.add(model3);
        expected.add(model4);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    // nie wiem czy tak moze byc
    @Test
    public void shouldMakePayment(){
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setId("123");
        given.add(coM);
        var sut = new CarrierOrderServiceImpl(given, null, null);
        sut.makePayment("123");
        Assertions.assertThat(given.get(0).isPaid()).isEqualTo(true);
    }





}

