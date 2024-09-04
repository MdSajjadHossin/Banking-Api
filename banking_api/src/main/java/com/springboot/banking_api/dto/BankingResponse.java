package com.springboot.banking_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BankingResponse {

    private String responseCode;
    private String responseMessage;
    private AccountInfo accountInfo;

}
