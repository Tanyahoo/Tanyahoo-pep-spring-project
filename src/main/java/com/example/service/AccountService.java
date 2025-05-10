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
            return null;
            //throw new InvalidAccountException("Invalid username or password");// write in exception class;
        }
       // Use Optional to check for existing username
        Optional<Account> existing = accountRepository.findAccountByUsername(user);
        if (existing.isPresent()) {
            //throw new UsernameAlreadyExistsException("Username already in use"); // write in exception class
            return null;
    }
            
    // Save and return the new account
    return accountRepository.save(acc);

    }

     









}
