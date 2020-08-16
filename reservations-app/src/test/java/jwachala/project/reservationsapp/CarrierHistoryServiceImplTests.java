package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;

@DataJpaTest
public class CarrierHistoryServiceImplTests {

    @Autowired
    private CarrierHistoryRepository carrierHistoryRepository;

    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    @Test
    public void shouldGetHistoryCarriersByCompanyName() {
        var model = new CarrierModel();
        var model2 = new CarrierModel();
        model.setCompanyName("Company1");
        model2.setCompanyName("Company2");
        carrierHistoryRepository.save(model);
        carrierHistoryRepository.save(model2);
        var sut = new CarrierHistoryServiceImpl(carrierHistoryRepository, null, null);
        var actual = sut.getHistoryCarriersbyCompanyName("Company1");
        var expected = model;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldRefreshHistory() {
        var model = new CarrierModel();
        model.setDate(LocalDate.now().minusDays(2));
        var model2 = new CarrierModel();
        model2.setDate(LocalDate.now().plusDays(2));

        carrierHistoryRepository.save(model);
        carrierHistoryRepository.save(model2);

        //carrier
        var carrierProvider = Mockito.mock(CarrierService.class);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(carrierHistoryRepository.findAll());
        // carrierorder
        var carrierOrderServiceProvider = Mockito.mock(CarrierOrderService.class);

        var modelOrder1 = new CarrierOrderModel();
        var modelOrder2 = new CarrierOrderModel();
        modelOrder1.setCarrierId(model.getId());
        modelOrder2.setCarrierId(model2.getId());
        carrierOrderRepository.save(modelOrder1);
        carrierOrderRepository.save(modelOrder2);
        Mockito.when(carrierOrderServiceProvider.getCarrierOrderListIterable()).thenReturn(carrierOrderRepository.findAll());

        var sut = new CarrierHistoryServiceImpl(carrierHistoryRepository, carrierProvider, carrierOrderServiceProvider);
        var actual = sut.refreshHistory();
        var expected = model;
        Assertions.assertThat(actual).containsExactly(expected);
    }

}
