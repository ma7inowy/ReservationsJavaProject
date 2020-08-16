package jwachala.project.reservationsapp;

import net.bytebuddy.implementation.auxiliary.MethodCallProxy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;

@DataJpaTest
public class BankAccountServiceImplTests {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void shouldGetBankAccountByEmail() {
        var bAM = new BankAccountModel("email");
        bankAccountRepository.save(bAM);
        var sut = new BankAccountServiceImpl(bankAccountRepository);
        var actual = sut.getBankAccountByEmail("email");
        var expected = bAM;
        Assertions.assertThat(actual).isEqualTo(bAM);
    }

    @Test
    public void shouldNotGetBankAccountByEmail() {
        var bAM = new BankAccountModel("email");
        bankAccountRepository.save(bAM);
        var sut = new BankAccountServiceImpl(bankAccountRepository);
        var actual = sut.getBankAccountByEmail("email1");
        Assertions.assertThat(actual).isEqualTo(null);
    }

    @Test
    public void shouldAddBankAccount() {
        var bAM = new BankAccountModel("email");
        var sut = new BankAccountServiceImpl(bankAccountRepository);
        sut.addBankAccount(bAM);
        Assertions.assertThat(bankAccountRepository.findAll()).containsExactly(bAM);
    }

    @Test
    public void shouldAddMoneyToBankAccount() {
        var bAM = new BankAccountModel("email");
        bankAccountRepository.save(bAM);

        var sut = new BankAccountServiceImpl(bankAccountRepository);
        var actual = sut.addMoneyToAccount("email", 10);
        var expected = true;
        Assertions.assertThat(bankAccountRepository.findByEmail("email").getAccountBalance()).isEqualTo(10);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void shouldChargeMoney(){
        var bAM = new BankAccountModel("email");
        bAM.setAccountBalance(100);
        bankAccountRepository.save(bAM);
        var sut = new BankAccountServiceImpl(bankAccountRepository);
        sut.chargeMoney(bAM, 10);
        Assertions.assertThat(bankAccountRepository.findByEmail("email").getAccountBalance()).isEqualTo(90);
    }

    @Test
    public void shouldGetBankAccounts(){
        var bAM = new BankAccountModel("email");
        bankAccountRepository.save(bAM);
        var sut = new BankAccountServiceImpl(bankAccountRepository);
        var actual = sut.getBankAccounts();
        var expected = bAM;
        Assertions.assertThat(actual).containsExactly(expected);
    }

}
