package com.bankmgmt.app.rest;

import com.bankmgmt.app.entity.Account;
import com.bankmgmt.app.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// TODO: Make this class a Rest Controller
@RestController
@RequestMapping("/accounts")
public class BankController {

    // TODO Autowired the BankService class
    @Autowired
    private BankService bankService;


    public BankController(BankService bankService) { this.bankService = bankService; }
    // TODO: API to Create a new account

    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account newAccount = bankService.createAccount( account.getAccountHolderName(), account.getAccountType(),account.getEmail());
        return ResponseEntity.ok(newAccount);
    }

    // TODO: API to Get all accounts
    @GetMapping("/")
    public List<Account> getAllAccounts() { return bankService.getAllAccounts(); }

    // TODO: API to Get an account by ID
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable int id) {
        Optional<Account> account = bankService.getAccountById(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // TODO: API to Deposit money
    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(@PathVariable int id, @RequestParam Double amount) {
        return bankService.deposit(id, amount) ? ResponseEntity.ok("Deposit successful") : ResponseEntity.badRequest().body("Invalid deposit");
    }


    // TODO: API to Withdraw money
    @PostMapping("/{id}/withdraw")
    public ResponseEntity<String> withdraw(@PathVariable int id, @RequestParam double amount) {
        return bankService.withdraw(id, amount) ? ResponseEntity.ok("Withdrawal successful") : ResponseEntity.badRequest().body("Insufficient funds");
    }

    // TODO: API to Transfer money between accounts
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam int fromId, @RequestParam int toId, @RequestParam double amount) {
        return bankService.transfer(fromId, toId, amount) ? ResponseEntity.ok("Transfer successful") : ResponseEntity.badRequest().body("Transfer failed");
    }


    // TODO: API to Delete an account

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable int id) {
        return bankService.deleteAccount(id) ? ResponseEntity.ok("Account deleted") : ResponseEntity.notFound().build();
    }
    
}
