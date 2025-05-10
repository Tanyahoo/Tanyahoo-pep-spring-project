package com.example.repository;
import org.springframework.data.jpa.repository.*;

import com.example.entity.Account;

// It doesn't need to be marked as @Component or @Repository as this is inherited from the JPARepository
public interface AccountRepository extends JpaRepository<Account, Integer>{


// to do: write bespoke crud methods maybe using @Query?








}
