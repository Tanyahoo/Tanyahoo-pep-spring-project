package com.example.repository;
import java.util.*;

import org.springframework.data.jpa.repository.*;

import com.example.entity.Account;

// It doesn't need to be marked as @Component or @Repository as this is inherited from the JPARepository
public interface AccountRepository extends JpaRepository<Account, Integer>{


// to do: write bespoke crud methods maybe using @Query?


// method to find account with a username and password as params
Optional<Account> findByUsernameAndPassword(String username, String password);

Optional<Account> findAccountByUsername(String user);







}
