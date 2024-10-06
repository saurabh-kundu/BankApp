package com.bankapp.depositservice.repository;

import com.bankapp.depositservice.model.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<DepositAccount, Long> {

    List<DepositAccount> findAllByExternalUserId(String externalUserId);

    Optional<DepositAccount> findByExternalId(String depositAccountId);
}
