package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.service.AccountService;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. 
 * The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to 
 * use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;




    // mapping 'register' to create account, http post method
    @PostMapping("/register")
        // wildcard generic to allow for account or string object 
        private ResponseEntity<?> register(@RequestBody Account acc) {

            try {

            Account created = accountService.createAccount(acc);
                return ResponseEntity.ok(created); // 200 OK with Account JSON

            } catch (IllegalArgumentException e) {    
                return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); // 409 Conflict
            }
        }





    // method to map login to 
    @PostMapping("/login")

    private ResponseEntity<?> login(@RequestBody Account account){

        try {Account exists = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(exists);
        
        // return 401 unauthorized
        } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
            } 

        }

    }

