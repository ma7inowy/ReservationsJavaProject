package jwachala.project.reservationsapp;

public interface BankAccountService {
    BankAccountModel getBankAccountByEmail(String email);

    Iterable<BankAccountModel> getBankAccountList();
    void addBankAccount(BankAccountModel bankAccountModel);
    boolean addMoneyToAccount(String email, double money);
}
