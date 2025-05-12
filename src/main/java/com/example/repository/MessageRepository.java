package com.example.repository;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import com.example.entity.Message;

// this doesn't need to be marked as @Component or @Repository as it is inherited from the JPARepository
public interface MessageRepository extends JpaRepository<Message, Integer>{

// to do: write bespoke crud methods if needed
   
List<Message> findMessagesByPostedBy(Integer postedBy);


}


