package com.bankapp.depositservice.gateway;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String userId;
    private String firstName;
    private String lastName;
    private int age;
    private Date dateOfBirth;
    private String address;
    private String phoneNumber;
    private String email;

}
