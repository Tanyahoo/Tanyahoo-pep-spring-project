package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;




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

    @Autowired
    MessageService messageService;




    // mapping 'register' to create account, http post method
    @PostMapping("/register")
    // wildcard generic to allow for account or unknown object
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


    @PostMapping("/messages")
    private ResponseEntity<?> createMessage(@RequestBody Message mess){
        try { Message created = messageService.addMessage(mess);
            return ResponseEntity.ok(created);    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }




    }

////////////////////////////////////////////





    // retrieve all messages
    @GetMapping("/messages")
    private ResponseEntity<?> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }





    // retrieve message by its id
    @GetMapping("messages/{messageId}")
    private ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("Message not found.");
        }

    }




    // delete message with message id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            // return if deleted
            return ResponseEntity.ok(1); 
        } else {
            // return if never existed
            return ResponseEntity.ok().build(); 
        }
    }




   // needs work
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable int messageId, @RequestBody String messageText) {
        Message updatedMessage = messageService.updateMessage(messageId, messageText);
        if (updatedMessage!=null) {
            return ResponseEntity.ok(1); 
        } else {
            return ResponseEntity.status(400).build();     
        }
    }
    

    

    // retrieve list of messages, empty or full
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
    List<Message> messages = messageService.getAllMessagesByUserId(accountId);
    return ResponseEntity.ok(messages);  
}

    






    }

