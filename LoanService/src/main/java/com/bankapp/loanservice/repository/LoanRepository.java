package com.bankapp.loanservice.repository;

import com.bankapp.loanservice.model.LoanAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<LoanAccount, Long> {
}
