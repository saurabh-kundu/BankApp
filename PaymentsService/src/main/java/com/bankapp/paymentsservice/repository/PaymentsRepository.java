package com.bankapp.paymentsservice.repository;

import com.bankapp.paymentsservice.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<PaymentDetails, Long> {
}
