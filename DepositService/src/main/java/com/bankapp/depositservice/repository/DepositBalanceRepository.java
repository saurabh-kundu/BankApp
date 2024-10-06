package com.bankapp.depositservice.repository;

import com.bankapp.depositservice.model.DepositAccountBalance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositBalanceRepository extends JpaRepository<DepositAccountBalance, Long> {

    Optional<DepositAccountBalance> findByExternalAccountId(String depositAccountId);
}
