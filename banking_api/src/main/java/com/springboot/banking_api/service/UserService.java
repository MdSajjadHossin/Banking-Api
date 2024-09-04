package com.springboot.banking_api.service;

import com.springboot.banking_api.dto.BankingResponse;
import com.springboot.banking_api.dto.CreditDebitRequest;
import com.springboot.banking_api.dto.EnquiryRequest;
import com.springboot.banking_api.dto.UserRequest;

public interface UserService {

    BankingResponse createAccount(UserRequest userRequest);
    BankingResponse balanceEnquiry(EnquiryRequest enquiryRequest);
    String nameEnquiry(EnquiryRequest request);
    BankingResponse creditAccount(CreditDebitRequest creditDebitRequest);
    BankingResponse debitAccount(CreditDebitRequest creditDebitRequest);

}
