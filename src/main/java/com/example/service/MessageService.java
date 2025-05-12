package com.example.service;
import com.example.entity.Message;
import java.util.*;
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



    


    // method to create new message
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




     
    // method to update message with new text
   public Message updateMessage(Integer messageId, Message msg) {
        // get text from message
        String text = msg.getMessageText();
        // check conditions of text
        if (text == null || text.trim().isEmpty() || text.length() > 255) {
            throw new IllegalArgumentException("Text conditions not met. Cannot be empty or over 255 characters");
        }
        // find the existing message in db by id
        Optional<Message> optionalMessage = messageRepository.findById(messageId);
        if (optionalMessage.isPresent()) {
            Message message = optionalMessage.get();
            // update message text
            message.setMessageText(text);
            // persist it
            return messageRepository.save(message); 
        }
        return null; 
    }






    // method to retrieve all message by user id
    public List<Message> getAllMessagesByUserId(Integer accountId) {
        return messageRepository.findMessagesByPostedBy(accountId);
    }

}







