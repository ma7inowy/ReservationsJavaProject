package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

public class TravelerControllerTests {
    @Test
    public void shouldGetAllCarriers() {

        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("my company name");
        given.add(model);
        Mockito.when(carrierProvider.getAllCarriers()).thenReturn(given); // why ok

        var sut = new TravelerController(carrierProvider, null, null);
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
//        var model2 = new CarrierModel();
        model.setStartCity("City1");
        given.add(model);
//        given.add(model2);
        Mockito.when(carrierProvider.getCarriersbyStartCity("City1")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null);
        var actual = sut.getCarriersByCity("City1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByStartAndDestinationCity(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setStartCity("City1");
        model.setDestinationCity("City2");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyStartCityAndDestination("City1","City2")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null);
        var actual = sut.getCarriersbyStartCityAndDestination("City1","City2");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setStartCity("City1");
        expected.setDestinationCity("City2");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAllCarriersByCompanyName(){
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierModel>();
        var model = new CarrierModel();
        model.setCompanyName("Company1");
        given.add(model);
        Mockito.when(carrierProvider.getCarriersbyCompanyName("Company1")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(carrierProvider, null, null);
        var actual = sut.getCarriersByCompanyName("Company1");
        var expected = new CarrierDTO();
        expected.setId(model.getId());
        expected.setCompanyName("Company1");
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldCreateOrder(){
//        var carrierProvider = Mockito.mock(CarrierRepository.class);
//
//        var dto = new CarrierOrderDTO();
//        dto.setCarrierId("123");
//        dto.setOrderDate(LocalDate.now());
//        dto.setEmail("jakub@wp.pl");
//        var model = new CarrierOrderModel();
//        model.setEmail(dto.getEmail());
//        model.setOrderDate(dto.getOrderDate());
//        model.setCarrierId(dto.getCarrierId());
//
//        Mockito.when(carrierProvider.availabilityMinusOne("1")).thenReturn(true); // ustalam co ma sie stac?
//        var sut = new TravelerController(carrierProvider, null, null);
//        var actual = sut.createOrder(dto);




//        var given = true;
//        var model2 = new CarrierModel();
//        var carrierProvider = Mockito.mock(CarrierRepository.class);
//        Mockito.when(carrierProvider.availabilityMinusOne("1")).thenReturn(true); // ustalam co ma sie stac?


//        Assertions.assertThat(dto.getEmail()).isEqualTo(model.getEmail());
        //NAJPIERW IFY USUNAC

    }

    @Test
    public void shouldPayOrder(){

    }

    @Test
    public void shouldGetNotPayedOrders(){
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        model.setEmail("jakub@wp.pl");
        given.add(model);
        Mockito.when(carrierProvider.unpaidOrders("jakub@wp.pl")).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(null, carrierProvider, null);
        var actual = sut.getNotPayedOrders("jakub@wp.pl");
        var expected = new CarrierOrderTravelerDTO();
        expected.setEmail(model.getEmail());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCarrierIdSorted(){
        // CZY ABY NA PEWNO OK?
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        var given = new ArrayList<CarrierOrderModel>();
        var model = new CarrierOrderModel();
        given.add(model);
        Mockito.when(carrierProvider.getCarrierOrdersByCarrierIdSorted(model.getCarrierId())).thenReturn(given); // ustalam co ma sie stac?

        var sut = new TravelerController(null, carrierProvider, null);
        var actual = sut.getCarrierOrdersByCarrierIdSorted(model.getCarrierId());
        var expected = new CarrierOrderTravelerDTO();
        expected.setCarrierId(model.getCarrierId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldDeleteOrder(){
        var carrierProvider = Mockito.mock(CarrierOrderService.class);
        Mockito.when(carrierProvider.deleteOrder("email","carrierId")).thenReturn(true); // ustalam co ma sie stac?
        var sut = new TravelerController(null, carrierProvider, null);
        var actual = sut.deleteOrder("email", "carrierId");
        var expected = ResponseEntity.noContent().build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }


}


