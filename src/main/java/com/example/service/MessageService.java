package com.example.service;
import com.example.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;

@Service
public class MessageService {

   MessageRepository messageRepository;

    // dependency injection via constructor
    @Autowired
    public MessageService(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }



    // create a new message method
    public Message addMessage(Message mess) {
        // get message text
        String msgTxt = mess.getMessageText();
        // get user id
        int user = mess.getPostedBy();

        // check if message is not empty or over 255 chars
        if (msgTxt == null || msgTxt.trim().isEmpty() || msgTxt.length() > 255) {
            //return null;
           throw new IllegalArgumentException("Message cannot be empty or over 255 characters long!");
         }
        // check if user exists
        if (user == 0) {
            //return null;
            throw new IllegalArgumentException("User not found!");
        }
        // return the saved message by calling repository method
        return messageRepository.save(mess);
    }







}
