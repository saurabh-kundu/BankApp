package com.bankapp.depositservice.repository;

import com.bankapp.depositservice.model.DepositTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<DepositTransaction, Long> {

    List<DepositTransaction> findAllByExternalAccountId(String externalAccountId);

    List<DepositTransaction> findAllByExternalUserId(String externalUserId);
}
