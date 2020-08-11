package jwachala.project.reservationsapp;

public interface BankAccountService {
    BankAccountModel getBankAccountByEmail(String email);

    Iterable<BankAccountModel> getBankAccounts();
    void addBankAccount(BankAccountModel bankAccountModel);
    boolean addMoneyToAccount(String email, double money);
    void chargeMoney(BankAccountModel account, double price);
}
