package jwachala.project.reservationsapp;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
public class BankAccountModel {
    @Id
    private String email;
    private double accountBalance = 0;

    public BankAccountModel(String email) {
        this.email = email;
    }

//    public void depositMoney(double money){
//        accountBalance += money;
//    }
}
