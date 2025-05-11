package com.example.service;
import com.example.entity.Message;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;

import java.util.List;


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
        Integer userId = mess.getPostedBy();

        // check if user exists
        if (userId == null || !messageRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found!");
        }
        // check if message is not empty or over 255 chars
        if (msgTxt == null || msgTxt.trim().isEmpty() || msgTxt.length() > 255) {
            //return null;
           throw new IllegalArgumentException("Message cannot be empty or over 255 characters long!");
         }
        // return the saved message by calling repository method
        return messageRepository.save(mess);
    }

    


    // return all messages
    public List<Message> getAllMessages(){
        return messageRepository.findAll();
    }




    // get message by id if it exists
    public Message getMessageById(Integer id){
        // use optional in case message is empty
        Optional<Message> optionalStore = messageRepository.findById(id);
      if(optionalStore.isPresent()){
          return optionalStore.get();
      }else{
          return null;
      }
    }




    // delete message by id
    public boolean deleteMessage(Integer id) {
        // check if message doesn't exist
        if (!messageRepository.existsById(id)) {
            return false; 
        }
        // if it exists, delete it
        messageRepository.deleteById(id);
        return true; 
    }





    // update message with new text
    public Message updateMessage(Integer messageId, String messageText){
        // check conditions of message text
        if (messageText == null || messageText.trim().isEmpty() || messageText.length() > 255) {
            return null;
        }
        // find the message via id
        Optional<Message> mess = messageRepository.findById(messageId);
        if (!mess.isPresent()){
            Message message = mess.get();
            message.setMessageText(messageText);
            return messageRepository.save(message);
        }
       return null;   
    }




    // method to retrieve all message by user id
    public List<Message> getAllMessagesByUserId(Integer accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }

}







