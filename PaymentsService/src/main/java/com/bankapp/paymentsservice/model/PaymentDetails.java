package com.bankapp.paymentsservice.model;

import com.bankapp.paymentsservice.gateway.dto.DepositTransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "userId", nullable = false)
    private Long userId;
    @Column(name = "accountId", nullable = false)
    private Long accountId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "transactionType", nullable = false)
    private DepositTransactionType depositTransactionType;
    @Column(name = "creationDate")
    private OffsetDateTime creationDate;

    @PrePersist
    public void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = OffsetDateTime.now();
        }
    }
}
