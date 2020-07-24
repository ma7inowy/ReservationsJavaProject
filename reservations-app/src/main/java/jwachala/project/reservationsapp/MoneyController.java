package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/bank")
public class MoneyController {

    private BankAccountService bankAccountService;

    @Autowired
    public MoneyController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @GetMapping("/accounts")
    public Iterable<BankAccountModel> getAllAccounts() {
        return bankAccountService.getBankAccountList();
    }

    @GetMapping("/account/{email}")
    public BankAccountModel getAccountByEmail(@PathVariable(value = "email") String email){
        return bankAccountService.getBankAccountByEmail(email);
    }

    //SPRAWDZ SALDO KONTA
    @GetMapping("/account/{email}/accountBalance")
    public double getAccountBalance(@PathVariable(value = "email") String email) {
        return bankAccountService.getBankAccountByEmail(email).getAccountBalance();
    }

    //WPLAC GOTOWKE
    @PostMapping("/account/{email}/accountBalance/deposit")
    public ResponseEntity<?> addMoneyToAccount(@PathVariable(value = "email") String email,
                                               @RequestBody int money) {
        BankAccountModel ba = bankAccountService.getBankAccountByEmail(email);
        ba.depositMoney(money);

        // uri do poprawy
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }


    //ZALOZ KONTO
    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody String email){
        bankAccountService.addBankAccount(new BankAccountModel(email));

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(email).toUri();

        return ResponseEntity.created(uri).build();
    }
}
