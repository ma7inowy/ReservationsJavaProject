package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class MoneyControllerTests {
    @Test
    public void shouldGetAllAccounts() {
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        var baList = new ArrayList<BankAccountModel>();
        baList.add(baM);
        Mockito.when(bankService.getBankAccounts()).thenReturn(baList);
        var sut = new MoneyController(bankService, null);
        var actual = sut.getAllAccounts();
        var expected = new BankAccountDTO();
        expected.setEmail(baM.getEmail());
        expected.setAccountBalance(baM.getAccountBalance());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAccountByEmail() {
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        Mockito.when(bankService.getBankAccountByEmail("email")).thenReturn(baM);
        var sut = new MoneyController(bankService, null);
        var actual = sut.getAccountByEmail("email");
        var expected = new BankAccountDTO();
        expected.setEmail(baM.getEmail());
        expected.setAccountBalance(baM.getAccountBalance());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetAccountBalance() {
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        baM.setAccountBalance(10);
        Mockito.when(bankService.getBankAccountByEmail("email")).thenReturn(baM);
        var sut = new MoneyController(bankService, null);
        var actual = sut.getAccountBalance("email");
        var expected = 10;
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldAddMoneyToAccount() {
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        baM.setAccountBalance(10);
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };
        Mockito.when(bankService.addMoneyToAccount("email", 10)).thenReturn(true);
        var sut = new MoneyController(bankService, resourceProvider);
        var actual = sut.addMoneyToAccount("email", 10);
        var expected = ResponseEntity.created(uri.get()).build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldNotAddMoneyToAccount() {
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        baM.setAccountBalance(10);
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };
        Mockito.when(bankService.addMoneyToAccount("email1", 10)).thenReturn(false);
        var sut = new MoneyController(bankService, resourceProvider);
        var actual = sut.addMoneyToAccount("email1", 10);
        var expected = ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, operation rejected");
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldCreateAccount() {
        var bankService = Mockito.mock(BankAccountService.class);
        var uri = new AtomicReference<URI>();
        ResourceLocationBuilder resourceProvider = id -> {
            uri.set(URI.create(id));
            return uri.get();
        };
        var sut = new MoneyController(bankService, resourceProvider);
        var actual = sut.createAccount("email");
        var expected = ResponseEntity.created(uri.get()).build();
        Assertions.assertThat(actual).isEqualTo(expected);
    }


}
