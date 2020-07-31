package jwachala.project.reservationsapp;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

public class MoneyControllerTests {
    @Test
    public void shouldGetAllAccounts(){
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        var baList = new ArrayList<BankAccountModel>();
        baList.add(baM);
        Mockito.when(bankService.getBankAccountList()).thenReturn(baList);
        var sut = new MoneyController(bankService);
        var actual = sut.getAllAccounts();
        var expected = new BankAccountDTO();
        expected.setEmail(baM.getEmail());
        expected.setAccountBalance(baM.getAccountBalance());
        Assertions.assertThat(actual).containsExactly(expected);
    }

    @Test
    public void shouldGetAccountByEmail(){
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        Mockito.when(bankService.getBankAccountByEmail("email")).thenReturn(baM);
        var sut = new MoneyController(bankService);
        var actual = sut.getAccountByEmail("email");
        var expected = new BankAccountDTO();
        expected.setEmail(baM.getEmail());
        expected.setAccountBalance(baM.getAccountBalance());
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldGetAccountBalance(){
        var bankService = Mockito.mock(BankAccountService.class);
        var baM = new BankAccountModel("email");
        baM.setAccountBalance(10);
        Mockito.when(bankService.getBankAccountByEmail("email")).thenReturn(baM);
        var sut = new MoneyController(bankService);
        var actual = sut.getAccountBalance("email");
        var expected = 10;
        Assertions.assertThat(actual).isEqualTo(expected);
    }

//    @Test
//    public void shouldAddMoneyToAccount(){
//        var bankService = Mockito.mock(BankAccountService.class);
//        var baM = new BankAccountModel("email");
//        baM.setAccountBalance(10);
//
//        var sut = new MoneyController(bankService);
//        var actual = sut.addMoneyToAccount("email",10);
//
//        var expected =  ResponseEntity.created(uri).build();
//        Assertions.assertThat(actual).isEqualTo(expected);
//    }




}
