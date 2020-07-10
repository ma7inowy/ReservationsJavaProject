package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("api/bank")
public class BankController {
    @Autowired
    private BankAccountRepository bankAccountRepository;

    @GetMapping("/")
    public List<BankAccountModel> getAllAccounts() {
        return bankAccountRepository.getBankAccountList();
    }

    //SPRAWDZ SALDO KONTA
    @GetMapping("/{email}/accountBalance")
    public int getAccountBalance(@PathVariable(value = "email") String email) {
        return bankAccountRepository.getBankAccountByEmail(email).getAccountBalance();
    }

    //WPLAC GOTOWKE
    @PostMapping("/{email}/accountBalance/add")
    public ResponseEntity<?> addMoneyToAccount(@PathVariable(value = "email") String email,
                                               @RequestBody int money) {
        BankAccountModel ba = bankAccountRepository.getBankAccountByEmail(email);
        ba.addMoney(money);

        // uri do poprawy
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uri).build();
    }


    //ZALOZ KONTO
}
