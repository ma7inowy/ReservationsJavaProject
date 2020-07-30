package jwachala.project.reservationsapp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

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
        var actual = sut.getCarriersbyStartCityAndDestination("startcity1","destinationcity1");
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




}
