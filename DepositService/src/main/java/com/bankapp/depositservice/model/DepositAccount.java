package com.bankapp.depositservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "DepositAccount")
@NoArgsConstructor
@AllArgsConstructor
public class DepositAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "externalId", nullable = false, unique = true)
    private String externalId;
    @Enumerated(EnumType.STRING)
    @Column(name = "accountType", nullable = false, length = 20)
    private AccountType accountType;
    @Column(name = "openingBalance", nullable = false)
    private BigDecimal openingBalance;
    @Column(name = "externalUserId", updatable = false, nullable = false)
    private String externalUserId;
    @Enumerated(EnumType.STRING)
    @Column(name = "accountState", nullable = false, length = 20)
    private AccountState accountState;
    @Column(name = "creationDate")
    private OffsetDateTime creationDate;
    @Column(name = "modifiedDate")
    private OffsetDateTime modifiedDate;

    @PrePersist
    public void onCreate() {
        if (this.creationDate == null) {
            this.creationDate = OffsetDateTime.now();
        }

        if(this.modifiedDate == null) {
            this.modifiedDate = OffsetDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {
        this.modifiedDate = OffsetDateTime.now();
    }

}
