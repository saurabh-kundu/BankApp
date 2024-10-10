package com.bankapp.depositservice.repository.impl;

import com.bankapp.depositservice.model.DepositAccountBalance;
import com.bankapp.depositservice.repository.DepositBalanceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Slf4j
@Repository
public class DepositBalanceRepositoryImpl implements DepositBalanceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Optional<DepositAccountBalance> findByExternalAccountId(String depositAccountId) {
        try {
            Query query = entityManager.createQuery("SELECT dab FROM DepositAccountBalance where externalAccountId  = :externalAccountId");
            query.setParameter("externalAccountId", depositAccountId);
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            return Optional.ofNullable((DepositAccountBalance) query.getSingleResult());
        } catch (Exception e) {
            log.error("Exception caught while getting DepositAccountBalance using depositAccountId", e);
            throw e; // add a custom exception
        }
    }

    @Override
    @Transactional
    public void save(DepositAccountBalance depositAccountBalance) {
        try {
            entityManager.persist(depositAccountBalance);
        } catch (Exception e) {
            log.error("Exception caught while saving DepositAccountBalance", e);
            throw e; // add a custom exception
        }
    }
}
