package jwachala.project.reservationsapp;

public interface BankAccountService {
    BankAccountModel getBankAccountByEmail(String email);

    java.util.List<BankAccountModel> getBankAccountList();
}
