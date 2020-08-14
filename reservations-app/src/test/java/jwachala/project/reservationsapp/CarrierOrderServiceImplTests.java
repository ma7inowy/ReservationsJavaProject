package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@DataJpaTest
public class CarrierOrderServiceImplTests {

    @Autowired
    private CarrierOrderRepository carrierOrderRepository;

    @Autowired
    private CarrierRepository carrierRepository;

    @Test
    public void shouldGetCarrierOrdersByCarrierId() {
        Assertions.assertThat(carrierOrderRepository).isNotNull();
        var model = new CarrierOrderModel();
        var model2 = new CarrierOrderModel();
        model.setCarrierId("123");
        model2.setCarrierId("1234");
        carrierOrderRepository.save(model);
        carrierOrderRepository.save(model2);
//        var carrierService = Mockito.mock(CarrierService.class);
//        var bankAccountService = Mockito.mock(BankAccountService.class);
        var sut = new CarrierOrderServiceImpl(null,null, carrierOrderRepository);

        var actual = sut.getCarrierOrdersByCarrierId("123");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(model.getCarrierId());
        expected.setId(model.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCompanyName() {
        // JAK NAPISAC TEST JESLI POTRZEBA KORZYSTAC Z DWOCH REPO (BO MUSI SPRAWDZIC DANE Z DRUGIEJ)
        var coM = new CarrierOrderModel();
        var coM2 = new CarrierOrderModel();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");

        coM.setCarrierId(cM.getId());
        coM2.setCarrierId("123");
        carrierRepository.save(cM);
        carrierOrderRepository.save(coM);
        carrierOrderRepository.save(coM2);
        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrdersByCompanyName("Company1");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(coM.getCarrierId());
        expected.setId(coM.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCompanyNameAndCity() {
        var coM = new CarrierOrderModel();
        var coM2 = new CarrierOrderModel();
        var cM = new CarrierModel();
        cM.setCompanyName("Company1");
        cM.setStartCity("City1");

        coM.setCarrierId(cM.getId());
        coM2.setCarrierId("123");
        carrierRepository.save(cM);
        carrierOrderRepository.save(coM);
        carrierOrderRepository.save(coM2);

        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrdersByCompanyNameAndCity("Company1", "City1");

        var expected = new CarrierOrderModel();
        expected.setCarrierId(coM.getCarrierId());
        expected.setId(coM.getId());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrdersByCarrierIdSorted() {
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

        carrierOrderRepository.save(model2);
        carrierOrderRepository.save(model);
        carrierOrderRepository.save(model4);
        carrierOrderRepository.save(model3);

        var sut = new CarrierOrderServiceImpl(null, null,carrierOrderRepository);
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
        var coM = new CarrierOrderModel();
        coM.setId("123");

        carrierOrderRepository.save(coM);

        var sut = new CarrierOrderServiceImpl(null, null,carrierOrderRepository);
        sut.makePayment("123");
        Assertions.assertThat(carrierOrderRepository.findById(coM.getId()).get().isPaid()).isEqualTo(true);
    }

    @Test
    public void shouldGetCarrierOrderById() {
        var coM = new CarrierOrderModel();
        coM.setId("123");
        carrierOrderRepository.save(coM);
        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrderById("123");
        Optional<CarrierOrderModel> expected = Optional.of(coM);

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierOrderById() {
        var coM = new CarrierOrderModel();
        coM.setId("123");
        carrierOrderRepository.save(coM);
        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrderById("1234");

        Assertions.assertThat(actual).isEqualTo(Optional.empty());
    }

    @Test
    public void shouldGetCarrierOrderByEmailAndCarrierId() {
        var coM = new CarrierOrderModel();
        coM.setEmail("email1");
        coM.setCarrierId("1234");
        carrierOrderRepository.save(coM);
        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrderByEmailAndCarrierId("email1", "1234");
        var expected = coM;

        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotGetCarrierOrderByEmailAndCarrierId() {
        var coM = new CarrierOrderModel();
        coM.setEmail("email1");
        coM.setCarrierId("1234");
        carrierOrderRepository.save(coM);
        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrderByEmailAndCarrierId("email1", "12345");

        Assertions.assertThat(actual).isEqualTo(null);
    }


    @Test
    public void shouldRefreshCarrierOrders() {
//        var carrierProvider = Mockito.mock(CarrierService.class);
//        var given = new ArrayList<CarrierOrderModel>();
//        var cM = new CarrierModel();
//        cM.setDate(LocalDate.now().plusDays(4));
//
//        // should be removed from given
//        var coM = new CarrierOrderModel();
//        coM.setCarrierId(cM.getId());
//        coM.setOrderDate(cM.getDate().plusDays(3));
//
//        given.add(coM);
//        Mockito.when(carrierProvider.getCarrierById(cM.getId())).thenReturn(cM);
//        var sut = new CarrierOrderServiceImpl(carrierProvider, null, carrierOrderRepository);
//        sut.refreshCarrierOrders();
//        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldDeleteUnpaidOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);
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
        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider,carrierOrderRepository);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldDeletePaidOrderIfMoreThan7DaysLast() {
        var carrierProvider = Mockito.mock(CarrierService.class);
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
        Mockito.when(bankProvider.addMoneyToAccount(coM.getEmail(), 9)).thenReturn(true);

        //jesli zostalo wiecej niz 7 dni do wyjazdu zwroc 90%
        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider, carrierOrderRepository);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
//        Assertions.assertThat(baM.getAccountBalance()).isEqualTo(9);
    }

    @Test
    public void shouldDeletePaidOrderIfLessThan7DaysLast() {
        var carrierProvider = Mockito.mock(CarrierService.class);
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
        Mockito.when(bankProvider.addMoneyToAccount(coM.getEmail(), 5)).thenReturn(true);


        //jesli zostalo mniej niz 7 dni do wyjazdu zwroc 50%
        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider, carrierOrderRepository);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(true);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldNotDeleteOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);
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
        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider, carrierOrderRepository);
        var actual = sut.deleteOrder("email1", "123");
        Assertions.assertThat(actual).isEqualTo(false);
    }

    @Test
    public void shouldRemoveAllOrders() {
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        sut.removeAllOrders(given);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldAddOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();
        Mockito.when(carrierProvider.availabilityMinusOne("1234")).thenReturn(true);

        var sut = new CarrierOrderServiceImpl(carrierProvider, null, carrierOrderRepository);
        var actual = sut.addOrder(coM);
        var expected = true;
        Assertions.assertThat(given).containsExactly(coM);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotAddOrder() {
        var carrierProvider = Mockito.mock(CarrierService.class);

        var coM = new CarrierOrderModel(); // zamowienie
        coM.setPaid(true);
        coM.setEmail("email12");
        coM.setCarrierId("1234");
        var given = new ArrayList<CarrierOrderModel>();
        Mockito.when(carrierProvider.availabilityMinusOne("1234")).thenReturn(false);

        var sut = new CarrierOrderServiceImpl(carrierProvider, null, carrierOrderRepository);
        var actual = sut.addOrder(coM);
        var expected = false;
        Assertions.assertThat(given).isEmpty();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetUnpaidOrders() {
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.unpaidOrders("email1");
        var expected = coM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetCarrierOrderListIterable() {
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(null, null, carrierOrderRepository);
        var actual = sut.getCarrierOrderListIterable();
        var expected = coM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldRemoveCarrierOrder() {
        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(null, null,carrierOrderRepository);
        sut.removeCarrierOrder(coM);
        Assertions.assertThat(given).isEmpty();
    }

    @Test
    public void shouldPayForOrder(){
        // getCarrierOrderByEmailAndCarrierId czy na pewno powinno korzystać z innej metody skoro ona moze sie wysypac
        // wiec tak jakby testujemy i payForOrder i getCarrierOrderByEmailAndCarrierId
        var carrierProvider = Mockito.mock(CarrierService.class);
        var bankProvider = Mockito.mock(BankAccountService.class);

        var baM = new BankAccountModel("email1");
        baM.setAccountBalance(100);

        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);

        Mockito.when(bankProvider.getBankAccountByEmail("email1")).thenReturn(baM);
        Mockito.when(carrierProvider.getCarrierById("123")).thenReturn(cM);


        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider, carrierOrderRepository);
        var actual = sut.payForOrder("email1","123");
        var expected = true;
        Assertions.assertThat(actual).isEqualTo(expected);


    }

    @Test
    public void shouldNotPayForOrder(){
        // czy na pewno powinno korzystać z innej metody skoro ona moze sie wysypac
        // wiec tak jakby testujemy i payForOrder i getCarrierOrderByEmailAndCarrierId i makePayment
        var carrierProvider = Mockito.mock(CarrierService.class);
        var bankProvider = Mockito.mock(BankAccountService.class);

        var baM = new BankAccountModel("email1");
        baM.setAccountBalance(1);

        var cM = new CarrierModel();
        cM.setId("123");
        cM.setPrice(10);

        Mockito.when(bankProvider.getBankAccountByEmail("email1")).thenReturn(baM);
        Mockito.when(carrierProvider.getCarrierById("123")).thenReturn(cM);


        var coM = new CarrierOrderModel(); // zamowienie
        coM.setEmail("email1");
        coM.setCarrierId("123");
        var given = new ArrayList<CarrierOrderModel>();
        given.add(coM);

        var sut = new CarrierOrderServiceImpl(carrierProvider, bankProvider, carrierOrderRepository);
        var actual = sut.payForOrder("email1","123");
        var expected = false;
        Assertions.assertThat(actual).isEqualTo(expected);


    }


}

