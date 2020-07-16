package jwachala.project.reservationsapp;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class BankAccountRepository {

    private List<BankAccountModel> bankAccountList = new ArrayList<>();

    public BankAccountRepository() {
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

    public BankAccountModel getBankAccountByEmail(String email){
        for(BankAccountModel bA : bankAccountList){
            if(bA.getEmail().equals(email))
                return bA;
        }
        return null;
    }
}
