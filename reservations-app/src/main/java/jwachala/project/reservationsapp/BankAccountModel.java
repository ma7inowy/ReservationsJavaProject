package jwachala.project.reservationsapp;

import lombok.Data;

@Data
public class BankAccountModel {
    private String email;
    private int accountBalance = 0;

    public BankAccountModel(String email) {
        this.email = email;
    }

    public void addMoney(int money){
        accountBalance += money;
    }
}
