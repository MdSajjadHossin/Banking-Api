package com.springboot.banking_api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EnquiryRequest {

    private String accountNumber;
}
