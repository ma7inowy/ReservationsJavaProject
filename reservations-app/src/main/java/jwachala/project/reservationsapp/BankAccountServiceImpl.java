package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class BankAccountServiceImpl implements BankAccountService {

    private List<BankAccountModel> bankAccountList;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    //testy
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }


    public BankAccountServiceImpl() {
        bankAccountList = new ArrayList<>();
        bankAccountList.add(new BankAccountModel("jankowalski1@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski2@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski3@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski4@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski5@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski6@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski7@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski8@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski9@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski10@wp.pl"));
        bankAccountList.add(new BankAccountModel("jankowalski11@wp.pl"));
    }

    @PostConstruct
    public void init() {
        bankAccountRepository.save(new BankAccountModel("jankowalski1@wp.pl"));
        bankAccountRepository.save(new BankAccountModel("jankowalski2@wp.pl"));
        bankAccountRepository.save(new BankAccountModel("jankowalski3@wp.pl"));
        bankAccountRepository.save(new BankAccountModel("jankowalski4@wp.pl"));
        bankAccountRepository.save(new BankAccountModel("jankowalski5@wp.pl"));
        bankAccountRepository.save(new BankAccountModel("jankowalski6@wp.pl"));
    }


    @Override
    public BankAccountModel getBankAccountByEmail(String email) {
//        for (BankAccountModel bA : bankAccountList) {
//            if (bA.getEmail().equals(email))
//                return bA;
//        }
//        return null;
        return bankAccountRepository.findByEmail(email);
    }

    @Override
    public Iterable<BankAccountModel> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Override
    public void addBankAccount(BankAccountModel bankAccountModel) {
//        bankAccountList.add(bankAccountModel);
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


