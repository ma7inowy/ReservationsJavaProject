package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/bank")
public class MoneyController {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @GetMapping("/accounts")
    public List<BankAccountModel> getAllAccounts() {
        return bankAccountRepository.getBankAccountList();
    }

    @GetMapping("/account/{email}")
    public BankAccountModel getAccountByEmail(@PathVariable(value = "email") String email){
        return bankAccountRepository.getBankAccountByEmail(email);
    }

    //SPRAWDZ SALDO KONTA
    @GetMapping("/account/{email}/accountBalance")
    public double getAccountBalance(@PathVariable(value = "email") String email) {
        return bankAccountRepository.getBankAccountByEmail(email).getAccountBalance();
    }

    //WPLAC GOTOWKE
    @PostMapping("/account/{email}/accountBalance/deposit")
    public ResponseEntity<?> addMoneyToAccount(@PathVariable(value = "email") String email,
                                               @RequestBody int money) {
        BankAccountModel ba = bankAccountRepository.getBankAccountByEmail(email);
        ba.depositMoney(money);

        // uri do poprawy
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }


    //ZALOZ KONTO
    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody String email){
        bankAccountRepository.getBankAccountList().add(new BankAccountModel(email));

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(email).toUri();

        return ResponseEntity.created(uri).build();
    }
}
