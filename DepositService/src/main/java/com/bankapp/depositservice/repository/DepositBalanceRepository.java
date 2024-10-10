package com.bankapp.depositservice.repository;

import com.bankapp.depositservice.model.DepositAccountBalance;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepositBalanceRepository {

    void save(DepositAccountBalance depositAccountBalance);

    Optional<DepositAccountBalance> findByExternalAccountId(String depositAccountId);
}
