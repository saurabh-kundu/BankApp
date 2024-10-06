package com.bankapp.depositservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Date;

@Data
@Builder
@Entity
@Table(name = "DepositTransaction")
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "transactionType", nullable = false)
    private DepositTransactionType depositTransactionType;
    @Column(name = "externalAccountId", nullable = false)
    private String externalAccountId;
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;
    @Column(name = "externalUserId", nullable = false)
    private String externalUserId;
    @Column(name = "creationDate")
    private OffsetDateTime creationDate;

    @PrePersist
    public void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = OffsetDateTime.now();
        }
    }
}
