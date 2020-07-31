package jwachala.project.reservationsapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
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
    public Iterable<BankAccountDTO> getAllAccounts() {
        var dtoList = new ArrayList<BankAccountDTO>();
        for (var baM : bankAccountService.getBankAccountList()) {
            var baDTO = new BankAccountDTO();
            baDTO.setAccountBalance(baM.getAccountBalance());
            baDTO.setEmail(baM.getEmail());
            dtoList.add(baDTO);
        }
        return dtoList;
    }

    @GetMapping("/account/{email}")
    public BankAccountDTO getAccountByEmail(@PathVariable(value = "email") String email) {
        var baM = bankAccountService.getBankAccountByEmail(email);
        var baDTO = new BankAccountDTO();
        baDTO.setAccountBalance(baM.getAccountBalance());
        baDTO.setEmail(baM.getEmail());

        return baDTO;
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
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        if (bankAccountService.addMoneyToAccount(email, money)) {
            return ResponseEntity.created(uri).build();
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Sorry, operation rejected");
    }


    //ZALOZ KONTO
    @PostMapping("/account")
    public ResponseEntity<?> createAccount(@RequestBody String email) {
        bankAccountService.addBankAccount(new BankAccountModel(email));

        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{email}").buildAndExpand(email).toUri();

        return ResponseEntity.created(uri).build();
    }
}
