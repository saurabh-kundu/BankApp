package com.bankapp.user.repo;

import com.bankapp.user.model.BankUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Long> {

    @Query("SELECT u FROM BankUser u WHERE externalId = ?1")
    Optional<BankUser> findBankUserById(String userId);
}
