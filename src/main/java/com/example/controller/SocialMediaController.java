package com.example.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import com.example.service.MessageService;


@RestController
public class SocialMediaController {

    @Autowired
    AccountService accountService;

    @Autowired
    MessageService messageService;





    // mapping 'register' endpoint to create account, http post method
    @PostMapping("/register")
    // wildcard generic to allow for account or unknown object
    private ResponseEntity<?> register(@RequestBody Account acc) {
        try {
            Account created = accountService.createAccount(acc);
                return ResponseEntity.ok(created); 
        } catch (IllegalArgumentException e) {    
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage()); 
        }
    }





    // map endpoint for user login 
    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody Account account){
        try {Account exists = accountService.login(account.getUsername(), account.getPassword());
            return ResponseEntity.ok(exists);
        } catch (IllegalArgumentException e) {
            // return 401 unauthorized
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } 
    }





    // map endpoint to create message method
    @PostMapping("/messages")
    private ResponseEntity<?> createMessage(@RequestBody Message mess){
        try { Message created = messageService.addMessage(mess);
            return ResponseEntity.ok(created);    
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }





    // map endpoint to retrieve all messages via "get"
    @GetMapping("/messages")
    private ResponseEntity<?> getAllMessages(){
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }





    // handle message param to map to method to get message by id
    @GetMapping("messages/{messageId}")
    private ResponseEntity<?> getMessageById(@PathVariable Integer messageId){
        try {
            Message message = messageService.getMessageById(messageId);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.status(200).body("Message not found");
        }
    }





    // map end point to the delete message with message id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        if (deleted) {
            // return if deleted, rows deleted '1'
            return ResponseEntity.ok(1); 
        } else {
            // return if never existed
            return ResponseEntity.ok().build(); 
        }
    }




    
    // map to method to partially update message ie messageText
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        try {
            Message updatedMessage = messageService.updateMessage(messageId, message);
            if (updatedMessage != null) {
                return ResponseEntity.ok(1); 
            } else {
                return ResponseEntity.badRequest().body("Invalid message text or message not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid request: " + e.getMessage());
        }
    }





    // Map to param to retrieve list of messages, empty or full
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getAllMessagesByUserId(accountId);
        return ResponseEntity.ok(messages);  
    }


}

