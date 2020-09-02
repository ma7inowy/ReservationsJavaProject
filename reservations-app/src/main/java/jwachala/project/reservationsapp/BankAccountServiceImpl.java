package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Data
@Component
@NoArgsConstructor // czy potrzebny?
public class BankAccountServiceImpl implements BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    //testy
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

//    @PostConstruct
//    public void init() {
//        bankAccountRepository.save(new BankAccountModel("jankowalski1@wp.pl"));
//        bankAccountRepository.save(new BankAccountModel("jankowalski2@wp.pl"));
//        bankAccountRepository.save(new BankAccountModel("jankowalski3@wp.pl"));
//        bankAccountRepository.save(new BankAccountModel("jankowalski4@wp.pl"));
//        bankAccountRepository.save(new BankAccountModel("jankowalski5@wp.pl"));
//        bankAccountRepository.save(new BankAccountModel("jankowalski6@wp.pl"));
//    }

    @Override
    public BankAccountModel getBankAccountByEmail(String email) {
        return bankAccountRepository.findByEmail(email);
    }

    @Override
    public Iterable<BankAccountModel> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public void addBankAccount(BankAccountModel bankAccountModel) {
        bankAccountRepository.save(bankAccountModel);
    }

    @Override
    public boolean addMoneyToAccount(String email, double money) {
        BankAccountModel ba = getBankAccountByEmail(email);
        var actualState = ba.getAccountBalance();
        ba.setAccountBalance(ba.getAccountBalance() + money);
        bankAccountRepository.save(ba);
        return ba.getAccountBalance() == (actualState + money);
    }

    @Override
    public void chargeMoney(BankAccountModel account, double price) {
        account.setAccountBalance(account.getAccountBalance() - price);
        bankAccountRepository.save(account);
    }
}


