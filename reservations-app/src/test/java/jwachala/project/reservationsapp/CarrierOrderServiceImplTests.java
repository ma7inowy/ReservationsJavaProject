package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
}

