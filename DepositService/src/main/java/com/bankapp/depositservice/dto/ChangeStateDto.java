package com.bankapp.depositservice.dto;

import com.bankapp.depositservice.model.AccountState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangeStateDto {

    private AccountState accountState;
}
