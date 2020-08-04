package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class TravelerControllerTests {
    @Test
    public void shouldGetAllCarriers() {

        var carrierProvider = Mockito.mock(CarrierService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("my company name");
        given.add(model);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(given); // why ok

        var sut = new TravelerController(carrierProvider, null, null, null);
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
//        var model2 = new CarrierModel();
        model.setStartCity("City1");
        given.add(model);
//        given.add(model2);
        Mockito.when(carrierProvider.getCarriersbyStartCity("City1")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null, null);
        var actual = sut.getCarriersByCity("City1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByStartAndDestinationCity() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setStartCity("City1");
        model.setDestinationCity("City2");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyStartCityAndDestination("City1", "City2")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null, null);
        var actual = sut.getCarriersbyStartCityAndDestination("City1", "City2");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        expected.setDestinationCity("City2");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByCompanyName() {
        var carrierProvider = Mockito.mock(CarrierService.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("Company1");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyCompanyName("Company1")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null, null);
        var actual = sut.getCarriersByCompanyName("Company1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setCompanyName("Company1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    // NIETESTOWALNE createOrder() bo nie sprawdze id modelu w metodzie createOrder
//    @Test
//    public void shouldCreateOrder(){
//        var carrierProvider = Mockito.mock(CarrierService.class);
//        var carrierOrderProvider = Mockito.mock(CarrierOrderService.class);
//
//        var dto = new CarrierOrderDTO();
//        dto.setCarrierId("123");
//        dto.setOrderDate(LocalDate.now());
//        dto.setEmail("jakub@wp.pl");
//        var model = new CarrierOrderModel();
//        model.setEmail(dto.getEmail());
//        model.setOrderDate(dto.getOrderDate());
//        model.setCarrierId(dto.getCarrierId());
//        model.setId("");
//
//        var uri = new AtomicReference<URI>();
//        ResourceLocationBuilder resourceProvider = id -> {
//            uri.set(URI.create(id));
//            return uri.get();
//        };
//
//        Mockito.when(carrierOrderProvider.addOrder(model)).thenReturn(true);
//        var sut = new TravelerController(carrierProvider, carrierOrderProvider, null,resourceProvider);
//        var actual = sut.createOrder(dto);
//        var expected = ResponseEntity.created(uri.get()).build();
//        Assertions.assertThat(actual).isEqualTo(expected);
//    }

    @Test
    public void shouldPayOrder() {
        var carrierOrderProvider = Mockito.mock(CarrierOrderService.class);
        //CarrierOrderModel
        CarrierOrderModel coM = new CarrierOrderModel();
        coM.setId("123");
        coM.setEmail("email");
        coM.setCarrierId("1234");
        coM.setOrderDate(LocalDate.now());
        //resourceLocationBuilder
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };

        Mockito.when(carrierOrderProvider.getCarrierOrderByEmailAndCarrierId("email", "1234")).thenReturn(coM);
        Mockito.when(carrierOrderProvider.payForOrder("email","1234")).thenReturn(true);
        var sut = new TravelerController(null, carrierOrderProvider, null,resourceProvider);
        var actual = sut.payOrder("email","1234");
        var expected = ResponseEntity.created(uri.get()).build();
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldNotPayOrder() {
        var carrierOrderProvider = Mockito.mock(CarrierOrderService.class);
        //CarrierOrderModel
        CarrierOrderModel coM = new CarrierOrderModel();
        coM.setId("123");
        coM.setEmail("email");
        coM.setCarrierId("1234");
        coM.setOrderDate(LocalDate.now());
        //resourceLocationBuilder
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };

        Mockito.when(carrierOrderProvider.getCarrierOrderByEmailAndCarrierId("email", "1234")).thenReturn(coM);
        Mockito.when(carrierOrderProvider.payForOrder("email","1234")).thenReturn(false);
        var sut = new TravelerController(null, carrierOrderProvider, null,resourceProvider);
        var actual = sut.payOrder("email","1234");
        var expected = ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, Too less money on your bank account!");
        Assertions.assertThat(actual).isEqualTo(expected);

    }

    @Test
    public void shouldGetNotPayedOrders() {
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub@wp.pl");
        given.add(model);
        Mockito.when(carrierProvider.unpaidOrders("jakub@wp.pl")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(null, carrierProvider, null, null);
        var actual = sut.getNotPayedOrders("jakub@wp.pl");
        var expected = new CarrierOrderTravelerDTO();
        expected.setEmail(model.getEmail());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCarrierIdSorted() {
        // CZY ABY NA PEWNO OK?
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrdersByCarrierIdSorted(model.getCarrierId())).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(null, carrierProvider, null, null);
        var actual = sut.getCarrierOrdersByCarrierIdSorted(model.getCarrierId());
        var expected = new CarrierOrderTravelerDTO();
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldDeleteOrder() {
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        Mockito.when(carrierProvider.deleteOrder("email", "carrierId")).thenReturn(true); // ustalam co ma sie stac?
        var sut = new TravelerController(null, carrierProvider, null, null);
        var actual = sut.deleteOrder("email", "carrierId");
        var expected = ResponseEntity.noContent().build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }


}



