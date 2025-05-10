package com.example.service;
import com.example.entity.Message;

import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import com.example.repository.MessageRepository;

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

//////////////////////////////////////


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





    public void deleteMessage(Integer id) {
    if (!messageRepository.existsById(id)) {
        throw new EmptyResultDataAccessException("Message not found", 1);
    }
    messageRepository.deleteById(id);
}






}
