package com.bankapp.user.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@Table(name = "BankUser")
@NoArgsConstructor
@AllArgsConstructor
public class BankUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "externalId", length = 36, nullable = false, unique = true)
    private String externalId;
    @Column(name = "firstName", length = 36, nullable = false)
    private String firstName;
    @Column(name = "lastName", length = 36, nullable = false)
    private String lastName;
    @Column(name = "age", length = 3)
    private int age;
    @Column(name = "dateOfBirth")
    private Date dateOfBirth;
    @Column(name = "address")
    private String address;
    @Column(name = "phoneNumber", length = 20, nullable = false)
    private int phoneNumber;
    @Column(name = "email", length = 50, nullable = false)
    private String email;

}
