package com.bankmgmt.app.service;

import com.bankmgmt.app.entity.Account;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BankService {

    private List<Account> accounts = new ArrayList<>();
    private Integer currentId = 1;

    // TODO: Method to Create a new Account


    public Account createAccount(String accountHolderName, String accountType, String email) {
        Account account = new Account(currentId, accountHolderName, accountType,0.0,email);
        accounts.add(account);
        currentId+=1;
        return account;

    }

    // TODO: Method to Get All Accounts

    public List<Account> getAllAccounts() { return accounts; }
    // TODO: Method to Get Account by ID

    public Optional<Account> getAccountById(int id) {

        return accounts.stream().filter(a -> a.getId() == id).findFirst();
    }
    // TODO: Method to Deposit Money to the specified account id

    public boolean deposit(int id, double amount) {
        if (amount <= 0) return false;
        Optional<Account> account = getAccountById(id);
        account.ifPresent(a -> a.deposit(amount));
        return account.isPresent();
    }
    // TODO: Method to Withdraw Money from the specified account id

    public boolean withdraw(int id, double amount) {
        if (amount <= 0) return false;
        Optional<Account> account = getAccountById(id);
        return account.map(a -> a.withdraw(amount)).orElse(false);
    }


    // TODO: Method to Transfer Money from one account to another
    public boolean transfer(int fromId, int toId, double amount) {
        if (amount <= 0) return false;
        Optional<Account> fromAccount = getAccountById(fromId);
        Optional<Account> toAccount = getAccountById(toId);

        if (fromAccount.isPresent() && toAccount.isPresent()) {
            if (fromAccount.get().withdraw(amount)) {
                toAccount.get().deposit(amount);
                return true;
            }
        }
        return false;
    }
    
    // TODO: Method to Delete Account given a account id
    public boolean deleteAccount(int id) {
        return accounts.removeIf(a -> a.getId() == id);
    }


}
