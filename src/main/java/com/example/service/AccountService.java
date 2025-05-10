package com.example.service;
 

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;


@Service
public class AccountService {

    AccountRepository accountRepository;

    // dependency injection via constructor
    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }




    // create a new user account method
    public Account createAccount(Account acc){
        String user = acc.getUsername();
        String pass = acc.getPassword();

       // check conditions for password and username
       if (user == null || user.trim().isEmpty() || pass == null || pass.length() < 4) {
            throw new IllegalArgumentException("Invalid username or password");// maybe write bespoke exception class?
        }
       // use Optional to check for existing username
        Optional<Account> existing = accountRepository.findAccountByUsername(user);
        if (existing.isPresent()) {
            throw new IllegalArgumentException("Username already in use"); // maybe write bespoke exception class
    }
            
    // save and return the new account
    return accountRepository.save(acc);
    }

     


    // method to verify user login
    public Account login(String username, String password){
        // check for correct user input
        if (username == null || username.trim().isEmpty() || password == null || password.length() < 4) {
            
            throw new IllegalArgumentException("Incorrect username or password");
            }

            Optional<Account> existing = accountRepository.findByUsernameAndPassword(username, password);
            if (existing.isEmpty()) {
                throw new IllegalArgumentException("Incorrect username or password"); // avoid revealing which one failed
            }

            return existing.get();
    }








}
