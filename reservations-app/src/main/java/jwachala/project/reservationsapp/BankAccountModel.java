package jwachala.project.reservationsapp;

import lombok.Data;

@Data
public class BankAccountModel {
    private String email;
    private double accountBalance = 0;

    public BankAccountModel(String email) {
        this.email = email;
    }

//    public void depositMoney(double money){
//        accountBalance += money;
//    }
}
