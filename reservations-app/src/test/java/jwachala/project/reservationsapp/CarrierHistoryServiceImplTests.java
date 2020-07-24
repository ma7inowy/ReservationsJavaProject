package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;

public class CarrierHistoryServiceImplTests {

    @Test
    public void shouldGetHistoryCarriersByCompanyName() {
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        var model2 = new CarrierModel();
        model.setCompanyName("Company1");
        model2.setCompanyName("Company2");
        given.add(model);
        given.add(model2);
        var sut = new CarrierHistoryServiceImpl(given, null, null);
        var actual = sut.getHistoryCarriersbyCompanyName("Company1");
        var expected = model;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldRefreshHistory() {
        var carrierModelHistoryList = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setDate(LocalDate.now().minusDays(2));
        var model2 = new CarrierModel();
        model2.setDate(LocalDate.now().plusDays(2));

        var given = new ArrayList<CarrierModel>();
        given.add(model);
        given.add(model2);

        //carrier
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        Mockito.when(carrierProvider.getCarrierList()).thenReturn(given);
        // carrierorder
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);

        var modelOrder1 = new CarrierOrderModel();
        var modelOrder2 = new CarrierOrderModel();
        modelOrder1.setCarrierId(model.getId());
        modelOrder2.setCarrierId(model2.getId());
        var givenOrderList = new ArrayList<CarrierOrderModel>();
        givenOrderList.add(modelOrder1);
        givenOrderList.add(modelOrder2);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrderListIterable()).thenReturn(givenOrderList);

        var sut = new CarrierHistoryServiceImpl(carrierModelHistoryList, carrierProvider, carrierOrderServiceProvider);
        var actual = sut.refreshHistory();
        var expected = model;
        Assertions.assertThat(actual).containsExactly(expected);
    }

}
