package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class CarrierControllerTests {

    @Test
    public void shouldGetAllCarriers(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("my company name");
        given.add(model);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(given); // why ok

        var sut = new CarrierController(carrierProvider, null, null);
        var actual = sut.getCarriers();

        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setCompanyName("my company name");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByCity(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setStartCity("City1");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyStartCity("City1")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new CarrierController(carrierProvider, null, null);
        var actual = sut.getCarriersByCity("City1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldBeRealized(){
        //CZY NA PEWNO OKEJ?
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var sut = new CarrierController(carrierProvider, null, null);
        var model = new CarrierModel();
        Mockito.when(carrierProvider.getCarrierById(model.getId())).thenReturn(model); // ustalam co ma sie stac?

        var dto = new CarrierDTO();
        dto.setId(model.getId());
        dto.setRealized(true);
        var actual = sut.isRealized(model.getId());
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok(dto));
    }

    @Test
    public void shouldGetAllOrders(){
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub");
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrderListIterable()).thenReturn(given); // why ok

        var sut = new CarrierController(null, carrierProvider, null);
        var actual = sut.getCarrierOrders();

        var expected = new CarrierOrderDTO();
        expected.setCarrierId(model.getId());
        expected.setEmail("jakub");
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

}
