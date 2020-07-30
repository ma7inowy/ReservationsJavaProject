package jwachala.project.reservationsapp;

import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.ArrayList;

public class CarrierOrderServiceImplTests {


    @Test
    public void shouldGetCarrierOrdersByCarrierId() {
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
    public void shouldGetCarrierOrdersByCompanyName() {
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
    public void shouldGetCarrierOrdersByCompanyNameAndCity() {
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
        var actual = sut.getCarrierOrdersByCompanyNameAndCity("Company1", "City1");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(coM.getCarrierId());
        expected.setId(coM.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCarrierIdSorted() {
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
    public void shouldMakePayment() {
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();

        coM.setId("123");

        given.add(coM);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        sut.makePayment("123");
        Assertions.assertThat(given.get(0).isPaid()).isEqualTo(true);
    }

    @Test
    public void shouldGetCarrierOrderById() {
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setId("123");
        given.add(coM);
        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrderById("123");
        var expected = coM;

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierOrderById() {
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setId("123");
        given.add(coM);
        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrderById("1234");

        Assertions.assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldGetCarrierOrderByEmailAndCarrierId() {
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setEmail("email1");
        coM.setCarrierId("1234");
        given.add(coM);
        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrderByEmailAndCarrierId("email1", "1234");
        var expected = coM;

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierOrderByEmailAndCarrierId() {
        var given = new ArrayList<CarrierOrderModel>();
        var coM = new CarrierOrderModel();
        coM.setEmail("email1");
        coM.setCarrierId("1234");
        given.add(coM);
        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrderByEmailAndCarrierId("email1", "12345");

        Assertions.assertThat(actual).isEqualTo(null);
    }


    @Test
    public void shouldRefreshCarrierOrders() {
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var given = new ArrayList<CarrierOrderModel>();
        var cM = new CarrierModel();
        cM.setDate(LocalDate.now().plusDays(6));

        // should be removed from given
        var coM = new CarrierOrderModel();
        coM.setCarrierId(cM.getId());
        coM.setOrderDate(cM.getDate().plusDays(3));

        given.add(coM);
        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
        var sut = new CarrierOrderServiceImpl(given, carrierProvider, null);
        sut.refreshCarrierOrders();
        Assertions.assertThat(given.isEmpty());
    }

    @Test
    public void shouldDeleteUnpaidOrder() {
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var bankProvider = Mockito.mock(BankAccountService.class);
        var cM = new CarrierModel(); // przewoz konkretny
        cM.setId("123");

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var baM = new BankAccountModel("email1");

        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
        Mockito.when(bankProvider.getBankAccountByEmail(coM.getEmail())).thenReturn(baM);

        // jesli nieoplacone
        var sut = new CarrierOrderServiceImpl(given, carrierProvider, bankProvider);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldDeletePaidOrderIfMoreThan7DaysLast() {
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var bankProvider = Mockito.mock(BankAccountService.class);
        var cM = new CarrierModel(); // przewoz konkretny
        cM.setId("123");
        cM.setDate(LocalDate.now().plusDays(8));
        cM.setPrice(10);

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var baM = new BankAccountModel("email1");

        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
        Mockito.when(bankProvider.getBankAccountByEmail(coM.getEmail())).thenReturn(baM);


        //jesli zostalo wiecej niz 7 dni do wyjazdu zwroc 90%
        var sut = new CarrierOrderServiceImpl(given, carrierProvider, bankProvider);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
        Assertions.assertThat(baM.getAccountBalance()).isEqualTo(9);
    }

    @Test
    public void shouldDeletePaidOrderIfLessThan7DaysLast() {
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var bankProvider = Mockito.mock(BankAccountService.class);
        var cM = new CarrierModel(); // przewoz konkretny
        cM.setId("123");
        cM.setDate(LocalDate.now().plusDays(5));
        cM.setPrice(10);

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var baM = new BankAccountModel("email1");

        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
        Mockito.when(bankProvider.getBankAccountByEmail(coM.getEmail())).thenReturn(baM);


        //jesli zostalo mniej niz 7 dni do wyjazdu zwroc 50%
        var sut = new CarrierOrderServiceImpl(given, carrierProvider, bankProvider);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
        Assertions.assertThat(baM.getAccountBalance()).isEqualTo(5);
    }

    @Test
    public void shouldNotDeleteOrder() {
        var carrierProvider = Mockito.mock(CarrierRepository.class);
        var bankProvider = Mockito.mock(BankAccountService.class);
        var cM = new CarrierModel(); // przewoz konkretny
        cM.setId("123");
        cM.setDate(LocalDate.now().plusDays(5));
        cM.setPrice(10);

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var baM = new BankAccountModel("email1");

        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
        Mockito.when(bankProvider.getBankAccountByEmail(coM.getEmail())).thenReturn(baM);

        // false
        var sut = new CarrierOrderServiceImpl(given, carrierProvider, bankProvider);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(false);
    }

    @Test
    public void shouldRemoveAllOrders(){
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        sut.removeAllOrders(given);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldAddOrder(){
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();

        var sut = new CarrierOrderServiceImpl(given, null, null);
        sut.addOrder(coM);
        Assertions.assertThat(given).containsExactly(coM);
    }

    @Test
    public void shouldGetUnpaidOrders(){
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.unpaidOrders("email1");
        var expected = coM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrderListIterable(){
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        var actual = sut.getCarrierOrderListIterable();
        var expected = coM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldRemoveCarrierOrder(){
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(given, null, null);
        sut.removeCarrierOrder(coM);
        Assertions.assertThat(given).isEmpty();
    }



}

