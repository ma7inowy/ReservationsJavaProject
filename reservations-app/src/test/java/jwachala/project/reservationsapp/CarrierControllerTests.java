package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class CarrierControllerTests {

    @Test
    public void shouldGetAllCarriers() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("my company name");
        given.add(model);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(given);

        var sut = new CarrierController(carrierProvider, null, null, null);
        var actual = sut.getCarriers();

        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setCompanyName("my company name");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByCity() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setStartCity("City1");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyStartCity("City1")).thenReturn(given);

        var sut = new CarrierController(carrierProvider, null, null, null);
        var actual = sut.getCarriersByCity("City1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldBeRealized() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var sut = new CarrierController(carrierProvider, null, null, null);
        var model = new CarrierModel();
        Mockito.when(carrierProvider.getCarrierById(model.getId())).thenReturn(model);

        var dto = new CarrierDTO();
        dto.setId(model.getId());
        dto.setRealized(true);
        var actual = sut.isRealized(model.getId());
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.ok(dto));
    }

    @Test
    public void shouldGetAllOrders() {
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub");
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrderListIterable()).thenReturn(given);

        var sut = new CarrierController(null, carrierProvider, null, null);
        var actual = sut.getCarrierOrders();

        var expected = new CarrierOrderDTO();
        expected.setCarrierId(model.getId());
        expected.setEmail("jakub");
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    //NIE WIEM CZY OK
    @Test
    public void shouldGetAllOrdersByCompanyName() {
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub");
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrdersByCompanyName("Company1")).thenReturn(given);

        var sut = new CarrierController(null, carrierProvider, null, null);
        var actual = sut.getCarrierOrdersByCompanyName("Company1");

        var expected = new CarrierOrderDTO();
        expected.setCarrierId(model.getId());
        expected.setEmail("jakub");
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllOrdersByCompanyNameAndCity() {
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub");
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrdersByCompanyNameAndCity("Company1", "City1")).thenReturn(given);

        var sut = new CarrierController(null, carrierProvider, null, null);
        var actual = sut.getCarrierOrdersByCompanyNameAndCity("Company1", "City1");

        var expected = new CarrierOrderDTO();
        expected.setCarrierId(model.getId());
        expected.setEmail("jakub");
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetHistoryByCompanyName() {
        var carrierProvider = Mockito.mock(CarrierHistoryService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("Company1");
        given.add(model);
        Mockito.when(carrierProvider.getHistoryCarriersbyCompanyName("Company1")).thenReturn(given);

        var sut = new CarrierController(null, null, carrierProvider, null);
        var actual = sut.getHistoryByCompanyName("Company1");

        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setCompanyName("Company1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldDeleteOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var model = new CarrierModel();
        Mockito.when(carrierProvider.cancelCarrier(model.getId())).thenReturn(true);

        var sut = new CarrierController(carrierProvider, null, null, null);
        var actual = sut.deleteCarrier(model.getId());

        var expected = ResponseEntity.noContent().build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotDeleteOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var model = new CarrierModel();
        Mockito.when(carrierProvider.cancelCarrier(model.getId())).thenReturn(false);
        var sut = new CarrierController(carrierProvider, null, null, null);
        var actual = sut.deleteCarrier(model.getId());
        var expected = ResponseEntity.notFound().build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCreateCarrier() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var resourceProvider = Mockito.mock(ResourceLocationBuilder.class);
        var sut = new CarrierController(carrierProvider, null, null, resourceProvider);
        var given = new CarrierDTO();
        given.setStartCity("City1");
        given.setDestinationCity("Destination1");
        given.setCompanyName("Company1");
        given.setDate(LocalDate.now());
        sut.createCarrier(given);

        var expected = new CarrierModel();
        expected.setStartCity("City1");
        expected.setDestinationCity("Destination1");
        expected.setCompanyName("Company1");
        expected.setDate(given.getDate());
        var argument = ArgumentCaptor.forClass(CarrierModel.class);
        Mockito.verify(carrierProvider).addCarrier(argument.capture());

        expected.setId(argument.getValue().getId());
        Assertions.assertThat(argument.getValue()).isEqualTo(expected);

    }

    @Test
    public void shouldReturnLocationOfCreatedCarrier() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };
        var sut = new CarrierController(carrierProvider, null, null, resourceProvider);

        var given = new CarrierDTO();
        var actual = sut.createCarrier(given);
        Assertions.assertThat(actual).isEqualTo(ResponseEntity.created(uri.get()).build());
    }

}
