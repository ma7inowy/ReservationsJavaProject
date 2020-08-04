package jwachala.project.reservationsapp;

import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class BankAccountServiceImplTests {
    @Test
    public void shouldGetBankAccountByEmail(){
        var given = new ArrayList<BankAccountModel>();
        var bAM = new BankAccountModel("email");
        given.add(bAM);
        var sut = new BankAccountServiceImpl(given);
        var actual = sut.getBankAccountByEmail("email");
        var expected = bAM;
        Assertions.assertThat(actual).isEqualTo(bAM);
    }

    @Test
    public void shouldNotGetBankAccountByEmail(){
        var given = new ArrayList<BankAccountModel>();
        var bAM = new BankAccountModel("email");
        given.add(bAM);
        var sut = new BankAccountServiceImpl(given);
        var actual = sut.getBankAccountByEmail("email1");
        Assertions.assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldAddBankAccount(){
        var given = new ArrayList<BankAccountModel>();
        var bAM = new BankAccountModel("email");
        var sut = new BankAccountServiceImpl(given);
        sut.addBankAccount(bAM);
        Assertions.assertThat(given).containsExactly(bAM);
    }

    @Test
    public void shouldAddMoneyToBankAccount(){
        var given = new ArrayList<BankAccountModel>();
        var bAM = new BankAccountModel("email");
        given.add(bAM);
        var sut = new BankAccountServiceImpl(given);
        var actual = sut.addMoneyToAccount("email",10);
        var expected = true;
        Assertions.assertThat(bAM.getAccountBalance()).isEqualTo(10);
        Assertions.assertThat(actual).isEqualTo(expected);
    }






}
