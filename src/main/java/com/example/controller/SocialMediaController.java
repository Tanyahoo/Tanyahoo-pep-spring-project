package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

   // @PostMapping("/register")
   // public ResponseEntity<Account> registerAccount(@RequestBody Account account) {
       

   // }

    @PostMapping("/register")
        public ResponseEntity<?> register(@RequestBody Account acc) {
            
            try {

            Account created = accountService.createAccount(acc);
                return ResponseEntity.ok(created); // 200 OK with Account JSON

            } catch (IllegalArgumentException e) {    
            String msg = e.getMessage();
            if ("Username already in use".equals(msg)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(msg); // 409 Conflict
            }
            return ResponseEntity.badRequest().body(msg); // 400 Bad Request
        }
}






}
